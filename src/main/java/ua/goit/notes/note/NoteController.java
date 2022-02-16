package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/edit/{uuid}")
    public String showEditForm(@PathVariable("uuid") String uuid, Model model){
        try {
            Note note = service.get(uuid);
            model.addAttribute("note", note);
            return "note_form";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/notes";
        }
    }

    @GetMapping("/delete/{uuid}")
    public String deleteNote(@PathVariable("uuid") String uuid){
        service.delete(uuid);
        return "redirect:/notes";
    }

}
