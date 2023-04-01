package com.attornatus.demo.services;

import com.attornatus.demo.enums.EnderecoEnum;
import com.attornatus.demo.exceptions.PessoaNotFoundException;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.repositories.RepositoryEnderecos;
import com.attornatus.demo.repositories.RepositoryPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    RepositoryPessoa repositoryPessoa;
    @Autowired
    RepositoryEnderecos repositoryEndereco;

    public List<Endereco> saveEndereco(int id, List<Endereco> endereco) {

        Optional<Pessoa> pessoa = repositoryPessoa.findById(id);


        endereco.forEach(endereconew -> {

            endereconew.setPessoa(pessoa.get());
            repositoryEndereco.save(endereconew);
        });

        return endereco;
    }

    public List<Endereco> listarAllEndereco(int id){
        Optional<Pessoa> pessoa = repositoryPessoa.findById(id);

        List<Endereco> enderecos = pessoa.get().getEnderecos();

        return enderecos;
    }

}
