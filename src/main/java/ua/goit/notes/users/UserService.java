package ua.goit.notes.users;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();
    UserDto get(Long id);
    void create(UserDto dto);
    void update(Long id, UserDto dto);
    void delete(Long id);

}
