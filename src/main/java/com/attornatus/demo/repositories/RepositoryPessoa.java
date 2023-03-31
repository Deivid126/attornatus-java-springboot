package com.attornatus.demo.repositories;

import com.attornatus.demo.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryPessoa extends JpaRepository<Pessoa,Integer> {

}
