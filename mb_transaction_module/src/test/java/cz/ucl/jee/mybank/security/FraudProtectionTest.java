package cz.ucl.jee.mybank.security;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import cz.ucl.jee.mybank.accounts.Account;
import cz.ucl.jee.mybank.accounts.AccountBlackList;
import cz.ucl.jee.mybank.entity.PaymentOrder;
import cz.ucl.jee.mybank.transfer.OrderSender;

@RunWith(MockitoJUnitRunner.class)
public class FraudProtectionTest {
	
	@Mock
	private OrderSender orderSender;
	
	@Mock
	private AccountBlackList blackList;
	
	@InjectMocks
	private FraudProtectionDecorator fpDecorator;

	@Test
    public void testMaxAmount() {
		PaymentOrder order = new PaymentOrder();
		//amount is equal to maximum - sendPaymentOrder should be executed
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT);							
		fpDecorator.sendPaymentOrder(order);
		//verify, that the method was called
		Mockito.verify(orderSender, Mockito.times(1)).sendPaymentOrder(order);
	}

	@Test
	public void testOverMaxAmount() {
		PaymentOrder order = new PaymentOrder();
		//amount is greater than allowed - sendPaymentOrder should not execute
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT.add(BigDecimal.ONE) );
		fpDecorator.sendPaymentOrder(order);
		//verify, that the method has not been run (0 times)
		Mockito.verify(orderSender, Mockito.times(0)).sendPaymentOrder(order);
	}
	
	@Test
    public void testBlacklistedAccount() {
		PaymentOrder order = new PaymentOrder();
		Account creditAccount = Account.builder().number(123456L).build();
		order.setCreditAccount(creditAccount);
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT);
		
		//set the blackList mock to return true to specific account
		Mockito.when(blackList.isAccountOnList(creditAccount)).thenReturn(true);
				
		fpDecorator.sendPaymentOrder(order);
		
		//verify the method was not called
		Mockito.verify(orderSender, Mockito.times(0)).sendPaymentOrder(order);
    }

	@Test
	public void testSafeAccount() {
		PaymentOrder order = new PaymentOrder();
		Account creditAccount = Account.builder().number(123456L).build();
		order.setCreditAccount(creditAccount);
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT);

		//set the blackList mock to return true to specific account
		Mockito.when(blackList.isAccountOnList(creditAccount)).thenReturn(false);

		fpDecorator.sendPaymentOrder(order);

		//verify the method was not called
		Mockito.verify(orderSender, Mockito.times(1)).sendPaymentOrder(ArgumentMatchers.any());
	}
}
