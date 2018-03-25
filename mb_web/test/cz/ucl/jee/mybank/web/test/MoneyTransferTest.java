package cz.ucl.jee.mybank.web.test;

import cz.ucl.jee.mybank.MoneyTransfer;

import cz.ucl.jee.mybank.entity.PaymentOrder;
import cz.ucl.jee.mybank.transfer.OrderSender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferTest {

    @Test
    public void testSendingOrder() {
        //TODO
    }
}
