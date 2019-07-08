package br.com.autopass.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@ApiModel(description = "Sale order with a list of one or more items")
public class Sale implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated sale ID")
	private Integer id;
	
	@NotNull
	@ApiModelProperty(notes = "Client name for the sale")
	private String client;
	
	@NotNull
	@ApiModelProperty(notes = "List with the items for the sale")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Item> itens;
}
