package LabaJunit.LabaJunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import LabaJunit.LabaJunit.myatm.ATM;
import LabaJunit.LabaJunit.myatm.Account;
import LabaJunit.LabaJunit.myatm.Card;
import LabaJunit.LabaJunit.customATMexceptions.*;

public class AtmTest {
	
	
	@Test
	(expected = IllegalArgumentException.class)
	public void getIllegalArgumentException() 
			throws IllegalArgumentException, IllegalArgumentATMException{
		ATM atm = new ATM(-1000);
		
	}
	
	
	
	@Test
	public void getMoneyInATMDoubleReturns() throws IllegalArgumentATMException {
		double actualBalanse = 1000;
		double expectedBalanse;
		ATM atm = new ATM(actualBalanse);
		expectedBalanse = atm.getMoneyInATM();
		assertEquals(actualBalanse, expectedBalanse, 0.0000001);
	}
	@Test
	public void validateCardValidCardValidPinTRUE() throws IllegalArgumentATMException, NoCardInsertedException {
		ATM atm = new ATM(1000);
		Card card = mock(Card.class);
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		assertTrue(atm.validateCard(card, 1111));
		
			
		verify(card,  atLeastOnce()).isBlocked();
		verify(card,  atLeastOnce()).checkPin(1111);
	}
	
	@Test
	public void validateCardInvalidCardValidPinFALSE() throws IllegalArgumentATMException {
		ATM atm = new ATM(1000);
		Card card = mock(Card.class);
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(card, 1111));
		verify(card,  atLeastOnce()).isBlocked();
	}
	
	@Test
	public void validateCardValidCardInvalidPinFALSE() throws IllegalArgumentATMException {
		ATM atm = new ATM(1000);
		Card card = mock(Card.class);
		when(card.checkPin(1111)).thenReturn(false);
		when(card.isBlocked()).thenReturn(false);
		assertFalse(atm.validateCard(card, 1111));
		verify(card,  atLeastOnce()).isBlocked();
		verify(card,  atLeastOnce()).checkPin(1111);
	}
	
	@Test 
	public void checkBalanseCardInATMDoubleReturns() throws NoCardInsertedException, IllegalArgumentATMException { 
		ATM atm = new ATM(1000);
		Card card = mock(Card.class);
		Account account = mock(Account.class);
		double expectedBalanse = 100;
		double actualBalanse = 0;
		
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		when(card.getAccount()).thenReturn(account);
		when(account.getBalanse()).thenReturn(expectedBalanse);
		assertTrue(atm.validateCard(card, 1111)); 

		actualBalanse = atm.checkBalanse();
		assertEquals(actualBalanse, expectedBalanse, 0.0000001);
		verify(account,  atLeastOnce()).getBalanse();
	}
	
	@Test (expected = NoCardInsertedException.class)
	public void checkBalanseNoCardInATMNoCardInATMException() throws NoCardInsertedException, IllegalArgumentATMException {
		ATM atm = new ATM(1000);
		atm.checkBalanse();
	}
	
	@Test 
	public void getCashCardInATMEnoughtMoneyInATMEnoughtMoneyInAccountDoubleReturns() 
			throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception, IllegalArgumentATMException{
		ATM atm = spy(new ATM(1000));
		Card card = mock(Card.class);
		Account account = mock(Account.class);
		double expectedBalanse = 1000;
		
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		when(card.getAccount()).thenReturn(account);
		when(account.getBalanse()).thenReturn(expectedBalanse);
		assertTrue(atm.validateCard(card, 1111)); 
		atm.getCash(1000);
		verify(account,  atLeastOnce()).getBalanse();
		verify(atm,  atLeastOnce()).getMoneyInATM();
	}
	
	@Test (expected = NoCardInsertedException.class)
	public void getCashNoCardInATMNoCardInATMException() 
			throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception, IllegalArgumentATMException{
		ATM atm = new ATM(1000);
		atm.getCash(50);

	}
	
	@Test (expected = NotEnoughtMoneyInAccountException.class)
	public void getCashCardInATMEnoughtMoneyInATMNotEnoughtMoneyInAccountNotEnoughtMoneyInAccountException() 
			throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception, IllegalArgumentATMException{
		ATM atm = spy(new ATM(1000));
		Card card = mock(Card.class);
		Account account = mock(Account.class);
		double expectedBalanse = 10;
		
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		when(card.getAccount()).thenReturn(account);
		when(account.getBalanse()).thenReturn(expectedBalanse);
		assertTrue(atm.validateCard(card, 1111));
		
		atm.getCash(50);
	}
	
	@Test (expected = NotEnoughtMoneyInATMexception.class)
	public void getCashCardInATMNotEnoughtMoneyInATMEnoughtMoneyInAccountNotEnoughtMoneyInATMexception() 
			throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception, IllegalArgumentATMException{
		ATM atm = spy(new ATM(1000));
		Card card = mock(Card.class);
		Account account = mock(Account.class);
		double expectedBalanse = 10000;
		
		when(card.checkPin(1111)).thenReturn(true);
		when(card.isBlocked()).thenReturn(false);
		when(card.getAccount()).thenReturn(account);
		when(account.getBalanse()).thenReturn(expectedBalanse);
		assertTrue(atm.validateCard(card, 1111)); 
		
		atm.getCash(5000);
	} 
}
