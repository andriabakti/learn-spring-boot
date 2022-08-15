package id.learn.learnspringboot.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.learn.learnspringboot.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  List<User> findByIsDeleted(boolean isDeteled);
  User findByEmail(String email);
}
