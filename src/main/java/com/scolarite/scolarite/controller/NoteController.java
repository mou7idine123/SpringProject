package com.scolarite.scolarite.controller;

import com.scolarite.scolarite.entities.Note;
import com.scolarite.scolarite.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // POST /api/notes
    // Body: { "etudiantId": 1, "moduleId": 2, "note": 15.5, "annee": "2024-2025" }
    @PostMapping
    public ResponseEntity<?> ajouterNote(@RequestBody Map<String, Object> body) {
        try {
            Long etudiantId = Long.valueOf(body.get("etudiantId").toString());
            Long moduleId = Long.valueOf(body.get("moduleId").toString());
            double valeur = Double.parseDouble(body.get("note").toString());
            String annee = body.get("annee").toString();
            Note note = noteService.ajouterNote(etudiantId, moduleId, valeur, annee);
            return ResponseEntity.status(HttpStatus.CREATED).body(note);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/notes/{id}
    // Body: { "note": 17.0 }
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierNote(@PathVariable Long id,
                                           @RequestBody Map<String, Object> body) {
        try {
            double nouvelleValeur = Double.parseDouble(body.get("note").toString());
            return ResponseEntity.ok(noteService.modifierNote(id, nouvelleValeur));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/notes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerNote(@PathVariable Long id) {
        try {
            noteService.supprimerNote(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<?> findNotesParEtudiant(@PathVariable Long etudiantId) {
        try {
            return ResponseEntity.ok(noteService.findNotesParEtudiant(etudiantId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}/semestre/{semestre}
    @GetMapping("/etudiant/{etudiantId}/semestre/{semestre}")
    public ResponseEntity<?> findNotesParEtudiantEtSemestre(@PathVariable Long etudiantId,
                                                              @PathVariable int semestre) {
        try {
            return ResponseEntity.ok(noteService.findNotesParEtudiantEtSemestre(etudiantId, semestre));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}/moyenne
    @GetMapping("/etudiant/{etudiantId}/moyenne")
    public ResponseEntity<?> calculerMoyenneGenerale(@PathVariable Long etudiantId) {
        try {
            double moyenne = noteService.calculerMoyenneGenerale(etudiantId);
            return ResponseEntity.ok(Map.of("etudiantId", etudiantId, "moyenneGenerale", moyenne));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}/moyenne/semestre/{semestre}
    @GetMapping("/etudiant/{etudiantId}/moyenne/semestre/{semestre}")
    public ResponseEntity<?> calculerMoyenneParSemestre(@PathVariable Long etudiantId,
                                                          @PathVariable int semestre) {
        try {
            double moyenne = noteService.calculerMoyenneParSemestre(etudiantId, semestre);
            return ResponseEntity.ok(Map.of(
                    "etudiantId", etudiantId,
                    "semestre", semestre,
                    "moyenne", moyenne
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/notes/etudiant/{etudiantId}/resultats
    @GetMapping("/etudiant/{etudiantId}/resultats")
    public ResponseEntity<?> afficherResultats(@PathVariable Long etudiantId) {
        try {
            List<Note> notes = noteService.findNotesParEtudiant(etudiantId);
            double moyenneGenerale = noteService.calculerMoyenneGenerale(etudiantId);
            return ResponseEntity.ok(Map.of(
                    "etudiantId", etudiantId,
                    "notes", notes,
                    "moyenneGenerale", moyenneGenerale,
                    "mention", getMention(moyenneGenerale)
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getMention(double moyenne) {
        if (moyenne >= 16) return "Très Bien";
        if (moyenne >= 14) return "Bien";
        if (moyenne >= 12) return "Assez Bien";
        if (moyenne >= 10) return "Passable";
        return "Insuffisant";
    }
}
