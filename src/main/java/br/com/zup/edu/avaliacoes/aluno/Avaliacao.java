package br.com.zup.edu.avaliacoes.aluno;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String avaliacaoReferente;

    @ManyToOne
    private Aluno aluno;

    @Column(nullable = false)
    private LocalDateTime criadoEm= LocalDateTime.now();

    public Avaliacao(String titulo, String avaliacaoReferente, Aluno aluno) {
        this.titulo = titulo;
        this.avaliacaoReferente = avaliacaoReferente;
        this.aluno = aluno;
    }

    /**
     * @deprecated  construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public Avaliacao() {
    }

    public Long getId() {
        return id;
    }
}
