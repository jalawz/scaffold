package br.com.autopass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.autopass.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
