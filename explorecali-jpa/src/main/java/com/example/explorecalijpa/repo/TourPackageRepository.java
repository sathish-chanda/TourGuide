package com.example.explorecalijpa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.explorecalijpa.model.TourPackage;

//Overrides default path from "/tourPackage" to "/package".
@RepositoryRestResource(path="package", collectionResourceRel="package")
public interface TourPackageRepository extends JpaRepository<TourPackage,String> {
      Optional<TourPackage> findByName(String name);
}
