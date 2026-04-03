package com.helpdesk.model.dto;

import com.helpdesk.model.entity.Chamado;
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
) {
    public static ChamadoResponse fromEntity(Chamado chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getSolucao(),
                chamado.getObservacao(),
                chamado.getCategoria(),
                chamado.getStatus(),
                chamado.getSetor(),
                chamado.getColaborador(),
                chamado.getEmailColaborador()
        );
    }
}