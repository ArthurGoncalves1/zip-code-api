package br.com.hub.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Serviço está em instalação. Aguarde.")
public class NotReadException extends Exception{

	private static final long serialVersionUID = 1L;

}
