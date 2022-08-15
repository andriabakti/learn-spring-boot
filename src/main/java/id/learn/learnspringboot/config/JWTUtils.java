package id.learn.learnspringboot.config;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import id.learn.learnspringboot.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {
  private String jwtSecret = "secret-key";
  private Integer jwtExpirationMS = 90000;

  // generate the token
  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder().setSubject(userPrincipal.getUsername())
    .setIssuedAt(new Date())
    .setExpiration(new Date(new Date().getTime() + jwtExpirationMS))
    .signWith(SignatureAlgorithm.HS512, jwtSecret)
    .compact();
  }

  // retrieve username from token
  public String getUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  // validate the token
  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (Exception e) {
      e.printStackTrace();;
      return false;
    }
  }
}
