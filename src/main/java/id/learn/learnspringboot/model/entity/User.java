package id.learn.learnspringboot.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 25, unique = true, nullable = false)
  private String username;

  @Column(length = 100, unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column()
  private boolean isDeleted = false;

  // constructor
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
