package com.helpdesk.model.dto;

import com.helpdesk.model.enums.Categoria;
import com.helpdesk.model.enums.StatusChamado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoRequest(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotBlank(message = "Setor é obrigatório")
        String setor,

        String colaborador,

        @Email(message = "Email inválido")
        String emailColaborador,

        @NotNull(message = "Categoria é obrigatória")
        Categoria categoria,

        StatusChamado status,

        String solucao,

        String observacao
) {}