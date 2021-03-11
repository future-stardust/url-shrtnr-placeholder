package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.auth.MD5passwordEncoder;
import edu.kpi.testcourse.auth.UserCredentials;
import edu.kpi.testcourse.bigtable.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller that provides logic for user registration.
 */
@Secured(SecurityRule.IS_ANONYMOUS)
@Produces
@Controller
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

  @Inject
  private User user;

  /**
   * Registers user.
   */
  @Post(value = "/signup")
  public MutableHttpResponse<JsonError> signUp(@Body UserCredentials credentials) {
    logger.info("Sign up");

    if (user.get(credentials.getUsername()) != null) {
      return HttpResponse.unprocessableEntity().body(new JsonError("User already exists"));
    }
    String encodedPassword = MD5passwordEncoder.getSecurePassword(credentials.getPassword());
    user.put(credentials.getUsername(), encodedPassword);

    return HttpResponse.ok();
  }

}
