package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Member;
import com.julian.gymapp.dto.MemberDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class MemberController extends BaseController {

  private final IBasicCrud<Member, Long> memberRepository;
  private final ModelMapper mapper;

  public MemberController(IBasicCrud<Member, Long> memberRepository, ModelConfig mapper) {
    this.memberRepository = memberRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllMembers() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Member> members = memberRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(members), "Members returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Member member = memberRepository.findById(id);
      if (member == null) {
        baseResponse = createErrorResponse("Member was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(member), "Member returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createMember(@Valid @RequestBody MemberDto memberDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Member member = mapper.map(memberDto, Member.class);
      Member savedMember = memberRepository.save(member);
      baseResponse = createSuccessResponse(convertToDto(savedMember), "Member was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateMember(@Valid @RequestBody MemberDto memberDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Member member = mapper.map(memberDto, Member.class);
      Member updatedMember = memberRepository.update(member, id);
      baseResponse = createSuccessResponse(convertToDto(updatedMember), "Member was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteMember(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      memberRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Member was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private MemberDto convertToDto(Member member) {
    MemberDto memberDto = mapper.map(member, MemberDto.class);

    return memberDto;
  }

  private List<MemberDto> convertToDto(List<Member> memberList) {
    return memberList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
