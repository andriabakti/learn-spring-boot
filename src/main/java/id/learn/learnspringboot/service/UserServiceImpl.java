package id.learn.learnspringboot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.learn.learnspringboot.model.dto.ResponseDto;
import id.learn.learnspringboot.model.dto.UserDto;
import id.learn.learnspringboot.model.entity.User;
import id.learn.learnspringboot.model.entity.UserDetail;
import id.learn.learnspringboot.respository.UserDetailRepository;
import id.learn.learnspringboot.respository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserDetailRepository detailRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Object create(UserDto dto) {
    User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword())); // instance object user
    userRepository.save(user); // save to db table users
    UserDetail detail = new UserDetail(dto.getFirstName(), dto.getLastName(), dto.getNik());
    detail.setUser(user);
    detailRepository.save(detail);

    // Map<String, Object> response = new HashMap<>(); // response api
    // response.put("message", "User berhasil ditambahkan!");
    // response.put("status", HttpStatus.CREATED);
    // response.put("data", user.getUsername());

    Map<String, Object> data = new HashMap<>();
    data.put("username", user.getUsername());
    data.put("email", user.getEmail());
    data.put("fullName", detail.getFirstName() + " " + detail.getLastName());

    ResponseDto<Object> response = new ResponseDto<>();
    response.setStatus(HttpStatus.CREATED);
    response.setMessage("User created!");
    response.setData(data);
    return response;
  }

  @Override
  public Object read() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.OK);
    response.put("data", userRepository.findAll());
    return response;
  }

  public Object readActive() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.OK);
    response.put("data", userRepository.findByIsDeleted(false));
    return response;
  }

  @Override
  public Object update(UserDto dto, Long id) {
    Map<String, Object> response = new HashMap<>();
    Optional<User> findUser = userRepository.findById(id);

    if (findUser.isEmpty()) {
      response.put("message", "User tidak ditemukan!");
      response.put("status", HttpStatus.NOT_FOUND);
      return response;
    }

    User user = findUser.get();
    if (dto.getUsername() != null) {
      user.setUsername(dto.getUsername());
    }

    if (dto.getEmail() != null) {
      user.setEmail(dto.getEmail());
    }

    if (dto.getPassword() != null) {
      user.setPassword(dto.getPassword());
    }
    userRepository.save(user);

    response.put("message", "User berhasil di-update!");
    response.put("status", HttpStatus.OK);
    response.put("data", user);
    return response;
  }

  @Override
  public Object delete(Long id) {
    Map<String, Object> response = new HashMap<>();
    Optional<User> findUser = userRepository.findById(id);

    if (findUser.isEmpty()) {
      response.put("message", "User tidak ditemukan!");
      response.put("status", HttpStatus.NOT_FOUND);
      return response;
    }

    User user = findUser.get();
    user.setDeleted(true);
    userRepository.save(user);

    response.put("message", "User berhasil dihapus!");
    response.put("status", HttpStatus.OK);
    return response;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User not found!");
    }
    return UserDetailsImpl.build(user);
  }
}
