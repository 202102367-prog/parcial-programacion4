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
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    @Transactional(readOnly=true)
    public List<Cliente> listar() { return repository.findAll(); }
    @Transactional(readOnly=true)
    public Cliente obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente no encontrado")); }
    public Cliente crear(Cliente dato) { dato.setId(null); return repository.save(dato); }
    public Cliente actualizar(Long id, Cliente dato) {
        Cliente actual = obtener(id);
        actual.setNombre(dato.getNombre());
        actual.setCorreo(dato.getCorreo());
        actual.setTelefono(dato.getTelefono());
        return repository.save(actual);
    }
    public void eliminar(Long id) { repository.delete(obtener(id)); repository.flush(); }
}
