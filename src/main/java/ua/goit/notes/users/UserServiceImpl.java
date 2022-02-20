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
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper mapper;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
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
    @Override
    public void create(UserDto dto) {
        User user = mapToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);
    }

    @Override
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

    @Override
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
