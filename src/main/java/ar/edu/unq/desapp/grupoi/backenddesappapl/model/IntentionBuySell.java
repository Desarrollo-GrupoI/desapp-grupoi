package ar.edu.unq.desapp.grupoi.backenddesappapl.model;

import java.time.LocalDateTime;

public class IntentionBuySell {
	private Long id;
	private CryptoSymbol cryptoCurrency;
	private Float cryptoAmount;
	private Float price;
	private Float pesosArgAmount;
	private String userName;
	private String userLastName;
	private Operation operation;
	private LocalDateTime date = LocalDateTime.now();
	
	public IntentionBuySell(CryptoSymbol cryptoCurrency,Float cryptoAmount, Float price, Float pesosArgAmount, String userName, String userLastName, Operation operation) {
		this.cryptoCurrency = cryptoCurrency;
		this.cryptoAmount = cryptoAmount;
		this.price = price;
		this.pesosArgAmount = pesosArgAmount;
		this.userName = userName;
		this.userLastName = userLastName;		
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
	
	public String getUserName() {
		return this.userName ;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserLastName() {
		return this.userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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
