package com.attornatus.demo;

import com.attornatus.demo.enums.EnderecoEnum;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.repositories.RepositoryEnderecos;
import com.attornatus.demo.repositories.RepositoryPessoa;
import com.attornatus.demo.services.EnderecoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {

    @Mock
    private RepositoryPessoa repositoryPessoa;

    @Mock
    private RepositoryEnderecos repositoryEndereco;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    public void testSaveEndereco() {
        int id = 1;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        Endereco endereco1 = new Endereco();
        endereco1.setId(1);
        endereco1.setLogradouro("Rua A");
        endereco1.setNumero(10);
        endereco1.setCidade("São Paulo");
        endereco1.setTipoEndereco(EnderecoEnum.PADRÃO);
        Endereco endereco2 = new Endereco();
        endereco2.setId(2);
        endereco2.setLogradouro("Rua B");
        endereco2.setNumero(20);
        endereco2.setCidade("São Paulo");
        endereco1.setTipoEndereco(EnderecoEnum.SECUNDÁRIO);
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        when(repositoryPessoa.findById(id)).thenReturn(Optional.of(pessoa));
        when(repositoryEndereco.save(endereco1)).thenReturn(endereco1);
        when(repositoryEndereco.save(endereco2)).thenReturn(endereco2);

        List<Endereco> result = enderecoService.saveEndereco(id, enderecos);

        verify(repositoryPessoa, times(1)).findById(id);
        verify(repositoryEndereco, times(1)).save(endereco1);
        verify(repositoryEndereco, times(1)).save(endereco2);

        assertEquals(2, result.size());
        assertEquals(pessoa, endereco1.getPessoa());
        assertEquals(pessoa, endereco2.getPessoa());
    }
}
