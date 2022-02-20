package ua.goit.notes.note;

import ua.goit.notes.users.UserDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> getAll();
    NoteDto get(String uuid);
    void create(NoteDto dto);
    void update(String uuid, NoteDto dto);
    void delete(String uuid);

}
