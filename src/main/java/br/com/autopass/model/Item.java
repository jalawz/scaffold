package br.com.autopass.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@ApiModel(description = "Item of a sale")
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated item ID")
	private Integer id;
	
	@NotNull
	@ApiModelProperty(notes = "Name of the item")
	private String name;
	
	@NotNull
	@ApiModelProperty(notes = "Quantity of the sale item")
	private Integer quantity;
}
