package com.example.explorecalijpa.model;

public enum Region {
    Central_Coast("Central Coast"),
    Southern_Clifornia("Southern California"),
    North_California("Northern California"),
    Varies("Varies");

    private String name;

    private Region(String name) {
      this.name = name;
    }

    public static Region findByName(String name) {
        for(Region r : Region.values()) {
          if(r.name.equalsIgnoreCase(name)) {
              return r;
          }
        }
        return null;
    }

    public String getName() {
      return name;
    }
}
