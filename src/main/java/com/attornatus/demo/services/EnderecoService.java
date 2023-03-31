package com.attornatus.demo.services;

import com.attornatus.demo.enums.EnderecoEnum;
import com.attornatus.demo.exceptions.PessoaNotFoundException;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.repositories.RepositoryEnderecos;
import com.attornatus.demo.repositories.RepositoryPessoa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoService {
    @Autowired
    RepositoryPessoa repositoryPessoa;
    @Autowired
    RepositoryEnderecos repositoryEndereco;

    public List<Endereco> saveEndereco(int id, List<Endereco> endereco) {

        Pessoa pessoa = repositoryPessoa.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com Id: " + id));

        endereco.forEach(endereconew -> {

            endereconew.setPessoa(pessoa);
            repositoryEndereco.save(endereconew);
        });

        return endereco;
    }

    public List<Endereco> listarAllEndereco(int id){
        Pessoa pessoa = repositoryPessoa.findById(id).orElseThrow(()-> new PessoaNotFoundException("Pessoa não encontrada com Id:" + id));

        List<Endereco> enderecos = pessoa.getEnderecos();

        return enderecos;
    }

}
