package br.com.hub.correios.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
	
	@Id
	private String zipcode;
	@Column
	private String street;
	@Column
	private String district;
	@Column
	private String city;
	@Column
	private String state;
	
}
