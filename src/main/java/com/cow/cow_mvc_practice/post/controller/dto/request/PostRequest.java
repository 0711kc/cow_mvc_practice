package com.cow.cow_mvc_practice.post.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
  private String title;
  private String content;
}