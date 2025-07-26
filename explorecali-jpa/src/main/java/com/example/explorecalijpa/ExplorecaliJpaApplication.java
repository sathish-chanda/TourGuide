package com.example.explorecalijpa;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.explorecalijpa.business.TourPackageService;
import com.example.explorecalijpa.business.TourService;
import com.example.explorecalijpa.model.Difficulty;
import com.example.explorecalijpa.model.Region;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ExplorecaliJpaApplication implements CommandLineRunner {
	private final String TOUR_IMPORT_FILE = "ExploreCalifornia.json";
	
	@Bean
	public OpenAPI swaggerHeader() {
		return new OpenAPI()
							.info(new Info()
							.description("Services for the Explore California Relational Database.")
							.title(StringUtils.substringBefore(getClass().getSimpleName(), "$"))
							.version("3.0.0"));
	}
	@Autowired
	private TourPackageService tourPackageService;
	
	@Autowired
	private TourService tourService;
	
	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createTourAllPackages();
		System.out.println("Persisted packages = " + tourPackageService.total());
		createToursFromFile(TOUR_IMPORT_FILE);
		System.out.println("Persisted Tours = " + tourService.total());
		System.out.println("Test lookupByDifficulty : EASY");
		tourService.lookupByDifficulty(Difficulty.Easy).forEach(System.out::println);
		System.out.println("Test lookupByPackageCode : BC");
		tourService.lookupByPackage("BC").forEach(System.out::println);
	}

	/**
	 * Initialize all the known tour packages
	 */
	private void createTourAllPackages() {
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");
	}

	/*
	 * Create tour entities from an external file
	 */
	private void createToursFromFile(String fileToImport) throws IOException {
		TourFromFile.read(fileToImport).forEach(t -> tourService.createTour(t.packageName(), t.title(), t.description(), t.blurb(), t.price(), t.length(), t.bullets(), t.keywords(), Difficulty.valueOf(t.difficulty()), Region.findByName(t.region())));
	}

	/*
	 * Helper to import ExploreCalifornia.json
	 */
	record TourFromFile(String packageName,String title, String description, String blurb,Integer price,String length,String bullets,String keywords,String difficulty,String region) {
		static List<TourFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().readValue(new File(fileToImport),new TypeReference<List<TourFromFile>>(){});
		}
	}
}
