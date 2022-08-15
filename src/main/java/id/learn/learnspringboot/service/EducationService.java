package id.learn.learnspringboot.service;

import id.learn.learnspringboot.model.dto.EducationDto;
import id.learn.learnspringboot.model.dto.ResponseDto;

public interface EducationService {
  public ResponseDto<Object> addEdu(EducationDto dto, Long userId);
  public ResponseDto<Object> getEdu();
  public ResponseDto<Object> getEduByUser(Long userId);
}
