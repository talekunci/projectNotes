package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping
    public List<NoteDto> showNotes() {
        return service.getAll();
    }

    @GetMapping("/{uuid}")
    public NoteDto getNote(@PathVariable String uuid) {
        return service.get(uuid);
    }

    @PostMapping
    public void createNote(@Valid @RequestBody NoteDto note) {
        service.create(note);
    }

    @PutMapping("/{uuid}")
    public void updateNote(@PathVariable("uuid") String uuid,
                           @RequestBody NoteDto dto) {
        service.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteNote(@PathVariable String uuid) {
        service.delete(uuid);
    }

    @GetMapping("/share/{uuid}")
    public NoteDto shareNote(@PathVariable("uuid") String uuid) {
        NoteDto note = service.get(uuid);

        if (note.getAccess().equals(AccessTypes.PRIVATE)) {
            note = null;
        }

        return note;
    }

}
