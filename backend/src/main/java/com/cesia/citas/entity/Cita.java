package com.cesia.citas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cita {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate fecha;
    @NotNull
    private LocalTime hora;
    @NotNull @Enumerated(EnumType.STRING)
    private EstadoCita estado;
    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal total;
    @ManyToOne(optional=false) @JoinColumn(name="cliente_id")
    private Cliente cliente;
    @ManyToOne(optional=false) @JoinColumn(name="empleado_id")
    private Empleado empleado;
    @OneToMany(mappedBy="cita",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<DetalleCita> detalles = new ArrayList<>();
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public List<DetalleCita> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCita> detalles) { this.detalles = detalles; }
}
