package ua.goit.notes.note;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<Note> getAll() {
        return repository.findAll();
    }

    public NoteDto get(String uuid) {
        return repository.findById(uuid)
                .map(this::mapToDto)
                .orElseThrow();
    }

    public void create(NoteDto dto) {
        dto.setUuid(UUID.randomUUID().toString());

        repository.save(mapToNote(dto));
    }

    public void delete(String uuid) {
        repository.deleteById(uuid);
    }

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
