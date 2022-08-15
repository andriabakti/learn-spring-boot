package id.learn.learnspringboot.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.learn.learnspringboot.model.entity.Education;
import id.learn.learnspringboot.model.entity.User;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>  {
  List<Education> findByUser(User user);
}
