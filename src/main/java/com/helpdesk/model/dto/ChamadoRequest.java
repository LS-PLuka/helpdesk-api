package com.helpdesk.model.dto;

import com.helpdesk.model.enums.Categoria;

public record ChamadoRequest(
        String titulo,
        String descricao,
        String setor,
        String colaborador,
        String emailColaborador,
        Categoria categoria
) {}
