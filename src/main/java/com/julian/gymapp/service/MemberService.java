package com.julian.gymapp.service;

import com.julian.gymapp.domain.Member;
import com.julian.gymapp.domain.Trainer;
import com.julian.gymapp.repository.MemberRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends PersonService implements IBasicCrud<Member, Long> {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public List<Member> findAll() {
    return memberRepository.findAll();
  }

  @Override
  public Member findById(Long id) {
    Optional<Member> member = memberRepository.findById(id);
    return member.orElse(null);
  }

  @Override
  public Member save(Member entity) {
    validateMember(entity);
    return memberRepository.save(entity);
  }

  public Member createMemberFromPerson(Long personId, Member member) {
    member.setPersonId(personId);
    return memberRepository.save(member);
  }

  @Override
  public Member update(Member entity, Long id) {
    Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member not found"));
    member.setEmergencyContactName(entity.getEmergencyContactName());
    member.setEmergencyContactPhone(entity.getEmergencyContactPhone());
    update(entity, member);
    validateMember(member);
    return memberRepository.save(member);
  }

  protected void validateMember(Member entity) {
    validatePerson(entity);

    if(memberRepository.existsByMemberNumber(entity.getMemberNumber()) && entity.getPersonId() == null) {
      throw new IllegalArgumentException("member number must be unique");
    }

    if (StringUtils.isBlank(entity.getMemberNumber())) {
      throw new IllegalArgumentException("Member number cannot be blank");
    }

    if (entity.getJoinDate() == null) {
      throw new IllegalArgumentException("Join date cannot be null");
    }
  }


  @Override
  public void delete(Member entity) {
    if (StringUtils.isBlank(entity.getMemberNumber())) {
      throw new IllegalArgumentException("Member number cannot be blank");
    }
    Optional<Member> member = memberRepository.findMemberByMemberNumber(entity.getMemberNumber());
    if(member.isPresent()) {
      memberRepository.delete(member.get());
    } else {
      throw new IllegalArgumentException("Member was not found");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Member> member = memberRepository.findById(id);
    if(member.isPresent()) {
      memberRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("Member was not found");
    }
  }
}
