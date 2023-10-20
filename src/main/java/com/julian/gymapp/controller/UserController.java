package com.julian.gymapp.controller;

import static com.julian.gymapp.helper.StringHelper.generateRandomString;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.julian.gymapp.controller.request.UserRequest;
import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Member;
import com.julian.gymapp.domain.Person;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.domain.enums.Gender;
import com.julian.gymapp.domain.enums.IdentifierType;
import com.julian.gymapp.dto.ChangePasswordDto;
import com.julian.gymapp.dto.PersonUserDto;
import com.julian.gymapp.dto.UserDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.IUserService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {
  private IUserService userRepository;
  private IBasicCrud<Member, Long> memberRepository;
  private final ModelMapper mapper;

  public UserController(ModelConfig mapper, IUserService userRepository, IBasicCrud<Member, Long> memberRepository) {
    this.userRepository = userRepository;
    this.mapper = mapper.getModelMapper();
    this.memberRepository = memberRepository;
  }

  @PostMapping("/create")
  @Transactional
  public ResponseEntity<BaseResponse> createUser(@Valid @RequestBody PersonUserDto personUserDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Member member = createMember(personUserDto);
      User user = createBasicUser(personUserDto.getEmail(), personUserDto.getPassword(), personUserDto.getUsername());
      Member memberSaved = memberRepository.save(member);
      user.setUser(memberSaved);
      userRepository.createUser(user, List.of("user"));
      baseResponse = createSuccessResponse(member, "User was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create-user")
  public ResponseEntity<BaseResponse> createUserToMember(@Valid @RequestBody UserRequest userRequest) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Member member = memberRepository.findById(userRequest.getMemberId());
      User user = createBasicUser(userRequest.getEmail(), userRequest.getPassword(), userRequest.getUsername());
      user.setUser(member);
      userRepository.createUser(user, userRequest.getRoles());
      baseResponse = createSuccessResponse(member, "User was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  //get all users endpoint
  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllUsers() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<User> users = userRepository.getAllUsers();
      List<UserDto> usersDto = users.stream().map(user -> mapper.map(user, UserDto.class)).toList();
      baseResponse = createSuccessResponse(usersDto, "Users were found successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/change-password")
  public ResponseEntity<BaseResponse> changePassword(@Valid @RequestBody ChangePasswordDto personUserDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      userRepository.changePassword(personUserDto.getNewPassword(), personUserDto.getCurrentPassword(), personUserDto.getUserId());
      baseResponse = createSuccessResponse(null, "Password was changed successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private User createBasicUser(String email, String password, String username) {
    User user = new User();
    if(!password.isBlank()) {
      user.setPassword(password);
    }
    user.setEmail(email);
    user.setUsername(username);
    return user;
  }

  private Member createMember(PersonUserDto personUserDto) {
    Member member = new Member();
    member.setName(personUserDto.getName());
    member.setGender(Gender.Male);
    member.setJoinDate(new java.util.Date());
    member.setLastName(personUserDto.getLastname());
    member.setBirthDate(Date.valueOf(personUserDto.getBirthDate()));
    member.setIdentifierType(IdentifierType.NationalID);
    member.setIdentifier(personUserDto.getIdentity());
    member.setMemberNumber("MB-" + generateRandomString());
    return member;
  }
}
