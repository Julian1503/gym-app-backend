package com.julian.gymapp.controller;

import com.julian.gymapp.controller.request.PageState;
import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.News;
import com.julian.gymapp.dto.NewsDto;
import com.julian.gymapp.service.interfaces.INewsService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/news")
public class NewsController extends BaseController {

  private final INewsService newsService;
  private final ModelMapper mapper;

  public NewsController(INewsService newsService, ModelConfig modelConfig) {
    this.newsService = newsService;
    this.mapper = modelConfig.getModelMapper();
  }

  @GetMapping("/get-all")
  @Transactional
  public ResponseEntity<BaseResponse> getAllNewss() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<News> newss = newsService.findAll();
      baseResponse = createSuccessResponse(convertToDto(newss), "Newss returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-news-page")
  @Transactional
  public ResponseEntity<BaseResponse> getPageNews(
      @ModelAttribute PageState pageOptions) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Sort sort = Sort.by(Sort.Direction.fromString(pageOptions.getDirection()), pageOptions.getSort());
      Pageable pageable = PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), sort);
      Page<News> news = newsService.getAllNewsPage(pageable);
      baseResponse = createSuccessResponse(news, "Newss returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }


  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      News news = newsService.findById(id);
      if(news == null) {
        baseResponse = createErrorResponse("News was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(news), "News returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createNews(@Valid @RequestBody NewsDto newsDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      News news = mapper.map(newsDto, News.class);
      News newsSaved = newsService.save(news);
      baseResponse = createSuccessResponse(convertToDto(newsSaved), "News was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateNews(@Valid @RequestBody NewsDto newsDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      News news = mapper.map(newsDto, News.class);
      News newsUpdated = newsService.update(news, id);
      baseResponse = createSuccessResponse(convertToDto(newsUpdated), "News was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/active-status/{id}")
  public ResponseEntity<BaseResponse> changeActiveStatus(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      News newsUpdated = newsService.changeActiveStatus(id);
      baseResponse = createSuccessResponse(convertToDto(newsUpdated), "News was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteNews(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      newsService.deleteById(id);
      baseResponse = createSuccessResponse(null, "News was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private NewsDto convertToDto(News news) {
    return mapper.map(news, NewsDto.class);
  }

  private List<NewsDto> convertToDto(List<News> newsList) {
    return newsList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}