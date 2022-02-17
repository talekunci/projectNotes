package ua.goit.notes.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void update(Long id, UserDto dto) {
        repository.findById(id)
                .map(user -> {
                    if (StringUtils.hasText(dto.getName())) {
                        user.setName(dto.getName());
                    }
                    return user;
                }).ifPresent(user -> {
                    repository.save(user);
                });
    }

}
