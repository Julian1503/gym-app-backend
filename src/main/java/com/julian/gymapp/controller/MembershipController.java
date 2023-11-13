package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Membership;
import com.julian.gymapp.dto.MembershipDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collector;
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
@RequestMapping("/api/membership")
public class MembershipController extends BaseController {

  private final IBasicCrud<Membership, Long> membershipRepository;
  private final ModelMapper mapper;

  public MembershipController(IBasicCrud<Membership, Long> membershipRepository, ModelConfig mapper) {
    this.membershipRepository = membershipRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllMemberships() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Membership> memberships = membershipRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(memberships), "Memberships returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Membership membership = membershipRepository.findById(id);
      if (membership == null) {
        baseResponse = createErrorResponse("Membership was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(membership), "Membership returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createMembership(@Valid @RequestBody MembershipDto membershipDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Membership membership = mapper.map(membershipDto, Membership.class);
      Membership savedMembership = membershipRepository.save(membership);
      baseResponse = createSuccessResponse(convertToDto(savedMembership), "Membership was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateMembership(@Valid @RequestBody MembershipDto membershipDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Membership membership = mapper.map(membershipDto, Membership.class);
      Membership updatedMembership = membershipRepository.update(membership, id);
      baseResponse = createSuccessResponse(convertToDto(updatedMembership), "Membership was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteMembership(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      membershipRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Membership was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private MembershipDto convertToDto(Membership membership) {
    return mapper.map(membership, MembershipDto.class);
  }

  private List<MembershipDto> convertToDto(List<Membership> memberships) {
    return memberships.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
