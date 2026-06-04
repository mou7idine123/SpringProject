package com.scolarite.scolarite.controller;

import com.scolarite.scolarite.entities.Enseignant;
import com.scolarite.scolarite.service.EnseignantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    private final EnseignantService enseignantService;

    public EnseignantController(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    // GET /api/enseignants
    @GetMapping
    public ResponseEntity<List<Enseignant>> findAll() {
        return ResponseEntity.ok(enseignantService.findAll());
    }

    // GET /api/enseignants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/enseignants
    @PostMapping
    public ResponseEntity<Enseignant> save(@RequestBody Enseignant enseignant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.save(enseignant));
    }

    // PUT /api/enseignants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> update(@PathVariable Long id, @RequestBody Enseignant enseignant) {
        try {
            return ResponseEntity.ok(enseignantService.update(id, enseignant));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/enseignants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            enseignantService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
