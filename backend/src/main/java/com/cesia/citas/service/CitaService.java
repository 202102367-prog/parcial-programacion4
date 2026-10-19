package com.cesia.citas.service;

import com.cesia.citas.entity.*;
import com.cesia.citas.repository.*;
import com.cesia.citas.dto.CitaRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.*;
@Service
@Transactional
public class CitaService {
    private final CitaRepository repository;
    private final EmpleadoRepository empleados;
    private final ClienteService clientes;
    private final ServicioService servicios;
    public CitaService(CitaRepository r, EmpleadoRepository e, ClienteService c, ServicioService s) {
        repository=r; empleados=e; clientes=c; servicios=s;
    }
    @Transactional(readOnly=true)
    public List<Cita> listar() { return repository.findAll(); }
    @Transactional(readOnly=true)
    public Cita obtener(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cita no encontrada")); }
    @Transactional(readOnly=true)
    public List<Cita> pendientes() { return repository.findByEstado(EstadoCita.PENDIENTE); }
    @Transactional(readOnly=true)
    public List<Cita> porCliente(Long id) { return repository.findByClienteId(id); }
    @Transactional(readOnly=true)
    public List<Cita> porEmpleado(Long id) { return repository.findByEmpleadoId(id); }
    public Cita crear(CitaRequest dato) { return guardar(new Cita(),dato); }
    public Cita actualizar(Long id, CitaRequest dato) { return guardar(obtener(id),dato); }
    private Cita guardar(Cita cita, CitaRequest dato) {
        // Bloquear al empleado serializa las reservas concurrentes para el mismo empleado.
        Empleado empleado=empleados.bloquear(dato.empleadoId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Empleado no encontrado"));
        EstadoCita estado=dato.estado()==null ? EstadoCita.PENDIENTE : dato.estado();
        validarDisponibilidad(cita.getId(),empleado.getId(),dato.fecha(),dato.hora(),estado);
        cita.setCliente(clientes.obtener(dato.clienteId())); cita.setEmpleado(empleado);
        cita.setFecha(dato.fecha()); cita.setHora(dato.hora()); cita.setEstado(estado);
        cita.getDetalles().clear();
        BigDecimal total=BigDecimal.ZERO;
        for (CitaRequest.DetalleRequest item : dato.detalles()) {
            Servicio servicio=servicios.obtener(item.servicioId());
            DetalleCita detalle=new DetalleCita();
            detalle.setCita(cita); detalle.setServicio(servicio); detalle.setCantidad(item.cantidad());
            detalle.setSubtotal(servicio.getPrecio().multiply(BigDecimal.valueOf(item.cantidad())));
            cita.getDetalles().add(detalle); total=total.add(detalle.getSubtotal());
        }
        cita.setTotal(total); return repository.saveAndFlush(cita);
    }
    private void validarDisponibilidad(Long id,Long empleadoId,java.time.LocalDate fecha,java.time.LocalTime hora,EstadoCita estado) {
        // Una cita activa es cualquier cita que no este CANCELADA.
        if (estado!=EstadoCita.CANCELADA && repository.existsByEmpleadoIdAndFechaAndHoraAndEstadoNotAndIdNot(
            empleadoId,fecha,hora,EstadoCita.CANCELADA,id==null ? -1L : id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"El empleado ya posee otra cita activa en la misma fecha y hora");
    }
    public Cita atender(Long id) {
        Cita cita=obtener(id);
        empleados.bloquear(cita.getEmpleado().getId()).orElseThrow();
        validarDisponibilidad(id,cita.getEmpleado().getId(),cita.getFecha(),cita.getHora(),EstadoCita.ATENDIDA);
        cita.setEstado(EstadoCita.ATENDIDA); return repository.saveAndFlush(cita);
    }
    public void eliminar(Long id) { repository.delete(obtener(id)); repository.flush(); }
}
