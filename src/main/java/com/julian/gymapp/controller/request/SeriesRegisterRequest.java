package com.julian.gymapp.controller.request;

import com.julian.gymapp.dto.SeriesRegisterDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesRegisterRequest {
  private List<SeriesRegisterDto> seriesRegisterDto;
}
