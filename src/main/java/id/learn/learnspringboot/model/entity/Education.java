package id.learn.learnspringboot.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "educations")
@Data
@NoArgsConstructor
public class Education {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private String level;

  @Column(length = 100)
  private String schoolName;

  @Column
  private String graduateYear;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Education(String level, String schoolName, String graduateYear) {
    this.level = level;
    this.schoolName = schoolName;
    this.graduateYear = graduateYear;
  }
}
