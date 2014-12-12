package LabaJunit.LabaJunit.myatm;
import LabaJunit.LabaJunit.customATMexceptions.*; // exceptions

public class ATM {
	private double moneyInATM;
	private Card cardInATM;
	
	public ATM(double moneyInATM) throws IllegalArgumentATMException {
		this.moneyInATM = moneyInATM;
		this.cardInATM = null;
		if (this.moneyInATM < 0) 
			throw new IllegalArgumentException("It's impossible to create ATM with negative ballance");
		
	}
	
	public boolean isCardInATM()
	{
		return cardInATM == null;
	}
	
	public double getMoneyInATM() {
		return moneyInATM;
	}
	public boolean validateCard(Card card, int pinCode) {
		if (!card.isBlocked() && card.checkPin(pinCode)) {
			cardInATM = card;
			return true;
		}
		
		return false;
	}
	public double checkBalanse() throws NoCardInsertedException{
		if (cardInATM == null) throw new NoCardInsertedException("It's impossible to check balanse. Card not exsist");
		return cardInATM.getAccount().getBalanse();
	}
	public double getCash(double amount) 
			throws NoCardInsertedException, NotEnoughtMoneyInAccountException, NotEnoughtMoneyInATMexception {
		
		if (isCardInATM()) 
			throw new NoCardInsertedException("It's impossible to get cash. Card not exsist");
		if (checkBalanse() < amount) 
			throw new NotEnoughtMoneyInAccountException("Not enought money in account");
		if (getMoneyInATM() < amount) 
			throw new NotEnoughtMoneyInATMexception("Not enought money in ATM");
		
		cardInATM.getAccount().withdraw(amount);
		moneyInATM -= amount;
		return cardInATM.getAccount().getBalanse();
	}
}
