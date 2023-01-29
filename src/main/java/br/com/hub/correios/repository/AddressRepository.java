package br.com.hub.correios.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.hub.correios.model.Address;

public interface AddressRepository extends CrudRepository<Address, String>{

}
