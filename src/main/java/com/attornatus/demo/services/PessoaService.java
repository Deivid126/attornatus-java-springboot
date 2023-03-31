package com.attornatus.demo.services;

import com.attornatus.demo.exceptions.PessoaNotFoundException;
import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.repositories.RepositoryEnderecos;
import com.attornatus.demo.repositories.RepositoryPessoa;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PessoaService {
    @Autowired
    RepositoryPessoa repositoryPessoa;
    @Autowired
    RepositoryEnderecos repositoryEndereco;

    public Pessoa findPessoaById(int id)
    {
        return repositoryPessoa.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com Id: " + id));
    }
    public Pessoa savePessoa(Pessoa pessoa)
    {

        return repositoryPessoa.save(pessoa);

    }
    public void deletepessoa(int id) {
        Pessoa pessoa = findPessoaById(id);
        repositoryPessoa.delete(pessoa);
    }

    public List<Pessoa> FindAllPessoa(){
        return repositoryPessoa.findAll();
    }

    public Pessoa atualizarPessoa(int id, Pessoa pessoaAtualizada) {
        Pessoa pessoaExistente = repositoryPessoa.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com ID: " + id));

        List<Endereco> enderecos = pessoaExistente.getEnderecos();
        List<Endereco> enderecosAtualizados = pessoaAtualizada.getEnderecos();

        for (int i = 0; i < enderecos.size(); i++) {
            Endereco enderecoExistente = enderecos.get(i);
            Endereco enderecoAtualizado = enderecosAtualizados.get(i);

            BeanUtils.copyProperties(enderecoAtualizado, enderecoExistente, "id", "pessoa");
            repositoryEndereco.save(enderecoExistente);
        }

        BeanUtils.copyProperties(pessoaAtualizada, pessoaExistente, "id", "enderecos");

        return repositoryPessoa.save(pessoaExistente);
    }





}
