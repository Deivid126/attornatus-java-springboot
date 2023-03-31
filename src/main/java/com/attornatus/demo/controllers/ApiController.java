package com.attornatus.demo.controllers;

import com.attornatus.demo.models.Endereco;
import com.attornatus.demo.models.Pessoa;
import com.attornatus.demo.services.EnderecoService;
import com.attornatus.demo.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/attornatus")
public class ApiController {

    @Autowired
    PessoaService pessoaService;
    @Autowired
    EnderecoService enderecoService;

    @PostMapping("/pessoa")
    public ResponseEntity<Pessoa> savePessoa(@Valid @RequestBody Pessoa pessoa){
        Pessoa pessoanew = pessoaService.savePessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoanew);
    }

    @GetMapping("/pessoa")
    public ResponseEntity<List<Pessoa>> getPessoa(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.FindAllPessoa());
    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<Optional<Pessoa>> putPessoa(@PathVariable(value = "id") int id, @Valid @RequestBody Pessoa pessoa) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(Optional.ofNullable(pessoaService.atualizarPessoa(id,pessoa)));
    }
    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity deletePessoa(@PathVariable(value = "id") int id){
        pessoaService.deletepessoa(id);
        return ResponseEntity.status(204).body("Pessoa foi deletada com sucesso");
    }

    @GetMapping("pessoa/{id}")
    public ResponseEntity<Pessoa> getOnePessoa(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findPessoaById(id));
    }

    @PostMapping("/endereco/{id}")
    public ResponseEntity<List<Endereco>> saveEndereco(@PathVariable(name = "id") int id, @Valid @RequestBody List<Endereco> endereco) throws Exception {
        List<Endereco> enderecos = enderecoService.saveEndereco(id,endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecos);
    }

    @GetMapping("/endereco/{id}")
    ResponseEntity<List<Endereco>> getAllEndereco(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.listarAllEndereco(id));
    }

}
