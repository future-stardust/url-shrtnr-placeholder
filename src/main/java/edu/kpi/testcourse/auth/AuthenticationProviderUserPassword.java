package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.bigtable.User;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Micronaut authentication bean that contains authorization logic: ensures that a user is
 * registered in the system and password is right.
 */
@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  private static final Logger logger = LoggerFactory
    .getLogger(AuthenticationProviderUserPassword.class);

  @Inject
  private User userDao;

  @Override
  public Publisher<AuthenticationResponse> authenticate(
    @Nullable HttpRequest<?> httpRequest,
    AuthenticationRequest<?, ?> authenticationRequest
  ) {
    logger.info("Authenticate user");

    return Flowable.create(emitter -> {
      String storedPassword = userDao.get(String.valueOf(authenticationRequest.getIdentity()));
      String requestPassword = String.valueOf(authenticationRequest.getSecret());

      if (storedPassword != null
        && MD5passwordEncoder.validatePassword(requestPassword, storedPassword)
      ) {
        emitter
          .onNext(new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
        emitter.onComplete();
      } else {
        emitter.onError(new AuthenticationException(new AuthenticationFailed(
          "No user found with such credentials")));
      }
    }, BackpressureStrategy.ERROR);
  }
}
