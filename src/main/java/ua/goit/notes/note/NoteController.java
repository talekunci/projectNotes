package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/note")
public class NoteController {

    private NoteServiceImpl service;

    @Autowired
    public void setService(NoteServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<NoteDto> showNotes() {
        return service.getAll();
    }

    @GetMapping("/{uuid}")
    public NoteDto getNote(@PathVariable UUID uuid) {
        return service.get(uuid);
    }

    @PostMapping
    public void createNote(@Valid @RequestBody NoteDto note) {
        service.create(note);
    }

    @PutMapping("/{uuid}")
    public void updateNote(@PathVariable("uuid") UUID uuid,
                           @RequestBody NoteDto dto) {
        service.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteNote(@PathVariable UUID uuid) {
        service.delete(uuid);
    }

    @GetMapping("/share/{uuid}")
    public NoteDto shareNote(@PathVariable("uuid") UUID uuid) {
        NoteDto note = service.get(uuid);

        if (note.getAccess().equals(AccessTypes.PRIVATE)) {
            note = null;
        }

        return note;
    }

}
