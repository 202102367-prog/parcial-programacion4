package com.cesia.citas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import com.cesia.citas.entity.EstadoCita;
public record CitaRequest(@NotNull LocalDate fecha, @NotNull LocalTime hora,
    EstadoCita estado, @NotNull @Positive Long clienteId, @NotNull @Positive Long empleadoId,
    @NotEmpty List<@Valid DetalleRequest> detalles) {
    public record DetalleRequest(@NotNull @Positive Long servicioId, @NotNull @Positive Integer cantidad) {}
}
