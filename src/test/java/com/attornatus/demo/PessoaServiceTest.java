package com.attornatus.demo;

import com.attornatus.demo.enums.EnderecoEnum;
import com.attornatus.demo.exceptions.PessoaNotFoundException;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.repositories.RepositoryEnderecos;
import com.attornatus.demo.repositories.RepositoryPessoa;
import com.attornatus.demo.services.PessoaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {
    @Mock
    private RepositoryPessoa repositoryPessoa;

    @Mock
    private RepositoryEnderecos repositoryEndereco;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    public void shouldFindPessoaById() {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        when(repositoryPessoa.findById(1)).thenReturn(Optional.of(pessoa));

        // when
        Pessoa result = pessoaService.findPessoaById(1);

        // then
        assertEquals(1, result.getId());
        assertEquals("João", result.getNome());
    }

    @Test(expected = PessoaNotFoundException.class)
    public void shouldThrowExceptionWhenFindPessoaByIdNotFound() {

        when(repositoryPessoa.findById(1)).thenReturn(Optional.empty());


        pessoaService.findPessoaById(1);


    }

    @Test
    public void shouldSavePessoa() {
        // given
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        String date = "1999-05-31";
        LocalDate data = LocalDate.parse(date);
        pessoa.setDatanascimento(data);
        when(repositoryPessoa.save(pessoa)).thenReturn(pessoa);


        Pessoa result = pessoaService.savePessoa(pessoa);


        assertEquals(1, result.getId());
        assertEquals("João", result.getNome());
    }

    @Test
    public void shouldDeletePessoa() {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        when(repositoryPessoa.findById(1)).thenReturn(Optional.of(pessoa));


        pessoaService.deletepessoa(1);


        verify(repositoryPessoa, times(1)).delete(pessoa);
    }

    @Test(expected = PessoaNotFoundException.class)
    public void shouldThrowExceptionWhenDeletePessoaNotFound() {

        when(repositoryPessoa.findById(1)).thenReturn(Optional.empty());


        pessoaService.deletepessoa(1);

    }
    @Test(expected = PessoaNotFoundException.class)
    public void shouldThrowExceptionWhenAtualizarPessoaNotFound() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        String date = "1999-05-31";
        LocalDate data = LocalDate.parse(date);
        pessoa.setDatanascimento(data);
        when(repositoryPessoa.findById(1)).thenReturn(Optional.empty());


        pessoaService.atualizarPessoa(1,pessoa);

    }

    @Test
    public void shouldFindAllPessoa() {

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1);
        pessoa1.setNome("João");
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2);
        pessoa2.setNome("Maria");
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);
        when(repositoryPessoa.findAll()).thenReturn(pessoas);


        List<Pessoa> result = pessoaService.FindAllPessoa();


        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getNome());
        assertEquals("Maria", result.get(1).getNome());
    }
    @Test
    public void testAtualizarPessoa() {

        Endereco endereco1 = new Endereco(1,"Rua A", "123", 14, "São Paulo", EnderecoEnum.PADRÃO, null);
        Endereco endereco2 = new Endereco(2,"Rua B", "456", 0, "São Paulo", EnderecoEnum.SECUNDÁRIO, null);
        List<Endereco> enderecosAtualizados = Arrays.asList(endereco1, endereco2);
        Pessoa pessoanew= new Pessoa(1,"João da Silva", LocalDate.of(1990, 5, 20), enderecosAtualizados);


        Endereco endereco3 = new Endereco(3,"Rua C", "789", 1, "Rio de Janeiro", EnderecoEnum.PADRÃO, null);
        Endereco endereco4 = new Endereco(4,"Rua D", "101", 4, "Rio de Janeiro", EnderecoEnum.SECUNDÁRIO, null);
        List<Endereco> enderecosExistente = Arrays.asList(endereco3, endereco4);
        Pessoa pessoaExistente = new Pessoa(1,"Maria Oliveira", LocalDate.of(1985, 7, 15), enderecosExistente);
        pessoaExistente.setId(1);


        when(repositoryPessoa.findById(1)).thenReturn(Optional.of(pessoaExistente));
        when(repositoryEndereco.save(any(Endereco.class))).thenReturn(endereco1, endereco2);
        when(repositoryPessoa.save(any(Pessoa.class))).thenReturn(pessoanew);



        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(1, pessoanew);


        assertEquals("João da Silva", pessoaAtualizada.getNome());
        assertEquals(LocalDate.of(1990, 5, 20), pessoaAtualizada.getDatanascimento());
        assertEquals(2, pessoaAtualizada.getEnderecos().size());
        assertEquals("Rua A", pessoaAtualizada.getEnderecos().get(0).getLogradouro());
        assertEquals(14, pessoaAtualizada.getEnderecos().get(0).getNumero());
        assertEquals("123", pessoaAtualizada.getEnderecos().get(0).getCaixapostal());
        assertEquals("São Paulo", pessoaAtualizada.getEnderecos().get(0).getCidade());
        assertEquals(EnderecoEnum.PADRÃO, pessoaAtualizada.getEnderecos().get(0).getTipoendereco());
        assertEquals("Rua B", pessoaAtualizada.getEnderecos().get(1).getLogradouro());
        assertEquals(0, pessoaAtualizada.getEnderecos().get(1).getNumero());
        assertEquals("456", pessoaAtualizada.getEnderecos().get(1).getCaixapostal());
        assertEquals("São Paulo", pessoaAtualizada.getEnderecos().get(1).getCidade());
        assertEquals(EnderecoEnum.SECUNDÁRIO, pessoaAtualizada.getEnderecos().get(1).getTipoendereco());
        verify(repositoryPessoa, times(1)).findById(1);
        verify(repositoryEndereco, times(2)).save(any(Endereco.class));
        verify(repositoryPessoa, times(1)).save(any(Pessoa.class));
    }
}
