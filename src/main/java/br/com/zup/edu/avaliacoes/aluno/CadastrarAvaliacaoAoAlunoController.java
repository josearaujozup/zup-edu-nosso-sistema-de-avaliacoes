package br.com.zup.edu.avaliacoes.aluno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

@RestController
public class CadastrarAvaliacaoAoAlunoController {
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public CadastrarAvaliacaoAoAlunoController(AlunoRepository alunoRepository, AvaliacaoRepository avaliacaoRepository) {
        this.alunoRepository = alunoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }


    @PostMapping("/alunos/{id}/avaliacoes")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AvaliacaoRequest request, @PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){


        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno não cadastrado no sistema"));

        Avaliacao avaliacao = request.paraAvaliacao(aluno);

        avaliacaoRepository.save(avaliacao);

        URI location = uriComponentsBuilder.path("/alunos/{id}/avaliacoes/{id}")
                .buildAndExpand(aluno.getId(),avaliacao.getId())
                .toUri();

        return created(location).build();
    }
}
