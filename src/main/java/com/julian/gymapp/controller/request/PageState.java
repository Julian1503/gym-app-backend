package com.julian.gymapp.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Data
public class PageState {
  private int page = 1;
  private int size = 5;
  private String sort;
  private String direction = Sort.Direction.ASC.toString();
}
