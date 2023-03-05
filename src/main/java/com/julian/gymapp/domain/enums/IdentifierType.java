package com.julian.gymapp.domain.enums;

public enum IdentifierType {
  Passport("Passport"), NationalID("National ID"), DriverLicense("Driver License");
  private String description;

  IdentifierType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
