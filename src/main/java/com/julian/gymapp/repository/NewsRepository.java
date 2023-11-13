package com.julian.gymapp.repository;

import com.julian.gymapp.domain.News;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
  List<News> findAllByIsDeletedFalse();
  Optional<News> findByNewsIdAndIsDeletedFalse(Long id);

  Page<News> findAllByIsDeletedFalse(Pageable pageable);
}
