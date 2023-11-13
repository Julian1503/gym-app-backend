package com.julian.gymapp.service;

import static com.julian.gymapp.helper.StringHelper.generateRandomString;

import com.julian.gymapp.domain.Member;
import com.julian.gymapp.repository.MemberRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
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
    return memberRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Member findById(Long id) {
    Optional<Member> member = memberRepository.findByPersonIdAndIsDeletedFalse(id);
    return member.orElse(null);
  }

  @Override
  public Member save(Member entity) {
    entity.setMemberNumber("MB-" + generateRandomString());
    entity.setJoinDate(new Date());
    validateMember(entity, false);
    return memberRepository.save(entity);
  }

  @Override
  public Member update(Member entity, Long id) {
    Member member = memberRepository.findByPersonIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Member not found"));
    member.setEmergencyContactName(entity.getEmergencyContactName());
    member.setEmergencyContactPhone(entity.getEmergencyContactPhone());
    update(entity, member);
    validateMember(member, true);
    return memberRepository.save(member);
  }

  protected void validateMember(Member entity, boolean isUpdate) {
    validatePerson(entity, isUpdate);

    if(!isUpdate && memberRepository.existsByMemberNumber(entity.getMemberNumber()) && entity.getPersonId() == null) {
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
  public void deleteById(Long id) {
    Optional<Member> member = memberRepository.findByPersonIdAndIsDeletedFalse(id);
    if(member.isPresent()) {
      Member memberToDelete = member.get();
      memberToDelete.setDeleted(true);
      memberRepository.save(memberToDelete);
    } else {
      throw new IllegalArgumentException("Member was not found");
    }
  }
}
