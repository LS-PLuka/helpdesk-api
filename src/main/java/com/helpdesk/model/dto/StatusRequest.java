package com.helpdesk.model.dto;

import com.helpdesk.model.enums.StatusChamado;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(

        @NotNull(message = "Status é obrigatório")
        StatusChamado status
) {}
