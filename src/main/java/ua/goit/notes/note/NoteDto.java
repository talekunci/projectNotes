package ua.goit.notes.note;

import lombok.Data;

@Data
public class NoteDto {

    private String uuid;
    private String name;
    private String body;
    private AccessTypes access;

}
