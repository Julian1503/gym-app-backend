package com.julian.gymapp.service;

import com.julian.gymapp.domain.News;
import com.julian.gymapp.repository.NewsRepository;
import com.julian.gymapp.service.interfaces.INewsService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements INewsService {
  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
      this.newsRepository = newsRepository;
    }

    @Override
    public List<News> findAll() {
      return newsRepository.findAllByIsDeletedFalse();
    }

    @Override
    public News findById(Long id) {
      Optional<News> news = newsRepository.findByNewsIdAndIsDeletedFalse(id);
      return news.orElse(null);
    }

    @Override
    public News save(News entity) {
      validateNews(entity);
      return newsRepository.save(entity);
    }

    @Override
    public News update(News entity, Long id) {
      News news = newsRepository.findByNewsIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("News was not found"));
      validateNews(news);
      news.setContent(entity.getContent());
      news.setTitle(entity.getTitle());
      news.setSubtitle(entity.getSubtitle());
      news.setImageUrl(entity.getImageUrl());
      return newsRepository.save(news);
    }

    protected void validateNews(News entity) {
      if(entity.getContent().isBlank() || entity.getContent().isEmpty()) {
        throw new IllegalArgumentException("Content can not be blank or empty");
      }
      if(entity.getTitle().isBlank() || entity.getTitle().isEmpty()) {
        throw new IllegalArgumentException("Title can not be blank or empty");
      }
    }

    @Override
    public void deleteById(Long id) {
      Optional<News> news = newsRepository.findByNewsIdAndIsDeletedFalse(id);
      if(news.isPresent()) {
        News newsToDelete = news.get();
        newsToDelete.setDeleted(true);
        newsRepository.save(newsToDelete);
      } else {
        throw new IllegalArgumentException("News was not found");
      }
    }

  @Override
  public News changeActiveStatus(Long id) {
    News news = newsRepository.findByNewsIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("News was not found"));
    news.setActive(!news.isActive());
    return newsRepository.save(news);
  }

  @Override
  public Page<News> getAllNewsPage(Pageable pageable) {
    return newsRepository.findAllByIsDeletedFalse(pageable);
  }
}
