package br.com.zup.edu.avaliacoes.aluno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno,Long> {
    boolean existsByEmail(String email);
}
