package com.attornatus.demo.repositories;

import com.attornatus.demo.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEnderecos extends JpaRepository<Endereco, Integer> {
}
