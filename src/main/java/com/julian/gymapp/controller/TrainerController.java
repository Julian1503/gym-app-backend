package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Trainer;
import com.julian.gymapp.dto.TrainerDto;
import com.julian.gymapp.service.IBasicCrud;
import jakarta.validation.Valid;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/trainer")
public class TrainerController {

  private final IBasicCrud<Trainer, Long> trainerRepository;
  private final ModelMapper mapper;

  public TrainerController(IBasicCrud<Trainer, Long> trainerRepository, ModelMapper mapper) {
    this.trainerRepository = trainerRepository;
    this.mapper = mapper;
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllTrainers() {
    BaseResponse baseResponse = new BaseResponse();
    try {
      List<Trainer> trainers = trainerRepository.findAll();
      baseResponse.setResponse(trainers);
      baseResponse.setMessage("Trainers returned successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage("Something went bad");
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Trainer trainer = trainerRepository.findById(id);
      if(trainer == null) {
        baseResponse.setMessage("Trainer was not found");
        baseResponse.setSuccess(false);
      } else {
        baseResponse.setResponse(trainer);
        baseResponse.setMessage("Trainer returned successfully");
      }
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @PostMapping("create")
  public ResponseEntity<BaseResponse> createTrainer(@Valid @RequestBody TrainerDto trainerDto) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Trainer trainer = mapper.map(trainerDto, Trainer.class);
      trainerRepository.save(trainer);
      baseResponse.setResponse(trainer);
      baseResponse.setMessage("Trainer was created successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @PutMapping("update/{id}")
  public ResponseEntity<BaseResponse> updateTrainer(@Valid @RequestBody TrainerDto trainerDto, @PathVariable Long id) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Trainer trainer = mapper.map(trainerDto, Trainer.class);
      Trainer trainerUpdated = trainerRepository.update(trainer, id);
      baseResponse.setResponse(trainerUpdated);
      baseResponse.setMessage("Trainer was updated successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @DeleteMapping("delete")
  public ResponseEntity<BaseResponse> deleteTrainer(@Valid @RequestBody TrainerDto trainerDto) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      Trainer trainer = mapper.map(trainerDto, Trainer.class);
      trainerRepository.delete(trainer);
      baseResponse.setMessage("Trainer was deleted successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<BaseResponse> deleteTrainer(@PathVariable Long id) {
    BaseResponse baseResponse = new BaseResponse();
    try {
      trainerRepository.deleteById(id);
      baseResponse.setMessage("Trainer was deleted successfully");
    } catch (Exception e) {
      baseResponse.setStatus(HttpStatus.BAD_GATEWAY);
      baseResponse.setSuccess(false);
      baseResponse.setMessage(e.getMessage());
    }
    return new ResponseEntity<>(baseResponse, HttpStatus.OK);
  }
}