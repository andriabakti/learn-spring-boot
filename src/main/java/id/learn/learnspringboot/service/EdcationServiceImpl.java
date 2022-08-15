package id.learn.learnspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.learn.learnspringboot.model.dto.EducationDto;
import id.learn.learnspringboot.model.dto.ResponseDto;
import id.learn.learnspringboot.model.entity.Education;
import id.learn.learnspringboot.model.entity.User;
import id.learn.learnspringboot.respository.EducationRepository;
import id.learn.learnspringboot.respository.UserRepository;

@Service
@Transactional
public class EdcationServiceImpl implements EducationService {
  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private UserRepository userRepository;

  ResponseDto<Object> response = new ResponseDto<>();

  @Override
  public ResponseDto<Object> addEdu(EducationDto dto, Long userId) {
    Education education = new Education(dto.getEduLevel(), dto.getSchName(), dto.getGradYear());
    //  find user
    User user = userRepository.findById(userId).get();
    education.setUser(user);

    // save to db table edu
    educationRepository.save(education);
    // create response object
    response.setStatus(HttpStatus.CREATED);
    response.setMessage("Education added!");
    response.setData(education.getSchoolName());
    return response;
  }

  @Override
  public ResponseDto<Object> getEdu() {
    response.setStatus(200);
    response.setData(educationRepository.findAll());
    return response;
  }

  @Override
  public ResponseDto<Object> getEduByUser(Long userId) {
    // find edu by user
    User user = userRepository.findById(userId).get();
    response.setStatus(200);
    response.setData(educationRepository.findByUser(user));
    return response;
  }
}
