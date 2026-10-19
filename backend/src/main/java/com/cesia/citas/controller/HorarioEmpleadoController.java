package com.cesia.citas.controller;

import com.cesia.citas.entity.*;
import com.cesia.citas.dto.*;
import com.cesia.citas.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.*;
@RestController
@RequestMapping("/api/horarios")
public class HorarioEmpleadoController {
    private final HorarioEmpleadoService service;
    public HorarioEmpleadoController(HorarioEmpleadoService service) { this.service=service; }
    @GetMapping public List<HorarioEmpleado> listar() { return service.listar(); }
    @GetMapping("/{id}") public HorarioEmpleado obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public HorarioEmpleado crear(@Valid @RequestBody HorarioRequest dato) { return service.crear(dato); }
    @PutMapping("/{id}") public HorarioEmpleado actualizar(@PathVariable Long id,@Valid @RequestBody HorarioRequest dato) { return service.actualizar(id,dato); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
