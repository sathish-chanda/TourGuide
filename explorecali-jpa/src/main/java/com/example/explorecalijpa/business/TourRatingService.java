package com.example.explorecalijpa.business;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.repo.TourRatingRepository;
import com.example.explorecalijpa.repo.TourRepository;

import jakarta.transaction.Transactional; 

import jakarta.validation.ConstraintViolationException;

@Service
@Transactional
public class TourRatingService {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
      this.tourRatingRepository = tourRatingRepository;
      this.tourRepository = tourRepository;
    }

    /**
     * Create a new Tour Rating in the database
     * 
     * @param tourId 
     * @param customerId
     * @param score
     * @param comment
     * @throws NoSuchElementException if no Tour found.
     * @return created entity
     */
    public TourRating createNew(int tourId,int customerId,Integer score,String comment) throws NoSuchElementException {
      if(tourRatingRepository.findByTourIdAndCustomerId(tourId,customerId).isPresent()) {
        throw new ConstraintViolationException("Unable to create duplicate ratings",null); 
      }
      return tourRatingRepository.save(new TourRating(verifyTour(tourId),customerId, score, comment));
    }

    /**
     * Verify and return the Tour given  tour Id.
     * 
     * @param tourId 
     * @return the found Tour
     * @throws NoSuchElementException if no tour found.
     */
    private Tour verifyTour(int tourId) {
      return tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
    }

    /**
     * Get rating by id
     * 
     * @param id
     * @return TourRating by the given id.
     */
    public Optional<TourRating> lookupRatingById(int id) {
      return tourRatingRepository.findById(id);
    }

    /**
     * Get All ratings
     * 
     * @return list of ratings
     */
    public List<TourRating> lookupAll() {
      return tourRatingRepository.findAll();
    }

    /**
     * Get a page of tour ratings for a tour
     * 
     * @param tourId
     * @param pageable page parameters to determine which element to fetch
     * @return Page of TourRatings
     * @throws NoSuchElementException if tour not found.
     */
    public List<TourRating> lookupRatings(int tourId) throws NoSuchElementException {
      return tourRatingRepository.findByTourId(verifyTour(tourId).getId());
    }

    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
      TourRating rating = verifyTourRating(tourId,customerId);
      rating.setScore(score);
      rating.setComment(comment);
      return tourRatingRepository.save(rating);
    }

    public TourRating updateSome(int tourId,Integer customerId, Optional<Integer> score, Optional<String> comment) throws NoSuchElementException {
      TourRating rating = verifyTourRating(tourId, customerId);
      score.ifPresent(s -> rating.setScore(s));
      comment.ifPresent(c -> rating.setComment(c));
      return tourRatingRepository.save(rating);
    }

    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
      TourRating rating = verifyTourRating(tourId, customerId);
      tourRatingRepository.delete(rating);
    }

    public Double getAverageScore(int tourId) throws NoSuchElementException {
      List<TourRating> ratings = lookupRatings(tourId);
      OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
      return average.isPresent() ? average.getAsDouble() : null;
    }

    public TourRating verifyTourRating(int tourId,int customerId) throws NoSuchElementException {
      return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request "+ tourId + " for customer " + customerId + " is not found."));
    }

    public void rateMany(int tourId, int score, List<Integer> customers) {
      Tour tour = verifyTour(tourId);
      for(Integer cId : customers) {
        if(tourRatingRepository.findByTourIdAndCustomerId(tourId,cId).isPresent()) {
           throw new ConstraintViolationException("Unable to create duplicate ratings",null); 
        }
        tourRatingRepository.save(new TourRating(tour,cId,score));
      }

    }
}
