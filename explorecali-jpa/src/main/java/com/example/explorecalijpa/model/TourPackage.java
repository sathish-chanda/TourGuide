package com.example.explorecalijpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tour_package")
@Entity
public class TourPackage {
  
    @Id
    private String code;

    @Column
    private String name;

    protected TourPackage() {
    } 
    
    public TourPackage(String code, String name) {
      this.code = code;
      this.name = name;
    }

    public String getCode() {
      return code;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "TourPackage [code=" + code + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((code == null) ? 0 : code.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
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
      TourPackage other = (TourPackage) obj;
      if (code == null) {
        if (other.code != null)
          return false;
      } else if (!code.equals(other.code))
        return false;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      return true;
    }

    

}
