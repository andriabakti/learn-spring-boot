package id.learn.learnspringboot.service;

import id.learn.learnspringboot.model.dto.UserDto;

public interface UserService {
  public Object create(UserDto dto);

  public Object read();

  public Object update(UserDto dto, Long id);

  public Object delete(Long id);
}
