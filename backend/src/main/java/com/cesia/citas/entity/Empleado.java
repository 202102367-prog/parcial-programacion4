package com.cesia.citas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Empleado {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String especialidad;
    @NotNull
    private Boolean activo;
    @OneToMany(mappedBy="empleado") @JsonIgnore
    private List<Cita> citas = new ArrayList<>();
    @OneToMany(mappedBy="empleado") @JsonIgnore
    private List<HorarioEmpleado> horarios = new ArrayList<>();
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
    public List<HorarioEmpleado> getHorarios() { return horarios; }
    public void setHorarios(List<HorarioEmpleado> horarios) { this.horarios = horarios; }
}
