package com.helpdesk.service.validacao;

import com.helpdesk.exceptions.BusinessException;
import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.entity.Chamado;
import com.helpdesk.model.enums.StatusChamado;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoStatusResolvido implements  ValidacaoChamado {

    @Override
    public void validar(Chamado chamado, ChamadoRequest dto) {
        if (chamado.getStatus() == StatusChamado.RESOLVIDO) {
            throw new BusinessException(
                    "Não é possível alterar um chamado que já está resolvido."
            );
        }
    }
}
