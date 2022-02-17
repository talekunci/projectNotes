package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public List<Note> getAll(){
        return repository.findAll();
    }

    public void create(Note note){
        repository.save(note);
    }

    public Note get (String uuid){
        return repository.findById(uuid).orElseThrow();
    }

    public void delete (String uuid){
        repository.deleteById(uuid);
    }

    public void update(String uuid, NoteDto dto) {
        repository.findById(uuid)
                .map(note -> {
                    if (StringUtils.hasText(dto.getName())){
                        note.setName(dto.getName());
                    }
                    if (StringUtils.hasText(dto.getBody())){
                        note.setBody(dto.getBody());
                    }
                    if (StringUtils.hasText(String.valueOf(dto.getAccess()))){
                        note.setAccess(dto.getAccess());
                    }
                    return note;
                }).ifPresent(note -> {
                    repository.save(note);
                });
    }
}
