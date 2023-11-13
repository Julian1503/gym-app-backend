package com.julian.gymapp.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.julian.gymapp.exception.CustomAuthenticationEntryPoint;
import com.julian.gymapp.service.SecurityService;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final SecurityService securityService;
  private final PasswordEncoder passwordEncoder;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  public SecurityConfig(
      SecurityService securityService, PasswordEncoder passwordEncoder,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
    this.securityService = securityService;
    this.passwordEncoder = passwordEncoder;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
  }

  @Bean
  public AuthenticationManager authorizationManager(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    authenticationProvider.setUserDetailsService(securityService);
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf().disable()
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/user/create").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().denyAll()
            )
            .headers(HeadersConfigurer::cacheControl)
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .exceptionHandling(ex-> ex.authenticationEntryPoint(customAuthenticationEntryPoint))
            .userDetailsService(securityService)
            .build();
  }
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE","PUT","PATCH"));
    configuration.setAllowCredentials(true);
    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
