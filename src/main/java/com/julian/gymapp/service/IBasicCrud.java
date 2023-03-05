package com.julian.gymapp.service;

import com.julian.gymapp.domain.Trainer;
import java.util.List;

public interface IBasicCrud<T, ID> {

  List<T> findAll();

  T findById(Long id);

  T save(T entity);

  T update(T entity, Long id);

  void delete(T entity);

  void deleteById(Long id);
}
