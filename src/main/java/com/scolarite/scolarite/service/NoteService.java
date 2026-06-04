package com.scolarite.scolarite.service;

import com.scolarite.scolarite.entities.Etudiant;
import com.scolarite.scolarite.entities.Module;
import com.scolarite.scolarite.entities.Note;
import com.scolarite.scolarite.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantService etudiantService;
    private final ModuleService moduleService;

    public NoteService(NoteRepository noteRepository,
                       EtudiantService etudiantService,
                       ModuleService moduleService) {
        this.noteRepository = noteRepository;
        this.etudiantService = etudiantService;
        this.moduleService = moduleService;
    }

    public Note ajouterNote(Long etudiantId, Long moduleId, double valeur, String annee) {
        Etudiant etudiant = etudiantService.findById(etudiantId);
        Module module = moduleService.findById(moduleId);

        Note note = new Note();
        note.setEtudiant(etudiant);
        note.setModule(module);
        note.setNote(valeur);
        note.setAnnee(annee);

        return noteRepository.save(note);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable avec l'id : " + id));
    }

    public Note modifierNote(Long noteId, double nouvelleValeur) {
        Note note = findById(noteId);
        note.setNote(nouvelleValeur);
        return noteRepository.save(note);
    }

    public void supprimerNote(Long noteId) {
        findById(noteId);
        noteRepository.deleteById(noteId);
    }

    public List<Note> findNotesParEtudiant(Long etudiantId) {
        etudiantService.findById(etudiantId);
        return noteRepository.findByEtudiantId(etudiantId);
    }

    public List<Note> findNotesParEtudiantEtSemestre(Long etudiantId, int semestre) {
        etudiantService.findById(etudiantId);
        return noteRepository.findByEtudiantIdAndModuleSemestre(etudiantId, semestre);
    }

    /**
     * Calcule la moyenne générale d'un étudiant (pondérée par les coefficients).
     */
    public double calculerMoyenneGenerale(Long etudiantId) {
        List<Note> notes = findNotesParEtudiant(etudiantId);
        if (notes.isEmpty()) {
            return 0.0;
        }
        double sommeNotesPonderees = 0;
        double sommeCoefficients = 0;
        for (Note n : notes) {
            double coeff = n.getModule().getCoefficient();
            sommeNotesPonderees += n.getNote() * coeff;
            sommeCoefficients += coeff;
        }
        if (sommeCoefficients == 0) return 0.0;
        return Math.round((sommeNotesPonderees / sommeCoefficients) * 100.0) / 100.0;
    }

    /**
     * Calcule la moyenne d'un étudiant pour un semestre donné.
     */
    public double calculerMoyenneParSemestre(Long etudiantId, int semestre) {
        List<Note> notes = findNotesParEtudiantEtSemestre(etudiantId, semestre);
        if (notes.isEmpty()) {
            return 0.0;
        }
        double sommeNotesPonderees = 0;
        double sommeCoefficients = 0;
        for (Note n : notes) {
            double coeff = n.getModule().getCoefficient();
            sommeNotesPonderees += n.getNote() * coeff;
            sommeCoefficients += coeff;
        }
        if (sommeCoefficients == 0) return 0.0;
        return Math.round((sommeNotesPonderees / sommeCoefficients) * 100.0) / 100.0;
    }
}
