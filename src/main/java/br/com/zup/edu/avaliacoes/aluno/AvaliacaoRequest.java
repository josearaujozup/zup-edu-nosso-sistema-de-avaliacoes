package br.com.zup.edu.avaliacoes.aluno;

import javax.validation.constraints.NotBlank;

public class AvaliacaoRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String avaliacaoReferente;

    public AvaliacaoRequest(String titulo, String avaliacaoReferente) {
        this.titulo = titulo;
        this.avaliacaoReferente = avaliacaoReferente;
    }

    public AvaliacaoRequest() {
    }

    public Avaliacao paraAvaliacao(Aluno aluno){
        return new Avaliacao(titulo,avaliacaoReferente,aluno);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAvaliacaoReferente() {
        return avaliacaoReferente;
    }
}
