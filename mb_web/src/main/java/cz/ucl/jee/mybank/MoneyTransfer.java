package cz.ucl.jee.mybank;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.ucl.jee.mybank.accounts.Account;
import cz.ucl.jee.mybank.entity.PaymentOrder;
import cz.ucl.jee.mybank.transfer.OrderSender;

import java.math.BigDecimal;

@RequestScoped
@Named
public class MoneyTransfer {
	
	@Inject
	private OrderSender orderSender;
	
	private int debitAccountPrefix;
	private long debitAccountNo;
	private String debitBankCode;
	
	private int creditAccountPrefix;
	private long creditAccountNo;
	private String creditBankCode;

	private BigDecimal amount;
	
	public void send(){
		PaymentOrder order = new PaymentOrder();
		Account debitAccount = Account.builder()
				.prefix(debitAccountPrefix)
				.number(debitAccountNo)
				.bankCode(debitBankCode)
				.build();
		order.setDebitAccount(debitAccount);
		
		Account creditAccount = Account.builder()
				.prefix(creditAccountPrefix)
				.number(creditAccountNo)
				.bankCode(creditBankCode)
				.build();
		order.setCreditAccount(creditAccount);
		order.setAmount(amount);
		orderSender.sendPaymentOrder(order);
	}
	
	
	public int getDebitAccountPrefix() {
		return debitAccountPrefix;
	}
	public void setDebitAccountPrefix(int debitAccountPrefix) {
		this.debitAccountPrefix = debitAccountPrefix;
	}
	public long getDebitAccountNo() {
		return debitAccountNo;
	}
	public void setDebitAccountNo(long debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}
	public String getDebitBankCode() {
		return debitBankCode;
	}
	public void setDebitBankCode(String debitBankCode) {
		this.debitBankCode = debitBankCode;
	}
	public int getCreditAccountPrefix() {
		return creditAccountPrefix;
	}
	public void setCreditAccountPrefix(int creditAccountPrefix) {
		this.creditAccountPrefix = creditAccountPrefix;
	}
	public long getCreditAccountNo() {
		return creditAccountNo;
	}
	public void setCreditAccountNo(long creditAccountNo) {
		this.creditAccountNo = creditAccountNo;
	}
	public String getCreditBankCode() {
		return creditBankCode;
	}
	public void setCreditBankCode(String creditBankCode) {
		this.creditBankCode = creditBankCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
