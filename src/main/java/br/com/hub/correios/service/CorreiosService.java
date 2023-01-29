package br.com.hub.correios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.com.hub.correios.CorreiosApplication;
import br.com.hub.correios.exception.NoContentException;
import br.com.hub.correios.exception.NotReadException;
import br.com.hub.correios.model.Address;
import br.com.hub.correios.model.AddressStatus;
import br.com.hub.correios.model.Status;
import br.com.hub.correios.repository.AddressRepository;
import br.com.hub.correios.repository.AddressStatusRepository;
import br.com.hub.correios.repository.SetupRepository;

@Service
public class CorreiosService {
	
	private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressStatusRepository addressStatusRepository;
	
	@Autowired
	private SetupRepository setupRepository;
	
	
	public void setup() throws Exception{
		logger.info("-----");
		logger.info("-----");
		logger.info("Setup Running");
		logger.info("-----");
		logger.info("-----");
		if(this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);
			try {
				this.addressRepository.saveAll(this.setupRepository.listAdressesFromOrigin());
			}catch (Exception e) {
				saveStatus(Status.NEED_SETUP);
				throw e;
			}
			this.saveStatus(Status.READY);
		}
		logger.info("-----");
		logger.info("Service Read");
		logger.info("-----");
	}
	
	@EventListener(ApplicationStartedEvent.class)
	protected void setupOnStartup() {
		try {
			setup();
		} catch (Exception e) {
			CorreiosApplication.close(999);
			logger.error(".setupOnStatup() - Exception");
		}
	}
	
	private void saveStatus(Status status) {
		AddressStatus a = new AddressStatus();
		a.setId(AddressStatus.DEFAULT_ID);
		a.setStatus(status);
		addressStatusRepository.save(a);
		
	}
	
	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
				.getStatus();
	}
	
	public Address getAddresByZipCode(String zipcode) throws NoContentException, NotReadException {
		if(!this.getStatus().equals(Status.READY)) {
			throw new NotReadException();
		}
		
		return addressRepository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
}
