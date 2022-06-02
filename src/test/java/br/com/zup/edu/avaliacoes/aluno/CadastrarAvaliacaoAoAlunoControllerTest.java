package br.com.zup.edu.avaliacoes.aluno;

import br.com.zup.edu.avaliacoes.compartilhado.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarAvaliacaoAoAlunoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private Aluno aluno;

    @BeforeEach
    void setUp(){
        this.avaliacaoRepository.deleteAll();
        this.alunoRepository.deleteAll();

        this.aluno = new Aluno("Denes","Denes@email.com","aceleração");
        alunoRepository.save(this.aluno);
    }

    @Test
    @DisplayName("Deve cadastrar uma avaliação para um aluno")
    void test1() throws Exception {
        //cenarios

        AvaliacaoRequest avaliacaoRequest =new AvaliacaoRequest("testes","integração");
        String payload = mapper.writeValueAsString(avaliacaoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/alunos/{id}/avaliacoes",aluno.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        //acao e corretude
        mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.redirectedUrlPattern("http://localhost/alunos/*/avaliacoes/*")
                );

        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        assertEquals(1,avaliacoes.size());
    }

    @Test
    @DisplayName("Não deve cadastrar uma avaliação caso um aluno não exista")
    void test2() throws Exception{
        AvaliacaoRequest avaliacaoRequest =new AvaliacaoRequest("testes","integração");
        String payload = mapper.writeValueAsString(avaliacaoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/alunos/{id}/avaliacoes",100000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        //acao e corretude
        mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    @DisplayName("Não deve cadastrar uma avaliação caso os dados sejam inválidos")
    void test3() throws Exception{
        AvaliacaoRequest avaliacaoRequest =new AvaliacaoRequest(" "," ");
        String payload = mapper.writeValueAsString(avaliacaoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/alunos/{id}/avaliacoes",aluno.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);
        assertEquals(2,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo avaliacaoReferente não deve estar em branco",
                "O campo titulo não deve estar em branco"
        ));

    }
}