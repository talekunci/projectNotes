package ua.goit.notes.note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository <Note, String> {
}
