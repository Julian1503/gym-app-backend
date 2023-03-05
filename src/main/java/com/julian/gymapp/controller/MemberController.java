package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Member;
import com.julian.gymapp.dto.MemberDto;
import com.julian.gymapp.service.IBasicCrud;
import jakarta.validation.Valid;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class MemberController {

  private final IBasicCrud<Member, Long> memberRepository;
  private final ModelMapper mapper;

  public MemberController(IBasicCrud<Member, Long> memberRepository, ModelMapper mapper) {
    this.memberRepository = memberRepository;
    this.mapper = mapper;
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllMembers() {
    BaseResponse baseResponse = new BaseResponse();
    try {
      List<Member> members = memberRepository.findAll();
      baseResponse.setResponse(members);
      baseResponse.setMessage("Members returned successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage("Something went bad");
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @PostMapping("create")
  public ResponseEntity<BaseResponse> createMember(@Valid @RequestBody MemberDto memberDto) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Member member = mapper.map(memberDto, Member.class);
      memberRepository.save(member);
      baseResponse.setResponse(member);
      baseResponse.setMessage("Member was created successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }
}