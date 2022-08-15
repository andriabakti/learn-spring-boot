package id.learn.learnspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GreetingController {

  @GetMapping("/world")
  public String hello() {
    return "Hello World from Spring Boot App!";
  }

  @GetMapping
  public String hello(String name) {
    return "Hello " + name + "!";
  }

  @GetMapping("/name")
  public String hello(@RequestParam String firstName, @RequestParam String lastName) {
    return "Hello " + firstName + " " + lastName + "!";
  }

  @GetMapping("/get")
  public String helloName(@RequestBody String fullName) {
    return "Hello kamu, " + fullName + "!";
  }
}
