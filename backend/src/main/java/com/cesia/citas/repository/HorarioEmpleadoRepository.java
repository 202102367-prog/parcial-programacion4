package com.cesia.citas.repository;

import com.cesia.citas.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.time.*;
import java.util.*;
@Repository
public interface HorarioEmpleadoRepository extends JpaRepository<HorarioEmpleado,Long> {
    
}
