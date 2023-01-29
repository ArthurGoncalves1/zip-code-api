package br.com.hub.correios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.hub.correios.exception.NoContentException;
import br.com.hub.correios.exception.NotReadException;
import br.com.hub.correios.model.Address;
import br.com.hub.correios.service.CorreiosService;

@RestController
public class CorreiosController {
	
	@Autowired
	private CorreiosService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Service status: " + service.getStatus();
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public Address getAddressByZipCode(@PathVariable("zipcode") String zipcode) throws NoContentException, NotReadException {
		return service.getAddresByZipCode(zipcode);
	}
}


