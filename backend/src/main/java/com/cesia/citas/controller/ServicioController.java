package com.cesia.citas.controller;

import com.cesia.citas.entity.*;
import com.cesia.citas.dto.*;
import com.cesia.citas.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.*;
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    private final ServicioService service;
    public ServicioController(ServicioService service) { this.service=service; }
    @GetMapping public List<Servicio> listar() { return service.listar(); }
    @GetMapping("/{id}") public Servicio obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Servicio crear(@Valid @RequestBody Servicio dato) { return service.crear(dato); }
    @PutMapping("/{id}") public Servicio actualizar(@PathVariable Long id,@Valid @RequestBody Servicio dato) { return service.actualizar(id,dato); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
