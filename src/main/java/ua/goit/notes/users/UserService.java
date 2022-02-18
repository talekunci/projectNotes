package ua.goit.notes.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;

    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto get(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElseThrow();
    }

    public UserDto getByName(String name) {
        User byName = repository.findByName(name);

        return byName == null ? mapToDto(byName) : null;
    }

    @Transactional
    public void create(UserDto dto) {
        User user = mapToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);
    }

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

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private User mapToUser(UserDto dto) {
        return mapper.map(dto, User.class);
    }

    private UserDto mapToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

}
