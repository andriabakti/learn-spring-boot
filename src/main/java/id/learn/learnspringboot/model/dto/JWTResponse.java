package id.learn.learnspringboot.model.dto;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTResponse {
  private String token;
  private String email;
  private Set<String> role;
  private String type = "Bearer";

  public JWTResponse(String token, String email, Set<String> role) {
    this.token = token;
    this.email = email;
    this.role = role;
  }
}
