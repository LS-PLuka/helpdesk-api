package com.helpdesk.model.entity;

import com.helpdesk.model.enums.Categoria;
import com.helpdesk.model.enums.StatusChamado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chamados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String solucao;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusChamado status = StatusChamado.ABERTO;

    @Column(nullable = false, length = 100)
    private String setor;

    @Column(length = 150)
    private String colaborador;

    @Column(length = 150)
    private String emailColaborador;
}
