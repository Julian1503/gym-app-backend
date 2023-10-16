package com.julian.gymapp.controller;

import static com.julian.gymapp.helper.StringHelper.generateRandomString;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Member;
import com.julian.gymapp.domain.Person;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.domain.enums.Gender;
import com.julian.gymapp.domain.enums.IdentifierType;
import com.julian.gymapp.dto.PersonUserDto;
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
import org.springframework.web.bind.annotation.PostMapping;
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
      User user = createBasicUser(personUserDto);
      Member memberSaved = memberRepository.save(member);
      user.setUser(memberSaved);
      userRepository.createUser(user, List.of("user"));
      baseResponse = createSuccessResponse(member, "User was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private User createBasicUser(PersonUserDto personUserDto) {
    User user = new User();
    if(!personUserDto.getPassword().isBlank()) {
      user.setPassword(personUserDto.getPassword());
    }
    user.setEmail(personUserDto.getEmail());
    user.setUsername(personUserDto.getUsername());
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
