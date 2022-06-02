package br.com.zup.edu.avaliacoes.aluno;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

@RestController
public class CadastrarAlunoController {
    private final AlunoRepository repository;

    public CadastrarAlunoController(AlunoRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/alunos")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AlunoRequest request, UriComponentsBuilder uriComponentsBuilder){
        boolean existe = repository.existsByEmail(request.getEmail());

        if(existe){
            return unprocessableEntity().body(Map.of("mensagem","Já existe cadastro de Aluno para este email."));
        }

        Aluno aluno = request.paraAluno();

        repository.save(aluno);

        URI location = uriComponentsBuilder.path("/alunos/{id}")
                .buildAndExpand(aluno.getId())
                .toUri();

        return created(location).build();
    }
}
