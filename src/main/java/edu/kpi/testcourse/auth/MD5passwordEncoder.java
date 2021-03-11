package edu.kpi.testcourse.auth;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
/**
 * simple MD5 *salted* password hashing.
 */
public class MD5passwordEncoder {
//{
//  String passwordToHash = "password";
//
//  String text = "83ee5baeea20b6c";
//  byte[] salt = text.getBytes();
//
//  String securePassword = getSecurePassword(passwordToHash);
//  System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66
//
//  String regeneratedPasswordToVerify = getSecurePassword(passwordToHash);
//  System.out.println(regeneratedPasswordToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66
//}

  public static String getSecurePassword(String passwordToHash)
  {
    String text = "83ee5baeea20b6c";
    byte[] salt = text.getBytes();
    String generatedPassword = null;
    try {
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");
      //Add password bytes to digest
      md.update(salt);
      //Get the hash's bytes
      byte[] bytes = md.digest(passwordToHash.getBytes());
      //This bytes[] has bytes in decimal format;
      //Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++)
      {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      //Get complete hashed password in hex format
      generatedPassword = sb.toString();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

  public static boolean validatePassword(String passwordToCheck, String storedPassword) {
    String inputPassword = getSecurePassword(passwordToCheck);
    return (inputPassword.equals(storedPassword));
  }
}
