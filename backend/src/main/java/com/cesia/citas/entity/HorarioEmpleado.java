package com.cesia.citas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HorarioEmpleado {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;
    @NotNull
    private LocalTime horaInicio;
    @NotNull
    private LocalTime horaFin;
    @ManyToOne(optional=false) @JoinColumn(name="empleado_id")
    private Empleado empleado;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DayOfWeek getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DayOfWeek diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}
