package com.helpdesk.service;

import com.helpdesk.exceptions.ResourceNotFoundException;
import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.dto.ChamadoResponse;
import com.helpdesk.model.dto.StatusRequest;
import com.helpdesk.model.entity.Chamado;
import com.helpdesk.model.enums.StatusChamado;
import com.helpdesk.repository.ChamadoRepository;
import com.helpdesk.service.validacao.ValidacaoChamado;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository repository;
    private final List<ValidacaoChamado> validacoes;

    public ChamadoService(ChamadoRepository repository, List<ValidacaoChamado> validacoes) {
        this.repository = repository;
        this.validacoes = validacoes;
    }

    public List<ChamadoResponse> listAll() {
        return repository.findAll()
                .stream()
                .map(ChamadoResponse::fromEntity)
                .toList();
    }

    public ChamadoResponse getById(Long id) {
        Chamado chamado = buscarOuLancarExcecao(id);
        return ChamadoResponse.fromEntity(chamado);
    }

    public ChamadoResponse create(ChamadoRequest dto) {
        Chamado chamado = new Chamado();
        preencherDados(chamado, dto);
        chamado.setStatus(StatusChamado.ABERTO);
        return ChamadoResponse.fromEntity(repository.save(chamado));
    }

    public ChamadoResponse atualizarStatus(Long id, StatusRequest dto) {
        Chamado chamado = buscarOuLancarExcecao(id);
        chamado.setStatus(dto.status());
        return ChamadoResponse.fromEntity(repository.save(chamado));
    }

    public void delete(Long id) {
        buscarOuLancarExcecao(id);
        repository.deleteById(id);
    }

    // metodos privados -> auxiliares

    private Chamado buscarOuLancarExcecao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chamado não encontrado com id: " + id));
    }

    private void preencherDados(Chamado chamado, ChamadoRequest dto) {
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setSolucao(dto.solucao());
        chamado.setObservacao(dto.observacao());
        chamado.setCategoria(dto.categoria());
        chamado.setSetor(dto.setor());
        chamado.setColaborador(dto.colaborador());
        chamado.setEmailColaborador(dto.emailColaborador());
    }
}
