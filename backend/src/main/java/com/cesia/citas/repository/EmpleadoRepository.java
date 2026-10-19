package com.cesia.citas.repository;

import com.cesia.citas.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.time.*;
import java.util.*;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Empleado e where e.id = :id")
    Optional<Empleado> bloquear(@Param("id") Long id);
}
