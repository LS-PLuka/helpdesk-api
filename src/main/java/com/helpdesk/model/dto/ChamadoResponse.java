package com.helpdesk.model.dto;

import com.helpdesk.model.enums.Categoria;
import com.helpdesk.model.enums.StatusChamado;

public record ChamadoResponse(
        Long id,
        String titulo,
        String descricao,
        String solucao,
        String observacao,
        Categoria categoria,
        StatusChamado status,
        String setor,
        String colaborador,
        String emailColaborador
) {}
