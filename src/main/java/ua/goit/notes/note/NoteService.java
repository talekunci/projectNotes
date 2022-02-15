package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public List<Note> getAll(){
        return repository.findAll();
    }

    public void save(Note note){
        repository.save(note);
    }

    public Note get (String uuid){
        return repository.findById(uuid).orElseThrow();
    }

    public void delete (String id){
        repository.deleteById(id);
    }

}
