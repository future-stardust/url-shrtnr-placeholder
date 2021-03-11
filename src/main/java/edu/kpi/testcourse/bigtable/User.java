package edu.kpi.testcourse.bigtable;

/**
 * DAO for users.
 */
public interface User {

  void put(String email, String password);

  String get(String email);

}
