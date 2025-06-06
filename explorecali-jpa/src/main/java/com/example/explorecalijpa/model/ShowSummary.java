package com.example.explorecalijpa.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="showSummary", types = {Tour.class})
public interface ShowSummary {
  String getTitle();
  String getDescription();
  Integer getPrice();
}
