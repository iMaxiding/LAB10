package com.example.project1.controller;

import com.example.project1.dto.CreateNoteRequest;
import com.example.project1.dto.UpdateNoteRequest;
import com.example.project1.model.Note;
import com.example.project1.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("notes", noteService.listMine());
        model.addAttribute("createNoteRequest", new CreateNoteRequest("", ""));
        return "notes/list";
    }

    // CREATE (z formularza)
    @PostMapping
    public String create(@Valid @ModelAttribute CreateNoteRequest createNoteRequest,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("notes", noteService.listMine());
            return "notes/list";
        }
        noteService.create(createNoteRequest);
        return "redirect:/notes";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Note note = noteService.getMine(id);
        model.addAttribute("noteId", id);
        model.addAttribute("updateNoteRequest", new UpdateNoteRequest(note.getTitle(), note.getContent()));
        return "notes/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute UpdateNoteRequest updateNoteRequest,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("noteId", id);
            return "notes/edit";
        }
        noteService.updateMine(id, updateNoteRequest);
        return "redirect:/notes";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        noteService.deleteMine(id);
        return "redirect:/notes";
    }
}

