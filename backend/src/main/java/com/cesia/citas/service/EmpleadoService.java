package com.cesia.citas.service;

import com.cesia.citas.entity.*;
import com.cesia.citas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;
@Service
@Transactional
public class EmpleadoService {
    private final EmpleadoRepository repository;
    public EmpleadoService(EmpleadoRepository repository) { this.repository = repository; }
    @Transactional(readOnly=true)
    public List<Empleado> listar() { return repository.findAll(); }
    @Transactional(readOnly=true)
    public Empleado obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Empleado no encontrado")); }
    public Empleado crear(Empleado dato) { dato.setId(null); return repository.save(dato); }
    public Empleado actualizar(Long id, Empleado dato) {
        Empleado actual = obtener(id);
        actual.setNombre(dato.getNombre());
        actual.setEspecialidad(dato.getEspecialidad());
        actual.setActivo(dato.getActivo());
        return repository.save(actual);
    }
    public void eliminar(Long id) { repository.delete(obtener(id)); repository.flush(); }
}
