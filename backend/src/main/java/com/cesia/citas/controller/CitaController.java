package com.cesia.citas.controller;

import com.cesia.citas.entity.*;
import com.cesia.citas.dto.*;
import com.cesia.citas.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.*;
@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final CitaService service;
    public CitaController(CitaService service) { this.service=service; }
    @GetMapping public List<Cita> listar() { return service.listar(); }
    @GetMapping("/{id}") public Cita obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Cita crear(@Valid @RequestBody CitaRequest dato) { return service.crear(dato); }
    @PutMapping("/{id}") public Cita actualizar(@PathVariable Long id,@Valid @RequestBody CitaRequest dato) { return service.actualizar(id,dato); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @GetMapping("/pendientes") public List<Cita> pendientes() { return service.pendientes(); }
    @GetMapping("/cliente/{id}") public List<Cita> porCliente(@PathVariable Long id) { return service.porCliente(id); }
    @GetMapping("/empleado/{id}") public List<Cita> porEmpleado(@PathVariable Long id) { return service.porEmpleado(id); }
    @PutMapping("/{id}/atender") public Cita atender(@PathVariable Long id) { return service.atender(id); }
}
