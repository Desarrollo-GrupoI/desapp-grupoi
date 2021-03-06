package ar.edu.unq.desapp.grupoi.backenddesappapl.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.TransactionActionDTO;
import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.TransactionDTO;
import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.RegisterTransactionDTO;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.Intention;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.Transaction;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.TransactionState;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.User;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.exceptions.EntityNotFoundException;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.exceptions.InvalidArgumentsException;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.exceptions.SystemException;
import ar.edu.unq.desapp.grupoi.backenddesappapl.repositories.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private DateService dateService;
	@Autowired
	private CryptoCurrencyService cryptoCurrencyService;
	@Autowired
	private IntentionService intentionService;
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	public void saveTransaction(RegisterTransactionDTO transactionDTO) {
		User user = this.userService.findById(transactionDTO.getUserEmail());
		Intention intention = this.intentionService.findById(transactionDTO.getIntentionId());
		
		if(StringUtils.equals(transactionDTO.getUserEmail(), intention.getUser().getEmail()))
			throw new InvalidArgumentsException("You cannot make a transaction with yourself");
		
		Transaction transaction = new Transaction(intention, user, TransactionState.PENDING);

		this.transactionRepository.save(transaction);
	} 

	@Transactional
	public void acceptTransaction(TransactionActionDTO transactionDTO) throws SystemException {
		Transaction transaction = this.findById(transactionDTO.getTransactionId());	
		if(transaction.getState() == TransactionState.PENDING) {
			Intention intention = transaction.getTransactionIntention();
			
			User userTransaction = userService.findById(transaction.getUser().getEmail());
			User userIntention = userService.findById(intention.getUser().getEmail());
			
			if(!StringUtils.equals(transactionDTO.getUserEmail(), userIntention.getEmail()))
				throw new InvalidArgumentsException("You cannot accept this transaction");
			
			Float cryptoPrice = this.cryptoCurrencyService.getCryptoBySymbol(intention.getCryptoSymbol().toString()).getPrice();
			if(cryptoPrice * 0.95 > intention.getPrice() || intention.getPrice() > cryptoPrice * 1.05) {
				transaction.setState(TransactionState.CANCELED);
				
				this.transactionRepository.save(transaction);
				
				throw new SystemException("The transaction was canceled: the price is not in +/- 5 % range");
			} else {
				transaction.setState(TransactionState.DONE);
				
				int points = Duration.between(transaction.getDate(), dateService.getDate()).toMinutes() <= 30 ? 10 : 5;
				
				this.userService.addOperation(userIntention.getEmail(), userTransaction.getEmail());
				this.userService.updateReputationPoints(userTransaction.getEmail(), userIntention.getEmail(), points, true);
				
				this.transactionRepository.save(transaction);
				this.transactionRepository.cancelAllOthersTransactions(transactionDTO.getTransactionId(), intention.getId());
			}
		} else
			throw new InvalidArgumentsException("The transaction has already been accepted or canceled");
	} 

	@Transactional
	public void cancelTransaction(TransactionActionDTO transactionDTO) {
		if(!this.userService.existsById(transactionDTO.getUserEmail()))
			throw new EntityNotFoundException("The user was not found");
		
		Transaction transaction = this.findById(transactionDTO.getTransactionId());	
		
		if(transaction.getState() == TransactionState.PENDING) {			
			transaction.setState(TransactionState.CANCELED);
			
			this.transactionRepository.save(transaction);
			this.userService.updateReputationPoints(transactionDTO.getUserEmail(), 20, false);
		} else
			throw new InvalidArgumentsException("The transaction has already been accepted or canceled");
	}
	
	public Transaction findById(Integer transactionId) {
		try {
			return this.transactionRepository.findById(transactionId).get();
		} catch(NoSuchElementException e) {
			throw new EntityNotFoundException("The transaction was not found");
		}
	}
	
	public List<Transaction> findDoneTransactions(String email, LocalDateTime dateFrom, LocalDateTime dateTo) {
		return this.transactionRepository.findDoneTransactions(email, dateFrom, dateTo);
	}
	
	public List<TransactionDTO> findAllActives() {
		List<Transaction> transactions = this.transactionRepository.findAllActives();
		List<TransactionDTO> transactionsDTO = new ArrayList<TransactionDTO>();
		
		for(Transaction transaction : transactions) {
			transactionsDTO.add(
					new TransactionDTO(
							transaction.getTransactionIntention().getId(),
							transaction.getTransactionIntention().getDate(),
							transaction.getCryptoSymbol(),
							transaction.getCryptoAmount(),
							transaction.getTransactionIntention().getPrice(),
							transaction.getPesosArgAmount(),
							transaction.getState(),
							transaction.getUser().getName(),
							transaction.getUser().getSurname(),
							transaction.getUser().getOperations(),
							transaction.getUser().getReputation()
							)
					);
		}
		return transactionsDTO;					
		 
	} 
}
