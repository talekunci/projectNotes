package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping
    public String showNotes(Model model){
        List<Note> noteList = service.getAll();
        model.addAttribute("noteList", noteList);
        return "notes";
    }

    @GetMapping("/new")
    public String getCreateForm(Model model){
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/create")
    public String createNote (Note note){
        service.create(note);
        return "redirect:/notes";
    }

    @PutMapping("/{uuid}")
    public void updateNote(@PathVariable("uuid") String uuid, @RequestBody NoteDto dto){
        service.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteNote(@PathVariable String uuid){
        service.delete(uuid);
    }

    @GetMapping("/share/{uuid}")
    public String shareNote (@PathVariable("uuid") String uuid, Model model){
        Note note = service.get(uuid);
        if (note!=null && note.getAccess().equals(AccessTypes.PUBLIC)){
            model.addAttribute("note", note);
        }
        if (note!=null && note.getAccess().equals(AccessTypes.PUBLIC)){
            return "redirect:/error";
        }
        return "shared_note";
    }

}
