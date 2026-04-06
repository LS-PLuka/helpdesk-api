package com.helpdesk.service;

import com.helpdesk.exceptions.ResourceNotFoundException;
import com.helpdesk.model.dto.ChamadoRequest;
import com.helpdesk.model.dto.ChamadoResponse;
import com.helpdesk.model.dto.StatusRequest;
import com.helpdesk.model.entity.Chamado;
import com.helpdesk.model.enums.Categoria;
import com.helpdesk.model.enums.StatusChamado;
import com.helpdesk.repository.ChamadoRepository;
import com.helpdesk.service.validacao.ValidacaoChamado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock
    private ChamadoRepository repository;

    @Mock
    private List<ValidacaoChamado> validacoes;

    @InjectMocks
    private ChamadoService service;

    // ===== listAll =====

    @Test
    @DisplayName("Deve retornar lista de chamados")
    void listAll_deveRetornarListaDeChamados() {
        // Arrange
        Chamado chamado = chamadoValido();
        when(repository.findAll()).thenReturn(List.of(chamado));

        // Act
        List<ChamadoResponse> resultado = service.listAll();

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(chamado.getTitulo(), resultado.get(0).titulo());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao houver chamados")
    void listAll_deveRetornarListaVazia() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<ChamadoResponse> resultado = service.listAll();

        // Assert
        assertTrue(resultado.isEmpty());
    }

    // ===== getById =====

    @Test
    @DisplayName("Deve retornar chamado quando id existir")
    void getById_deveRetornarChamadoQuandoIdExistir() {
        // Arrange
        Chamado chamado = chamadoValido();
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));

        // Act
        ChamadoResponse resultado = service.getById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(chamado.getTitulo(), resultado.titulo());
        assertEquals(chamado.getStatus(), resultado.status());
    }

    @Test
    @DisplayName("Deve lancar excecao quando id nao existir")
    void getById_deveLancarExcecaoQuandoIdNaoExistir() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act e Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.getById(99L));
    }

    // ===== create =====

    @Test
    @DisplayName("Deve criar chamado com status ABERTO")
    void create_deveCriarChamadoComStatusAberto() {
        // Arrange
        ChamadoRequest dto = requestValido();
        Chamado chamadoSalvo = chamadoValido();
        when(repository.save(any(Chamado.class))).thenReturn(chamadoSalvo);

        // Act
        ChamadoResponse resultado = service.create(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusChamado.ABERTO, resultado.status());
        verify(repository, times(1)).save(any(Chamado.class));
    }

    @Test
    @DisplayName("Deve preencher todos os dados ao criar chamado")
    void create_devePreencherTodosOsDados() {
        // Arrange
        ChamadoRequest dto = requestValido();
        Chamado chamadoSalvo = chamadoValido();
        when(repository.save(any(Chamado.class))).thenReturn(chamadoSalvo);

        // Act
        ChamadoResponse resultado = service.create(dto);

        // Assert
        assertEquals(dto.titulo(), resultado.titulo());
        assertEquals(dto.descricao(), resultado.descricao());
        assertEquals(dto.categoria(), resultado.categoria());
        assertEquals(dto.setor(), resultado.setor());
    }

    // ===== edit STATUS =====

    @Test
    @DisplayName("Deve atualizar status do chamado")
    void atualizarStatus_deveAtualizarStatusDoChamado() {
        // Arrange
        Chamado chamado = chamadoValido();
        StatusRequest dto = new StatusRequest(StatusChamado.RESOLVIDO);
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));
        when(repository.save(any(Chamado.class))).thenReturn(chamado);

        // Act
        ChamadoResponse resultado = service.atualizarStatus(1L, dto);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Chamado.class));
    }

    @Test
    @DisplayName("Deve lancar excecao ao atualizar status de chamado inexistente")
    void atualizarStatus_deveLancarExcecaoQuandoIdNaoExistir() {
        // Arrange
        StatusRequest dto = new StatusRequest(StatusChamado.RESOLVIDO);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizarStatus(99L, dto));
    }

    // ===== delete =====

    @Test
    @DisplayName("Deve deletar chamado quando id existir")
    void delete_deveDeletarChamadoQuandoIdExistir() {
        // Arrange
        Chamado chamado = chamadoValido();
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));

        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lancar excecao ao deletar id inexistente")
    void delete_deveLancarExcecaoQuandoIdNaoExistir() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act e Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(99L));
        verify(repository, never()).deleteById(any());
    }

    // ===== DTOs =====

    private Chamado chamadoValido() {
        Chamado chamado = new Chamado();
        chamado.setId(1L);
        chamado.setTitulo("Sem acesso a VPN");
        chamado.setDescricao("Usuario nao consegue conectar na VPN");
        chamado.setCategoria(Categoria.REDE);
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setSetor("TI");
        return chamado;
    }

    private ChamadoRequest requestValido() {
        return new ChamadoRequest(
                "Sem acesso a VPN",
                "Usuario nao consegue conectar na VPN",
                "TI",
                null,
                null,
                Categoria.REDE,
                null,
                null,
                null
        );
    }
}
