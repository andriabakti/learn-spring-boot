package id.learn.learnspringboot.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
public class UserDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String firstName;

  @Column(length = 50)
  private String lastName;

  @Column(length = 16, unique = true)
  private String nik;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public UserDetail(String firstName, String lastName, String nik) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.nik = nik;
  }
}
