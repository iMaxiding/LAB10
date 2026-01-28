package com.example.project1.service;

import com.example.project1.dto.CreateNoteRequest;
import com.example.project1.dto.UpdateNoteRequest;
import com.example.project1.model.Note;
import com.example.project1.repository.NoteJdbcRepository;
import com.example.project1.repository.NoteRepository;
import com.example.project1.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteJdbcRepository noteJdbcRepository;
    private final CurrentUser currentUser;

    public NoteService(NoteRepository noteRepository,
                       NoteJdbcRepository noteJdbcRepository,
                       CurrentUser currentUser) {
        this.noteRepository = noteRepository;
        this.noteJdbcRepository = noteJdbcRepository;
        this.currentUser = currentUser;
    }

    public Note create(CreateNoteRequest req) {
        Long userId = currentUser.requireUserId();

        Note note = new Note();
        note.setTitle(req.title());
        note.setContent(req.content());
        note.setUserId(userId);

        return noteRepository.save(note);
    }

    //  używamy prepared statement przez JdbcTemplate
    public List<Note> listMine() {
        Long userId = currentUser.requireUserId();
        return noteJdbcRepository.findAllByUserIdPrepared(userId);
    }

    public Note getMine(Long id) {
        Long userId = currentUser.requireUserId();
        return noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }

    @Transactional
    public Note updateMine(Long id, UpdateNoteRequest req) {
        Note note = getMine(id); // tu już jest kontrola userId
        note.setTitle(req.title());
        note.setContent(req.content());
        return note; // @Transactional zapisze zmiany
    }

    @Transactional
    public void deleteMine(Long id) {
        Long userId = currentUser.requireUserId();
        noteRepository.deleteByIdAndUserId(id, userId); // usunie tylko moje
    }

}
