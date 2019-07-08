package br.com.autopass.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.autopass.model.Sale;
import br.com.autopass.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sales")
@Api(value = "Sales Controller")
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@ApiOperation(value = "View a list of sales")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successful retrieved List of Sales"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping
	public ResponseEntity<List<Sale>> getAllSales() {
		List<Sale> sales = saleService.retrieveAllSales();
		return ResponseEntity.ok(sales);
	}
	
	@ApiOperation(value = "Insert a new Sale")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Successful Created"),
		@ApiResponse(code = 400, message = "Bad sale request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping
	public ResponseEntity<Void> insertSale(@ApiParam(value = "Sale object with its items to add to the database", required = true) 
		@Valid @RequestBody Sale sale) {
		Sale savedSale = saleService.insertSale(sale);
		template.convertAndSend("/topic/hello", savedSale);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}").buildAndExpand(savedSale.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Retrieve a single sale by id")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successful retrieve a sale by id"),
		@ApiResponse(code = 404, message = "Sale not found"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Sale> getSaleById(@ApiParam(value = "Sale id", required = true) @PathVariable Integer id) {
		Sale foundSale = saleService.findSale(id);
		return ResponseEntity.ok(foundSale);
	}
	
	@ApiOperation(value = "Update a sale object")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Successful updated a sale"),
		@ApiResponse(code = 404, message = "Sale not found"),
		@ApiResponse(code = 400, message = "Bad request for sale"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateSale(
			@ApiParam(value = "Sale id", required = true) @PathVariable Integer id,
			@ApiParam(value = "Sale object request to update", required = true) @Valid @RequestBody Sale sale) {
		saleService.updateSale(id, sale);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Delete a sale by id")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Successful deleted a sale"),
		@ApiResponse(code = 404, message = "Sale not found"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSale(@ApiParam(value = "Sale id", required = true) @PathVariable Integer id) {
		saleService.deleteSale(id);
		return ResponseEntity.noContent().build();
	}
	
	@MessageMapping("/hello")
	@SendTo("/topic/hello")
	public Sale saleMessage(@Payload Sale sale) {
		return sale;
	}
}
