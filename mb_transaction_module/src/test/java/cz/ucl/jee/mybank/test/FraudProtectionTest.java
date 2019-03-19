package cz.ucl.jee.mybank.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cz.ucl.jee.mybank.accounts.Account;
import cz.ucl.jee.mybank.accounts.AccountBlackList;
import cz.ucl.jee.mybank.entity.PaymentOrder;
import cz.ucl.jee.mybank.security.FraudProtectionDecorator;
import cz.ucl.jee.mybank.transfer.OrderSender;

@RunWith(MockitoJUnitRunner.class)
public class FraudProtectionTest {
	
	@Mock
	OrderSender orderSender;
	
	@Mock
	AccountBlackList blackList;
	
	@InjectMocks
	FraudProtectionDecorator fpDecorator;

	@Test
    public void testMaxAmount() {
		PaymentOrder order = new PaymentOrder();
			
		//amount is greater than allowed - sendPaymentOrder should not execute
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT.add(BigDecimal.ONE) );							
		fpDecorator.sendPaymentOrder(order);
		//verify, that the method has not been run (0 times)
		Mockito.verify(orderSender, Mockito.times(0)).sendPaymentOrder(order);
				
		//amount is equal to maximum - sendPaymentOrder should not execute
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT);							
		fpDecorator.sendPaymentOrder(order);
		//verify, that the method was called
		Mockito.verify(orderSender, Mockito.times(1)).sendPaymentOrder(order);
	}
	
	@Test
    public void testAccount() {		
		PaymentOrder order = new PaymentOrder();	
		Account creditAccount = new Account.Builder().number(123456L).build();
		order.setCreditAccount(creditAccount);
		order.setAmount(FraudProtectionDecorator.MAX_AMOUNT);
		
		//set the blackList mock to return true to specific account
		Mockito.when(blackList.isAccountOnList(creditAccount)).thenReturn(true);
				
		fpDecorator.sendPaymentOrder(order);
		
		//verify the method was not called
		Mockito.verify(orderSender, Mockito.times(0)).sendPaymentOrder(order);		
    }
}
