package br.com.zup.edu.avaliacoes.aluno;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AlunoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String bootcamp;

    public AlunoRequest(String nome, String email, String bootcamp) {
        this.nome = nome;
        this.email = email;
        this.bootcamp = bootcamp;
    }

    public AlunoRequest() {
    }

    public Aluno paraAluno(){
        return new Aluno(nome,email,bootcamp);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getBootcamp() {
        return bootcamp;
    }
}
