package ua.goit.notes.note;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    List<NoteDto> getAll();

    NoteDto get(UUID uuid);

    void create(NoteDto dto);

    void update(UUID uuid, NoteDto dto);

    void delete(UUID uuid);

}
