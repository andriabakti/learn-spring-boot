package id.learn.learnspringboot.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.learn.learnspringboot.config.JWTUtils;
import id.learn.learnspringboot.model.dto.JWTResponse;
import id.learn.learnspringboot.model.dto.ResponseDto;
import id.learn.learnspringboot.model.dto.UserDto;
import id.learn.learnspringboot.model.entity.User;
import id.learn.learnspringboot.respository.UserRepository;
import id.learn.learnspringboot.service.UserDetailsImpl;
import id.learn.learnspringboot.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JWTUtils jwtUtils;

  @GetMapping
  public ResponseEntity<Object> getUsers() {
    ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(userService.read(), HttpStatus.OK);
    return responseEntity;
  }

  @GetMapping("/active")
  public ResponseEntity<Object> getActiveUsers() {
    ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(userService.readActive(), HttpStatus.OK);
    return responseEntity;
  }

  @PostMapping
  public ResponseEntity<Object> createUser(@RequestBody UserDto dto) {
    try {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(userService.create(dto), HttpStatus.CREATED);
      return responseEntity;
    } catch (Exception e) {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      return responseEntity;
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUser(@RequestBody UserDto dto, @PathVariable Long id) {
    try {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(userService.update(dto, id), HttpStatus.OK);
      return responseEntity;
    } catch (Exception e) {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      return responseEntity;
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    try {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(userService.delete(id), HttpStatus.OK);
      return responseEntity;
    } catch (Exception e) {
      ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      return responseEntity;
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<Object> registration(@RequestBody UserDto dto) {
    ResponseDto<Object> response = new ResponseDto<>();

    // check user exist or not
    User user = userRepository.findByEmail(dto.getEmail());
    if (user != null) {
      response.setStatus(HttpStatus.EXPECTATION_FAILED);
      response.setMessage("Email is exist!");
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
    }

    // regist account
    try {
      Object userCreated = userService.create(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    } catch (Exception e) {
      response.setStatus(500);
      response.setMessage(e.getMessage());

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<Object> signIn(@RequestBody UserDto dto) {
    ResponseDto<JWTResponse> response = new ResponseDto<>();
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      // generate token
      String jwt = jwtUtils.generateJwtToken(authentication);
      // get user principal
      UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
      // get role
      Set<String> roles = userDetailsImpl.getAuthorities().stream().map(
        role -> role.getAuthority()).collect(Collectors.toSet());

      response.setStatus(200);
      response.setMessage("Login success!");
      response.setData(new JWTResponse(jwt, userDetailsImpl.getUsername(), roles));

      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      response.setStatus(500);
      response.setMessage(e.getMessage());

      return ResponseEntity.internalServerError().body(response);
    }
  }
}
