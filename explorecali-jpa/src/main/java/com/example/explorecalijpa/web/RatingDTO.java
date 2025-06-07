package com.example.explorecalijpa.web;

import com.example.explorecalijpa.model.TourRating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDTO {
  
  @Min(0) // minimum score is 0 
  @Max(5) // maximum score is 5
  private Integer score;

  @Size(max = 255) // maximun size of a comment
  private String comment;

  @NotNull // customer Id can not be null
  private Integer customerId;

  public RatingDTO(Integer score,String comment, Integer customerId) {
    this.score = score;
    this.comment = comment;
    this.customerId = customerId;
  }
  
  public RatingDTO(TourRating entity) {
    this.score = entity.getScore();
    this.comment = entity.getComment();
    this.customerId = entity.getCustomerId();
  }

  public RatingDTO() {
    
  }

}
