package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="news")
@NoArgsConstructor
@AllArgsConstructor
public class News {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="news_id", nullable = false)
  private Long newsId;
  @Column(name="title", nullable = false)
  private String title;
  @Column(name="subtitle", nullable = false)
  private String subtitle;
  @Column(name="content", nullable = false)
  private String content;
  @Column(name="image_url")
  private String imageUrl;
  @Column(name="link")
  private String link;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
  @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
  private boolean isActive;
}
