package ua.goit.notes.note;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository repository;
    private ModelMapper mapper;

    @Autowired
    public void setRepository(NoteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<NoteDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto get(String uuid) {
        return repository.findById(uuid)
                .map(this::mapToDto)
                .orElseThrow();
    }

    @Override
    public void create(NoteDto dto) {
        dto.setUuid(UUID.randomUUID().toString());

        repository.save(mapToNote(dto));
    }

    @Override
    public void delete(String uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void update(String uuid, NoteDto dto) {
        repository.findById(uuid)
                .map(note -> {
                    if (StringUtils.hasText(dto.getName())) {
                        note.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getBody())) {
                        note.setBody(dto.getBody());
                    }
                    if (StringUtils.hasText(String.valueOf(dto.getAccess()))) {
                        note.setAccess(dto.getAccess());
                    }
                    return note;
                }).ifPresent(note -> {
                    repository.save(note);
                });
    }

    private Note mapToNote(NoteDto dto) {
        return mapper.map(dto, Note.class);
    }

    private NoteDto mapToDto(Note note) {
        return mapper.map(note, NoteDto.class);
    }
}
