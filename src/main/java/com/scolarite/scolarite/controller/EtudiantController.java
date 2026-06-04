package com.scolarite.scolarite.controller;

import com.scolarite.scolarite.entities.Etudiant;
import com.scolarite.scolarite.service.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    // GET /api/etudiants
    @GetMapping
    public ResponseEntity<List<Etudiant>> findAll() {
        return ResponseEntity.ok(etudiantService.findAll());
    }

    // GET /api/etudiants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(etudiantService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/etudiants/matricule/{matricule}
    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<Etudiant> findByMatricule(@PathVariable String matricule) {
        try {
            return ResponseEntity.ok(etudiantService.findByMatricule(matricule));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/etudiants
    @PostMapping
    public ResponseEntity<Etudiant> save(@RequestBody Etudiant etudiant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.save(etudiant));
    }

    // PUT /api/etudiants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> update(@PathVariable Long id, @RequestBody Etudiant etudiant) {
        try {
            return ResponseEntity.ok(etudiantService.update(id, etudiant));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/etudiants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            etudiantService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
