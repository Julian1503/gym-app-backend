package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.MembershipSubscription;
import com.julian.gymapp.dto.MembershipSubscriptionDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
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
@RequestMapping("/api/membership-subscription")
public class MembershipSubscriptionController extends BaseController {

  private final IBasicCrud<MembershipSubscription, Long> membershipSubscriptionRepository;
  ModelMapper mapper;

  public MembershipSubscriptionController(IBasicCrud<MembershipSubscription, Long> membershipSubscriptionRepository, ModelConfig mapper) {
    this.membershipSubscriptionRepository = membershipSubscriptionRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllMembershipSubscriptions() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<MembershipSubscription> membershipSubscriptions = membershipSubscriptionRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(membershipSubscriptions), "MembershipSubscriptions returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      MembershipSubscription membershipSubscription = membershipSubscriptionRepository.findById(id);
      if (membershipSubscription == null) {
        baseResponse = createErrorResponse("MembershipSubscription was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(membershipSubscription), "MembershipSubscription returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createMembershipSubscription(@Valid @RequestBody MembershipSubscriptionDto membershipSubscriptionDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      MembershipSubscription membershipSubscription = mapper.map(membershipSubscriptionDto, MembershipSubscription.class);
      MembershipSubscription savedMembershipSubscription = membershipSubscriptionRepository.save(membershipSubscription);
      baseResponse = createSuccessResponse(convertToDto(savedMembershipSubscription), "MembershipSubscription was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateMembershipSubscription(@Valid @RequestBody MembershipSubscriptionDto membershipSubscriptionDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      MembershipSubscription membershipSubscription = mapper.map(membershipSubscriptionDto, MembershipSubscription.class);
      MembershipSubscription updatedMembershipSubscription = membershipSubscriptionRepository.update(membershipSubscription, id);
      baseResponse = createSuccessResponse(convertToDto(updatedMembershipSubscription), "MembershipSubscription was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteMembershipSubscription(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      membershipSubscriptionRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "MembershipSubscription was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private MembershipSubscriptionDto convertToDto(MembershipSubscription membershipSubscription) {
    return mapper.map(membershipSubscription, MembershipSubscriptionDto.class);
  }

  private List<MembershipSubscriptionDto> convertToDto(List<MembershipSubscription> membershipSubscriptions) {
    return membershipSubscriptions.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
