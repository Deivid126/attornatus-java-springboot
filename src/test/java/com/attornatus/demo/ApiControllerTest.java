package com.attornatus.demo;
import com.attornatus.demo.controllers.ApiController;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.services.EnderecoService;
import com.attornatus.demo.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PessoaService pessoaService;
    @MockBean
    private EnderecoService enderecoService;

    @Test
    public void testSavePessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");

        Mockito.when(pessoaService.savePessoa(pessoa)).thenReturn(pessoa);

        mockMvc.perform(post("/api/attornatus/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testGetAllPessoa() throws Exception {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("John Doe");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Jane Smith");

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);

        Mockito.when(pessoaService.FindAllPessoa()).thenReturn(pessoas);

        mockMvc.perform(get("/api/attornatus/pessoa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].nome").value("John Doe"))
                .andExpect((ResultMatcher) jsonPath("$[1].nome").value("Jane Smith"));
    }

    @Test
    public void testPutPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");

        Mockito.when(pessoaService.atualizarPessoa(1, pessoa)).thenReturn(pessoa);

        mockMvc.perform(put("/api/attornatus/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testDeletePessoa() throws Exception {
        mockMvc.perform(delete("/api/attornatus/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOnePessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("John Doe");

        Mockito.when(pessoaService.findPessoaById(1)).thenReturn(pessoa);


        mockMvc.perform(get("/api/attornatus/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }
    @Test
    public void testpostendereco() throws Exception
    {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("John Doe");

        Endereco endereco = new Endereco();
        endereco.setCidade("Igarassu");

        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);


       Mockito.when(enderecoService.saveEndereco(1,enderecos)).thenReturn(enderecos);

       mockMvc.perform(post("/api/attornatus/endereco/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(enderecos)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$[0].cidade").value("Igarassu"));
    }
    @Test
    public void testgetallenderecos() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("John Doe");

        Endereco endereco = new Endereco();
        endereco.setCidade("Igarassu");

        List<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(endereco);

        Mockito.when(enderecoService.listarAllEndereco(1)).thenReturn(enderecos);

        mockMvc.perform(get("/api/attornatus/endereco/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cidade").value("Igarassu"));

    }

}
