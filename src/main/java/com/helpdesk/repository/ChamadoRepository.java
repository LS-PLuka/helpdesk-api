package com.helpdesk.repository;

import com.helpdesk.model.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
