package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.Trainer;
import java.util.List;

public interface IBasicCrud<T, ID> {

  List<T> findAll();

  T findById(Long id);

  T save(T entity);

  T update(T entity, Long id);

  void deleteById(Long id);
}
