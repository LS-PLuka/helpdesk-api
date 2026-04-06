package com.helpdesk.controller;

import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.dto.ChamadoResponse;
import com.helpdesk.model.dto.StatusRequest;
import com.helpdesk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChamadoResponse>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ChamadoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ChamadoResponse> create(
            @RequestBody @Valid ChamadoRequest dto,
            UriComponentsBuilder uriBuilder) {

        ChamadoResponse response = service.create(dto);
        URI uri = uriBuilder.path("/api/chamados/create/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ChamadoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid StatusRequest dto) {

        ChamadoResponse response = service.atualizarStatus(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
