package com.julian.gymapp.service;

import com.julian.gymapp.configuration.modelConverter.ConvertShortAndString;
import com.julian.gymapp.configuration.modelConverter.ConvertStringAndShort;
import com.julian.gymapp.configuration.modelConverter.ConverterDateAndLocalDate;
import com.julian.gymapp.configuration.modelConverter.ConverterLocalDateAndDate;
import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.ExerciseDayPlan;
import com.julian.gymapp.domain.User;
import com.julian.gymapp.dto.CashTransactionDto;
import com.julian.gymapp.dto.ExerciseDayPlanDto;
import com.julian.gymapp.dto.UserDto;
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
    modelMapper.addConverter(new ConvertShortAndString());
    modelMapper.addConverter(new ConvertStringAndShort());
    modelMapper.typeMap(User.class, UserDto.class)
        .addMapping(src -> src.getUser().getPersonId(), UserDto::setMemberId)
        .addMapping(src -> src.getUser().getName(), UserDto::setMemberName)
        .addMapping(src -> src.getUser().getLastName(), UserDto::setMemberLastname);
    modelMapper.typeMap(CashTransaction.class, CashTransactionDto.class)
        .addMapping(src -> src.getPaymentType().getName(), CashTransactionDto::setPaymentTypeName)
        .addMapping(src -> src.getPaymentType().getPaymentTypeId(), CashTransactionDto::setPaymentTypeId)
        .addMapping(src -> src.getMember().getPersonId(), CashTransactionDto::setMemberId)
        .addMapping(src -> src.getMember().getIdentifier(), CashTransactionDto::setMemberIdentifier)
        .addMapping(src -> src.getMember().getName(), CashTransactionDto::setMemberName)
        .addMapping(src -> src.getMember().getLastName(), CashTransactionDto::setMemberLastname)
        .addMapping(src -> src.getCashRegister().getCashRegisterId(), CashTransactionDto::setCashRegisterId)
        .addMapping(src -> src.getMembership().getName(), CashTransactionDto::setMembershipName)
        .addMapping(src -> src.getMembership().getMembershipId(), CashTransactionDto::setMembershipId);

    modelMapper.typeMap(CashTransactionDto.class, CashTransaction.class)
        .addMappings(mapper -> {
          mapper.map(CashTransactionDto::getPaymentTypeId,
              (dest, v) -> dest.getPaymentType().setPaymentTypeId((Long)v));
          mapper.map(CashTransactionDto::getMemberId,
              (dest, v) -> dest.getMember().setPersonId((Long)v));
          mapper.map(CashTransactionDto::getMembershipId,
              (dest, v) -> dest.getMembership().setMembershipId((Long)v));
          mapper.map(CashTransactionDto::getCashRegisterId,
              (dest, v) -> dest.getCashRegister().setCashRegisterId((Long)v));
        });

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
