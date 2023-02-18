package com.julian.gymapp.service;

import com.julian.gymapp.dto.ConfigurationDto;
import com.julian.gymapp.dto.UserDto;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.error.ErrorBadUser;
import com.julian.gymapp.error.ErrorBase;
import com.julian.gymapp.response.ResponseBase;
import com.julian.gymapp.repository.UserRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class UserService implements IUserService{

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  private final IConfigurationService configurationService;

  @Transactional
  public UserDto createUser(UserDto userDto) {
    User user = modelMapper.map(userDto, User.class);
    User userSaved = userRepository.save(user);
    configurationService.createDefaultConfiguration(userSaved);
    return modelMapper.map(userSaved, UserDto.class);
  }

  @Transactional
  @Modifying
  public UserDto updateUser(UserDto userDto, Long userId) {
    User userEntity = userRepository.getReferenceById(userId);
    userEntity.setAvatar(userDto.getAvatar());
    userEntity.setName(userDto.getName());
    userEntity.setSurname(userDto.getSurname());
    User userSaved = userRepository.save(userEntity);
    return modelMapper.map(userSaved, UserDto.class);
  }

  @Transactional
  @Modifying
  public Either<ErrorBase, ResponseBase> changePassword(String password, Long userId) {
    ErrorBase pass = validatePassword(password);
    if(pass!= null) return Either.left(pass);
    User userEntity = userRepository.getReferenceById(userId);
    if(userEntity == null) return Either.left(new ErrorBadUser(userId));
    userEntity.setPassword(password);
    userRepository.save(userEntity);
    return Either.right(new ResponseBase("Your password was changed sucessfully","200"));
  }

  private ErrorBase validatePassword(String password) {
    ErrorBase error = null;
    if(!password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*\\d.*\\d)(?=.*[a-z].*[a-z].*[a-z]).{8}$")) error = new ErrorBase("Wrong Password Format", "400", "Your password does not match with the correct format.");
    return error;
  }
}
