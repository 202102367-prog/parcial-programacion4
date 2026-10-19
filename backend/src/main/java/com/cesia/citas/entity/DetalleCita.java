package com.cesia.citas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DetalleCita {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull @Positive
    private Integer cantidad;
    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal subtotal;
    @ManyToOne(optional=false) @JoinColumn(name="cita_id") @JsonIgnore
    private Cita cita;
    @ManyToOne(optional=false) @JoinColumn(name="servicio_id")
    private Servicio servicio;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
}
