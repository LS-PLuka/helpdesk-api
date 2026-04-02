package com.helpdesk.service.validacao;

import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.entity.Chamado;

public interface ValidacaoChamado {

    void validar(Chamado chamado, ChamadoRequest dto);
}
