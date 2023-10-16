package com.julian.gymapp.service;

import com.julian.gymapp.domain.Role;
import com.julian.gymapp.dto.UserDto;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.exception.error.ErrorBadUser;
import com.julian.gymapp.exception.error.ErrorBase;
import com.julian.gymapp.repository.RolesRepository;
import com.julian.gymapp.response.ResponseBase;
import com.julian.gymapp.repository.UserRepository;
import com.julian.gymapp.security.SpringCrypto;
import com.julian.gymapp.service.interfaces.IConfigurationService;
import com.julian.gymapp.service.interfaces.IUserService;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  private final ModelMapper modelMapper;

  private final IConfigurationService configurationService;

  @Transactional
  @Override
  public User createUser(User userEntity, List<String> roles) {
    validateUser(userEntity);
    List<Role> listOfRoles = rolesRepository.findAllByNameIn(roles);
    userEntity.setRoles(listOfRoles);
    return userRepository.save(userEntity);
  }

  @Transactional
  @Modifying
  public UserDto updateUser(UserDto userDto, Long userId) {
    User userEntity = userRepository.getReferenceById(userId);
    validateUser(userEntity);
    User userSaved = userRepository.save(userEntity);
    return modelMapper.map(userSaved, UserDto.class);
  }

  @Transactional
  @Modifying
  public void changePassword(String password, Long userId) {
    validatePassword(password);
    User userEntity = userRepository.getReferenceById(userId);
    if(userEntity == null) throw new IllegalArgumentException("User not found");
    userEntity.setPassword(password);
    userRepository.save(userEntity);
  }

  private void validateUser(User user) {
    if(userRepository.existsByUsername(user.getUsername())) {
      throw new IllegalArgumentException("The username must be unique");
    }

    if(!user.getEmail().matches("^(.+)@(.+)$")) {
      throw new IllegalArgumentException("Wrong email format");
    }

    if(user.getPassword().isBlank() && user.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }

    validatePassword(user.getPassword());
  }

  private void validatePassword(String password) {
    if(!SpringCrypto.decrypt(password).matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[^\\w\\d\\s]).{8,}$")) throw new IllegalArgumentException("Wrong Password Format");
  }
}
