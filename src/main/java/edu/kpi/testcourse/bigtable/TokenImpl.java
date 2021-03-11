package edu.kpi.testcourse.bigtable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
class TokenImpl implements Token {

  private final Map<String, Set<String>> map = new HashMap<>();

  @Override
  public void add(String email, String invalidToken) {
    Set<String> previousList = map.computeIfAbsent(email, k -> new HashSet<>());
    previousList.add(invalidToken);
  }

  @Override
  public void remove(String email, String invalidToken) {
    if (map.containsKey(email)) {
      map.get(email).remove(invalidToken);
    }
  }

  @Override
  public Set<String> get(String email) {
    return map.get(email);
  }

  @Override
  public Map<String, Set<String>> getAll() {
    return map;
  }

}
