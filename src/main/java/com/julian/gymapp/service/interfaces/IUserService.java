package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.User;
import com.julian.gymapp.dto.UserDto;
import com.julian.gymapp.exception.error.ErrorBase;
import com.julian.gymapp.response.ResponseBase;
import io.vavr.control.Either;
import java.util.List;

public interface IUserService {
  void changePassword(String password, Long userId);
  UserDto updateUser(UserDto userDto, Long userId);
  User createUser(User userEntity, List<String> roles);
}
