package br.com.autopass.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document(collection = "user")
@ApiModel(description = "All details about the User")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "Object id generated for the user")
	private String id;
	
	@NotNull
	@ApiModelProperty(notes = "Name of the user")
	private String name;
	
	@NotNull
	@ApiModelProperty(notes = "Age of the user")
	private Integer age;
}
