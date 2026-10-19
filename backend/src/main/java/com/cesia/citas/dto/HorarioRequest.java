package com.cesia.citas.dto;

import jakarta.validation.constraints.*;
import java.time.*;
public record HorarioRequest(@NotNull DayOfWeek diaSemana, @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFin, @NotNull @Positive Long empleadoId) {}
