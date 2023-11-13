package com.julian.gymapp.helper;

import java.util.UUID;

public class StringHelper {
  public static String generateRandomString() {
    return UUID.randomUUID().toString();
  }
}
