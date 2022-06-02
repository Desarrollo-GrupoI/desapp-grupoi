package ar.edu.unq.desapp.grupoi.backenddesappapl.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_intention")
public class IntentionBuySell {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private CryptoSymbol cryptoCurrency;
	private Float cryptoAmount;
	private Float price;
	private Float pesosArgAmount;
	@OneToOne
	@JoinColumn(name="user_email")
	private User user;
	@Enumerated(EnumType.STRING)
	private Operation operation;
	private LocalDateTime date = LocalDateTime.now();
	
	public IntentionBuySell() {}
	
	public IntentionBuySell(CryptoSymbol cryptoCurrency,Float cryptoAmount, Float price, Float pesosArgAmount, User user, Operation operation) {
		this.cryptoCurrency = cryptoCurrency;
		this.cryptoAmount = cryptoAmount;
		this.price = price;
		this.pesosArgAmount = pesosArgAmount;
		this.user= user;
		this.operation = operation;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public CryptoSymbol getCryptoCurrency() {
		return this.cryptoCurrency;
	}
	
	public void setCryptoCurrency(CryptoSymbol cryptoCurrency) {
		this.cryptoCurrency = cryptoCurrency;
	}
		
	public Float getCryptoAmount() {
		return this.cryptoAmount;
	}

	public void setCryptoAmount(Float cryptoAmount) {
		this.cryptoAmount = cryptoAmount;
	}
	
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Float getPesosArgAmount() {
		return this.pesosArgAmount;
	}

	public void setPesosArgAmount(Float pesosArgAmount) {
		this.pesosArgAmount = pesosArgAmount;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
