package edu.kpi.testcourse.hashing;

import edu.kpi.testcourse.auth.MD5passwordEncoder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordHashTest {

  @Test
  void checkHashing() {

    String passwordToHash = "password";

    String securePassword = MD5passwordEncoder.getSecurePassword(passwordToHash);
    String regeneratedPasswordToVerify = MD5passwordEncoder.getSecurePassword(passwordToHash);
    assertThat(securePassword).isEqualTo(regeneratedPasswordToVerify);
  }
}

