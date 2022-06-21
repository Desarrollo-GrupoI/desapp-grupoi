package ar.edu.unq.desapp.grupoi.backenddesappapl.webservice;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.RegisterTransactionActionDTO;
import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.RegisterTransactionDTO;
import ar.edu.unq.desapp.grupoi.backenddesappapl.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping(path = "/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterTransactionDTO transaction) {
		this.transactionService.saveTransaction(transaction);
		return ResponseEntity.ok().body("The transaction was registered");
	} 
	
	@PostMapping(path = "/accept")
	public ResponseEntity<?> accept(@Valid @RequestBody RegisterTransactionActionDTO transaction) {
		this.transactionService.acceptTransaction(transaction);
		return ResponseEntity.ok().body("The transaction was accepted");
	}
	
	@PostMapping(path = "/cancel")
	public ResponseEntity<?> cancel(@Valid @RequestBody RegisterTransactionActionDTO transaction) {
		this.transactionService.cancelTransaction(transaction);
		return ResponseEntity.ok().body("The transaction was canceled");
	}
}
