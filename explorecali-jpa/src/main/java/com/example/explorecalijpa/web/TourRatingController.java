package com.example.explorecalijpa.web;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.model.TourRating;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
  private TourRatingService tourRatingService;

  public TourRatingController(TourRatingService tourRatingService) {
    this.tourRatingService =  tourRatingService;
  }
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RatingDTO createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDTO ratingDTO) {
    log.info("POST /tours/{}/ratings", tourId);
    TourRating tourRating = tourRatingService.createNew(tourId, ratingDTO.getCustomerId(), ratingDTO.getScore(), ratingDTO.getComment());
    return new RatingDTO(tourRating); 
  }

  // find all the ratings for the tour
  @GetMapping
  public List<RatingDTO> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
    log.info("GET /tours/{}/ratings",tourId);
    List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
    return tourRatings.stream().map(RatingDTO::new).toList();
  }  

  // find the average rating for all the tours
  @GetMapping("/average")
  public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
    log.info("GET /tours/{}/ratings/average",tourId);
    return Map.of("average",tourRatingService.getAverageScore(tourId));
  }

  @PutMapping
  public RatingDTO updateWithPut(@PathVariable(value = "tourId") int tourId,@RequestBody @Valid RatingDTO ratingDTO) {
    log.info("PUT /tours/{}/ratings",tourId);
    return new RatingDTO(tourRatingService.update(tourId,ratingDTO.getCustomerId(), ratingDTO.getScore(), ratingDTO.getComment()));
  }
 
  @DeleteMapping("{customerId}")
  public void delete(@PathVariable(value = "tourId") int tourId,@PathVariable(value = "customerId") int customerId) {
    log.info("DELETE /tours/{}/ratings/{}",tourId, customerId);
    tourRatingService.delete(tourId, customerId);
  }

  @PatchMapping
  public RatingDTO updateSomeWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDTO ratingDTO) {
    log.info("PATCH /tours/{}/ratings",tourId);
    return new RatingDTO(tourRatingService.updateSome(tourId, ratingDTO.getCustomerId(), Optional.ofNullable(ratingDTO.getScore()), Optional.ofNullable(ratingDTO.getComment()))); 
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String return404(NoSuchElementException exception) {
    return exception.getMessage();
  }

    /**
   * Create Several Tour Ratings for one tour, score and several customers.
   *
   * @param tourId
   * @param score
   * @param customers
   */
  @PostMapping("/batch")
  @ResponseStatus(HttpStatus.CREATED)
  public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                    @RequestParam(value = "score") int score,
                                    @RequestBody List<Integer> customers) {
    log.info("BATCH /tours/{}/ratings/batch",tourId);
    tourRatingService.rateMany(tourId, score, customers);
  }
}
