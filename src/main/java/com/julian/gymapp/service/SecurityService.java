package com.julian.gymapp.service;

import antlr.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.julian.gymapp.configuration.RsaKeysProperties;
import com.julian.gymapp.domain.Permission;
import com.julian.gymapp.domain.Role;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.model.JwtUser;
import com.julian.gymapp.repository.UserRepository;
import com.julian.gymapp.service.interfaces.ISecurityService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class SecurityService implements ISecurityService, UserDetailsService {

  private static final Log LOGGER = LogFactory.getLog(SecurityService.class);
  private final JwtEncoder encoder;
  private final RsaKeysProperties rsaKeysProperties;
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).map(JwtUser::new).orElseThrow(()-> new UsernameNotFoundException("User not found"));
  }

  @Override
  public String generateToken(Authentication authentication, Consumer<Map<String, Object>> claims) {
    Instant now = Instant.now();
    User user = getUser(authentication);
    JwtClaimsSet claimsJwt = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("scope", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList())
        .claim("email", user.getEmail())
        .claim("scid", user.getUser() != null ? user.getUser().getPersonId() : "")
        .claims(claims)
        .build();
    return this.encoder.encode(JwtEncoderParameters.from(claimsJwt)).getTokenValue();
  }

  private User getUser(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    if (user != null) {
      return user;
    }
    return null;
  }

  @Override
  public Consumer<Map<String, Object>> getClaims() {
    return stringObjectMap -> stringObjectMap.put("isAdmin", true);
  }

  public DecodedJWT decode(String authToken) {
    return JWT.decode(authToken);
  }

  public DecodedJWT verify(String authToken) {
    DecodedJWT jwt = null;

    try {
      JWTVerifier verifier = JWT.require(Algorithm.RSA256(rsaKeysProperties.publicKey(), rsaKeysProperties.privateKey())).withIssuer(new String[]{"self"}).build();
      jwt = verifier.verify(authToken);
    } catch (Exception var4) {
      LOGGER.error("Error while verifying authentication token.", var4);
    }

    return jwt;
  }
  public String getSubjectFromToken(String token) {
    String username = null;

    try {
      DecodedJWT jwt = this.getClaimsFromToken(token);
      username = jwt.getSubject();
    } catch (Exception var4) {
      LOGGER.error("Error getting subject from token", var4);
    }

    return username;
  }

  public Date getExpirationDateFromToken(String token) {
    Date expiration = null;

    try {
      DecodedJWT jwt = this.getClaimsFromToken(token);
      expiration = jwt.getExpiresAt();
    } catch (Exception var4) {
      LOGGER.error("Error getting expiration date from token", var4);
    }

    return expiration;
  }

  private DecodedJWT getClaimsFromToken(String token) {
    DecodedJWT jwt = null;

    try {
      jwt = this.decode(token);
    } catch (Exception var4) {
      LOGGER.trace("Error getting claims from token: " + var4.getMessage());
    }

    return jwt;
  }

  public boolean validateToken(String token) {
    Boolean isValid = false;
    DecodedJWT jwt = this.getClaimsFromToken(token);
    if (jwt != null) {
      isValid = true;
    }

    return isValid;
  }

  public UserDetails getUserDetails(String authToken) {
    JwtUser jwtUser = null;

    try {
      DecodedJWT jwt = JWT.decode(authToken);
      String email = jwt.getClaim("email").asString();
      List<Role> authorities = new ArrayList();
      if (!jwt.getClaim("scope").isNull()) {
        String[] scopes = (String[])jwt.getClaim("scope").asArray(String.class);

        for(int i = 0; i < scopes.length; ++i) {
          Role auth = new Role(scopes[i]);
          authorities.add(auth);
        }
      }

      jwtUser = new JwtUser(new User(email, authorities));
    } catch (Exception var9) {
      LOGGER.error("Error getting user details", var9);
    }

    return jwtUser;
  }

  private List<GrantedAuthority> mapToGrantedAuthorities(List<Permission> authorities) {
    return authorities.stream().map((authority) -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
  }

  public Date getAdminClaimFromToken(String token) {
    Date expiration = null;

    try {
      DecodedJWT jwt = this.getClaimsFromToken(token);
      expiration = jwt.getExpiresAt();
    } catch (Exception var4) {
      LOGGER.error("Error getting expiration date from token", var4);
    }

    return expiration;
  }

  public DecodedJWT stripFrontAndDecode(String authorizationHeader) {
    DecodedJWT decodedJWT = null;
    String token = StringUtils.stripFront(authorizationHeader, "Bearer");

    try {
      decodedJWT = this.decode(token);
    } catch (NullPointerException var5) {
      LOGGER.trace("Invalid JWT: " + var5.getMessage());
    }

    return decodedJWT;
  }
}
