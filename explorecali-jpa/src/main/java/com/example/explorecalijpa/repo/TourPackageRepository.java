package com.example.explorecalijpa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecalijpa.model.TourPackage;

public interface TourPackageRepository extends JpaRepository<TourPackage,String>{
      Optional<TourPackage> findByName(String name);
}
