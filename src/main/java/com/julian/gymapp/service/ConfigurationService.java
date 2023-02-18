package com.julian.gymapp.service;

import com.julian.gymapp.domain.Configuration;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.dto.ConfigurationDto;
import com.julian.gymapp.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ConfigurationService implements IConfigurationService {

  private final ConfigurationRepository configurationRepository;
  private final ModelMapper modelMapper;

  public ConfigurationDto createDefaultConfiguration(User user) {
    Configuration configuration = new Configuration();
    configuration.setDateFormat(1);
    configuration.setTimeFormat(1);
    configuration.setLanguage(1);
    configuration.setUser(user);
    configurationRepository.save(configuration);
    return modelMapper.map(configuration, ConfigurationDto.class);
  }

  @Transactional
  @Modifying
  public ConfigurationDto updateConfiguration(ConfigurationDto configurationDto, Long userId) {
    return getConfigurationDto(configurationDto, userId, configurationRepository, modelMapper);
  }

  private static ConfigurationDto getConfigurationDto(ConfigurationDto configurationDto, Long userId,
      ConfigurationRepository configurationRepository, ModelMapper modelMapper) {
    Configuration configuration = configurationRepository.findByUser(userId);
    configuration.setLanguage(configurationDto.getLanguage());
    configuration.setDateFormat(configurationDto.getDateFormat());
    configuration.setTimeFormat(configurationDto.getTimeFormat());
    Configuration configurationSaved = configurationRepository.save(configuration);
    return modelMapper.map(configurationSaved, ConfigurationDto.class);
  }
}
