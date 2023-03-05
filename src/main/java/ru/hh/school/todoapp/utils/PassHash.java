package ru.hh.school.todoapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassHash {

  public static String getHash(String pass) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    messageDigest.update(pass.getBytes());
    return new String(messageDigest.digest());
  }
}
