package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INewsService extends IBasicCrud<News, Long> {
  News changeActiveStatus(Long id);
  Page<News> getAllNewsPage(Pageable pageable);
}
