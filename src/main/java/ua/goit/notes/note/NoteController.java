package ua.goit.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping("/notes")
    public String showNotes(Model model){
        List<Note> noteList = service.getAll();
        model.addAttribute("noteList", noteList);
        return "notes";
    }

    @GetMapping("/notes/new")
    public String getCreateForm(Model model){
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/notes/save")
    public String saveNote (Note note){
        service.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/notes/edit/{id}")
    public String showEditForm(@PathVariable("id") String uuid, Model model){
        try {
            Note note = service.get(uuid);
            model.addAttribute("note", note);
            return "note_form";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/notes";
        }
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable("uuid") String id){
        service.delete(id);
        return "redirect:/notes";
    }

}
