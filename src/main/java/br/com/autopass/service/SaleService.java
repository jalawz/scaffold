package br.com.autopass.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.autopass.exceptions.SaleNotFoundException;
import br.com.autopass.model.Sale;
import br.com.autopass.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaleService {
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Cacheable(value = "sales", key = "'all-sales'")
	public List<Sale> retrieveAllSales() {
		try {
			log.info("Retrieving all sales");
			return saleRepository.findAll();
		} catch (Exception e) {
			log.error("Error retrieving sales");
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@CacheEvict(value = "sales", allEntries=true)
	public Sale insertSale(Sale sale) {
		log.info("Inserting a new sale");
		return saleRepository.save(sale);
	}
	
	@Cacheable(value = "sale", key = "#id")
	public Sale findSale(Integer id) {
		log.info("Retrieving sale with id: {}", id);
		Optional<Sale> sale = saleRepository.findById(id);
		
		return sale.orElseThrow(() ->
			new SaleNotFoundException(String.format("Sale: %s was not found", id))
		);
	}
	
	@CachePut(value = "sales", key = "#id")
	public Sale updateSale(Integer id, Sale saleRequest) {
		Sale foundSale = findSale(id);
		saleRequest.setId(foundSale.getId());
		log.info("Saving sale");
		return saleRepository.save(saleRequest);
	}
	
	@CacheEvict(value = "sales", allEntries=true)
	public void deleteSale(Integer id) {
		findSale(id);
		log.info("Deleting sale with id: {}", id);
		saleRepository.deleteById(id);
	}
	
	public void send(Sale message) {
		template.convertAndSend("/topic/hello", message);
	}
}
