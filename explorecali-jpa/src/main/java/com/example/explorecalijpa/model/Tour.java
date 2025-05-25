package com.example.explorecalijpa.model;

import jakarta.persistence.*;

import java.util.Objects;

/*
 * The Tour contains all the attributes of an Explore California Tour.
 * 
 * Created by Satish Chanda
 */
@Entity
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String title;

  @Column(length = 2000)
  private String description;

  @Column(length = 2000)
  private String blurb;

  @Column
  private Integer price;

  @Column
  private String duration;

  @Column(length = 2000)
  private String bullets;

  @Column
  private String keywords;

  @ManyToOne
  @JoinColumn(name="tour_package_code")
  private TourPackage tourPackage;

  @Column
  @Enumerated(EnumType.STRING)
  private Difficulty difficulty;

  @Column
  private Region region;


  public Tour(String title, String description, String blurb, Integer price, String duration, String bullets,String keywords, TourPackage tourPackage, Difficulty difficulty, Region region) {
    this.title = title;
    this.description = description;
    this.blurb = blurb;
    this.price = price;
    this.duration = duration;
    this.bullets = bullets;
    this.keywords = keywords;
    this.tourPackage = tourPackage;
    this.difficulty = difficulty;
    this.region = region;
  }
  

  public Integer getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getDescription() {
    return this.description;
  }

  public String getBlurb() {
    return this.blurb;
  }

  public Integer getPrice() {
    return this.price;
  }

  public String getDuration() {
    return this.duration;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public TourPackage getTourPackage() {
    return this.tourPackage;
  }

  public Difficulty getDifficulty() {
    return this.difficulty;
  }

  public Region getRegion() {
    return this.region;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((blurb == null) ? 0 : blurb.hashCode());
    result = prime * result + ((price == null) ? 0 : price.hashCode());
    result = prime * result + ((duration == null) ? 0 : duration.hashCode());
    result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
    result = prime * result + ((tourPackage == null) ? 0 : tourPackage.hashCode());
    result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
    result = prime * result + ((region == null) ? 0 : region.hashCode());
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tour other = (Tour) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (blurb == null) {
      if (other.blurb != null)
        return false;
    } else if (!blurb.equals(other.blurb))
      return false;
    if (price == null) {
      if (other.price != null)
        return false;
    } else if (!price.equals(other.price))
      return false;
    if (duration == null) {
      if (other.duration != null)
        return false;
    } else if (!duration.equals(other.duration))
      return false;
    if (keywords == null) {
      if (other.keywords != null)
        return false;
    } else if (!keywords.equals(other.keywords))
      return false;
    if (tourPackage == null) {
      if (other.tourPackage != null)
        return false;
    } else if (!tourPackage.equals(other.tourPackage))
      return false;
    if (difficulty != other.difficulty)
      return false;
    if (region != other.region)
      return false;
    return true;
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", title='" + getTitle() + "'" +
      ", description='" + getDescription() + "'" +
      ", blurb='" + getBlurb() + "'" +
      ", price='" + getPrice() + "'" +
      ", duration='" + getDuration() + "'" +
      ", keywords='" + getKeywords() + "'" +
      ", tourPackage='" + getTourPackage() + "'" +
      ", difficulty='" + getDifficulty() + "'" +
      ", region='" + getRegion() + "'" +
      "}";
  }
  
  
}