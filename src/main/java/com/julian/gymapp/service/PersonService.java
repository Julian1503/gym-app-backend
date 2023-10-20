package com.julian.gymapp.service;

import com.julian.gymapp.domain.Member;
import com.julian.gymapp.domain.Person;
import com.julian.gymapp.domain.enums.IdentifierType;
import com.julian.gymapp.repository.PersonRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class PersonService {
  @Autowired
  private PersonRepository personRepository;
  protected void validatePerson(Person entity, boolean isUpdate) {
    if (entity == null) {
      throw new IllegalArgumentException("Member cannot be null");
    }

    if (StringUtils.isBlank(entity.getName())) {
      throw new IllegalArgumentException("Name cannot be blank");
    }

    if (StringUtils.isBlank(entity.getLastName())) {
      throw new IllegalArgumentException("Last name cannot be blank");
    }

    if (entity.getIdentifierType() == null) {
      throw new IllegalArgumentException("Identifier type cannot be null");
    }

    if (StringUtils.isNotBlank(entity.getIdentifier())) {
      String identity = entity.getIdentifier();
      if(StringUtils.isBlank(identity)) throw new IllegalArgumentException("Identity cannot be empty");
    }

    if(!isUpdate && personRepository.existsByIdentifier(entity.getIdentifier())) {
      throw new IllegalArgumentException("Identifier must be unique");
    }

    if (entity.getGender() == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }

    if (entity.getBirthDate() == null) {
      throw new IllegalArgumentException("Birth date cannot be null");
    }
  }

  protected void update(Person personUpdated, Person person) {
    person.setName(personUpdated.getName());
    person.setLastName(personUpdated.getLastName());
    person.setIdentifier(personUpdated.getIdentifier());
    person.setIdentifierType(personUpdated.getIdentifierType());
    person.setPhoneNumber(personUpdated.getPhoneNumber());
    person.setPhoto(personUpdated.getPhoto());
    person.setStreet(personUpdated.getStreet());
    person.setHouseNumber(personUpdated.getHouseNumber());
    person.setFloor(personUpdated.getFloor());
    person.setDoor(personUpdated.getDoor());
    person.setGender(personUpdated.getGender());
    person.setBirthDate(personUpdated.getBirthDate());
  }
}
