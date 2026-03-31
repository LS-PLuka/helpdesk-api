package com.helpdesk.model.entity;

import com.helpdesk.model.enums.Categoria;
import com.helpdesk.model.enums.StatusChamado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

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

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    private String solucao;

    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.ABERTO;

    @Column(nullable = false)
    private String setor;

    private String colaborador;

    private String emailColaborador;
}
