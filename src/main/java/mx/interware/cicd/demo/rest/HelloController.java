package mx.interware.cicd.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}

	@RequestMapping(value = "/greetings/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> greetings(@PathVariable(value = "name") String name) {
		String message = "Greetings from Spring Boot " + name + "!";
		return new ResponseEntity(message, HttpStatus.OK);
	}

}
