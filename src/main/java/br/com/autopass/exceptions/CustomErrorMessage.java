package br.com.autopass.exceptions;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomErrorMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CustomErrorMessage(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}
	
	private Integer status;
	private String msg;
	private Long timeStamp;
}
