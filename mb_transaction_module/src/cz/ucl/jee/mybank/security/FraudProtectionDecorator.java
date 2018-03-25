package cz.ucl.jee.mybank.security;

import java.math.BigDecimal;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import cz.ucl.jee.mybank.accounts.AccountBlackList;
import cz.ucl.jee.mybank.entity.PaymentOrder;
import cz.ucl.jee.mybank.transfer.OrderSender;

//TODO Annotate to make this class a decorator and declare it to implement OrderSender
public class FraudProtectionDecorator {

	public static BigDecimal MAX_AMOUNT = new BigDecimal(1000000);

	//TODO Annotate
	OrderSender orderSender;
	
	//TODO Inject AccountBlackList

	public void sendPaymentOrder(PaymentOrder order) {

		//TODO implmenent blacklist checking

		if (order.getAmount().compareTo(MAX_AMOUNT) > 0){
			System.out.println("Amount too large, transaction denied.");
			return;
		}

		orderSender.sendPaymentOrder(order);
	}

}
