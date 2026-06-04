package com.scolarite.scolarite.controller;

import com.scolarite.scolarite.entities.Inscription;
import com.scolarite.scolarite.service.InscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    // POST /api/inscriptions/etudiant/{etudiantId}/module/{moduleId}
    @PostMapping("/etudiant/{etudiantId}/module/{moduleId}")
    public ResponseEntity<?> inscrire(@PathVariable Long etudiantId,
                                       @PathVariable Long moduleId) {
        try {
            Inscription inscription = inscriptionService.inscrire(etudiantId, moduleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscription);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/inscriptions/etudiant/{etudiantId}
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<?> findModulesParEtudiant(@PathVariable Long etudiantId) {
        try {
            return ResponseEntity.ok(inscriptionService.findModulesParEtudiant(etudiantId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/inscriptions/module/{moduleId}
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<?> findEtudiantsParModule(@PathVariable Long moduleId) {
        try {
            return ResponseEntity.ok(inscriptionService.findEtudiantsParModule(moduleId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/inscriptions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            inscriptionService.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
