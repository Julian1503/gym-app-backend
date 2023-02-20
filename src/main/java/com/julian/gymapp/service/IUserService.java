package com.julian.gymapp.service;

import com.julian.gymapp.dto.UserDto;
import com.julian.gymapp.exception.error.ErrorBase;
import com.julian.gymapp.response.ResponseBase;
import io.vavr.control.Either;

public interface IUserService {
  Either<ErrorBase, ResponseBase> changePassword(String password, Long userId);
  UserDto updateUser(UserDto userDto, Long userId);
  UserDto createUser(UserDto userDto);
}
