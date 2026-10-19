package com.cesia.citas.service;

import com.cesia.citas.entity.*;
import com.cesia.citas.repository.*;
import com.cesia.citas.dto.HorarioRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;
@Service
@Transactional
public class HorarioEmpleadoService {
    private final HorarioEmpleadoRepository repository;
    private final EmpleadoService empleados;
    public HorarioEmpleadoService(HorarioEmpleadoRepository r, EmpleadoService e) { repository=r; empleados=e; }
    @Transactional(readOnly=true)
    public List<HorarioEmpleado> listar() { return repository.findAll(); }
    @Transactional(readOnly=true)
    public HorarioEmpleado obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Horario no encontrado")); }
    public HorarioEmpleado crear(HorarioRequest dato) { return guardar(new HorarioEmpleado(),dato); }
    public HorarioEmpleado actualizar(Long id, HorarioRequest dato) { return guardar(obtener(id),dato); }
    private HorarioEmpleado guardar(HorarioEmpleado h, HorarioRequest dato) {
        if (!dato.horaInicio().isBefore(dato.horaFin())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La hora de inicio debe ser anterior a la hora de fin");
        h.setDiaSemana(dato.diaSemana()); h.setHoraInicio(dato.horaInicio()); h.setHoraFin(dato.horaFin());
        h.setEmpleado(empleados.obtener(dato.empleadoId())); return repository.save(h);
    }
    public void eliminar(Long id) { repository.delete(obtener(id)); repository.flush(); }
}
