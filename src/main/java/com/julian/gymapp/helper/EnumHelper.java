package com.julian.gymapp.helper;

public class EnumHelper {
  public static <T extends Enum<T>> T fromString(String str, Class<T> enumClass) {
    try {
      str = str.replace(" ","");
      return Enum.valueOf(enumClass, str);
    } catch (IllegalArgumentException | NullPointerException ex) {
      throw new IllegalArgumentException("Invalid value: " + str + " for enum class " + enumClass.getSimpleName());
    }
  }
}
