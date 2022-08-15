package id.learn.learnspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.learn.learnspringboot.model.dto.EducationDto;
import id.learn.learnspringboot.service.EdcationServiceImpl;

@RestController
@RequestMapping("educations")
public class EducationController {
  @Autowired
  private EdcationServiceImpl educationService;

  @PostMapping("/{userId}")
  public ResponseEntity<Object> insertEdu(@RequestBody EducationDto dto, @PathVariable Long userId) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(educationService.addEdu(dto, userId));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<Object> getAllEdu() {
    try {
      return ResponseEntity.ok().body(educationService.getEdu());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/{userId}")
  public ResponseEntity<Object> getEduByUser(@PathVariable Long userId) {
    try {
      return ResponseEntity.ok().body(educationService.getEduByUser(userId));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
