package com.helpdesk.service.validacao;

import com.helpdesk.exceptions.BusinessException;
import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.entity.Chamado;
import com.helpdesk.model.enums.StatusChamado;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoReaberturaStatus implements ValidacaoChamado {

    @Override
    public void validar(Chamado chamado, ChamadoRequest dto) {
        if (chamado.getStatus() == StatusChamado.RESOLVIDO
                && dto.status() == StatusChamado.ABERTO) {
            throw new BusinessException(
                    "Não é possível reabrir um chamado que já está resolvido.");
        }
    }
}
