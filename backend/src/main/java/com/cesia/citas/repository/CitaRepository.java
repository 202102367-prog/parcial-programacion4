package com.cesia.citas.repository;

import com.cesia.citas.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.time.*;
import java.util.*;
@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {
    List<Cita> findByEstado(EstadoCita estado);
    List<Cita> findByClienteId(Long id);
    List<Cita> findByEmpleadoId(Long id);
    boolean existsByEmpleadoIdAndFechaAndHoraAndEstadoNotAndIdNot(Long empleadoId, LocalDate fecha, LocalTime hora, EstadoCita estado, Long id);
}
