package edu.kpi.testcourse.bigtable;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
class UserImpl implements User {

  private final Map<String, String> map = new HashMap<>();

  @Override
  public void put(String email, String password) {
    map.put(email, password);
  }

  @Override
  public String get(String email) {
    return map.get(email);
  }

}
