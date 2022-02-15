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
        return (List<Note>) repository.findAll();
    }

    public void save(Note note){
        repository.save(note);
    }

    public Note get (String uuid) throws Exception {
        Optional<Note> optionalNote = repository.findById(uuid);
        if(optionalNote.isPresent()){
            return optionalNote.get();
        }
        throw new Exception("Note not found!");
    }

    public void delete (String id){
        repository.deleteById(id);
    }

}
