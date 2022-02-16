package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
