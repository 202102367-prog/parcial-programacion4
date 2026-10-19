package com.cesia.citas.controller;

import com.cesia.citas.entity.*;
import com.cesia.citas.dto.*;
import com.cesia.citas.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.*;
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService service;
    public EmpleadoController(EmpleadoService service) { this.service=service; }
    @GetMapping public List<Empleado> listar() { return service.listar(); }
    @GetMapping("/{id}") public Empleado obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Empleado crear(@Valid @RequestBody Empleado dato) { return service.crear(dato); }
    @PutMapping("/{id}") public Empleado actualizar(@PathVariable Long id,@Valid @RequestBody Empleado dato) { return service.actualizar(id,dato); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
