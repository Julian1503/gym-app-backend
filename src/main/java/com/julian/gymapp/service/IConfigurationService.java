package com.julian.gymapp.service;

import com.julian.gymapp.domain.User;
import com.julian.gymapp.dto.ConfigurationDto;

public interface IConfigurationService {
  ConfigurationDto updateConfiguration(ConfigurationDto configurationDto, Long userId);
  ConfigurationDto createDefaultConfiguration(User user);
}
