package com.example.tourservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {
 
  @Bean
  public TourRepository tourRepository() {
    return new TourRepository();
  }
  @Bean
  public TourManagementService tourManagementService() {
    return new TourManagementService(tourRepository());
  }

  @Bean
  public TravelAgentService travelAgentService() {
    return new TravelAgentService(tourRepository());
  } 
}
