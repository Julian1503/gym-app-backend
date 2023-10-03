package com.julian.gymapp.service;

import com.julian.gymapp.configuration.modelConverter.ConverterDateAndLocalDate;
import com.julian.gymapp.configuration.modelConverter.ConverterLocalDateAndDate;
import com.julian.gymapp.domain.ExerciseDayPlan;
import com.julian.gymapp.dto.ExerciseDayPlanDto;
import com.julian.gymapp.service.interfaces.ModelConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperMapping implements ModelConfig {

    @Override
    public ModelMapper getModelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      modelMapper.addConverter(new ConverterDateAndLocalDate());
      modelMapper.addConverter(new ConverterLocalDateAndDate());
      modelMapper.typeMap(ExerciseDayPlan.class, ExerciseDayPlanDto.class)
          .addMapping(src -> src.getExercise().getName(), ExerciseDayPlanDto::setExerciseName)
          .addMapping(src -> src.getExercise().getExerciseId(), ExerciseDayPlanDto::setExerciseId)
          .addMapping(src -> src.getExercise().getDescription(), ExerciseDayPlanDto::setExerciseDescription);
      modelMapper.typeMap(ExerciseDayPlanDto.class, ExerciseDayPlan.class)
          .addMappings(mapper -> {
            mapper.map(ExerciseDayPlanDto::getExerciseId,
                (dest, v) -> dest.getExercise().setExerciseId((Long)v));
          });

      return modelMapper;
  }
}
