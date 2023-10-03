package com.julian.gymapp.dto;

import lombok.Data;

@Data
public class NewsDto {
    private Long newsId;
    private String title;
    private String subtitle;
    private String content;
    private String imageUrl;
    private String link;
}
