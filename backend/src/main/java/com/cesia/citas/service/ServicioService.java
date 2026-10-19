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
public class ServicioService {
    private final ServicioRepository repository;
    public ServicioService(ServicioRepository repository) { this.repository = repository; }
    @Transactional(readOnly=true)
    public List<Servicio> listar() { return repository.findAll(); }
    @Transactional(readOnly=true)
    public Servicio obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Servicio no encontrado")); }
    public Servicio crear(Servicio dato) { dato.setId(null); return repository.save(dato); }
    public Servicio actualizar(Long id, Servicio dato) {
        Servicio actual = obtener(id);
        actual.setNombre(dato.getNombre());
        actual.setDuracionMinutos(dato.getDuracionMinutos());
        actual.setPrecio(dato.getPrecio());
        return repository.save(actual);
    }
    public void eliminar(Long id) { repository.delete(obtener(id)); repository.flush(); }
}
