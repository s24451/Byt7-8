package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
		assertNotNull(SweBank.getBalance("Alice"));

	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(666, SEK));
		assertEquals(666, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(666, SEK));
		SweBank.withdraw("Bob", new Money(665, SEK));
		assertEquals(1,SweBank.getBalance("Bob").intValue() );

	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(666, SEK));
		assertEquals(666, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {


		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.transfer("Bob", Nordea, "Bob", new Money(100000000, SEK));

		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(100000000, Nordea.getBalance("Bob").intValue());

		SweBank.deposit("Ulrika", new Money(234, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(100, SEK));
		assertEquals(100, SweBank.getBalance("Bob").intValue());
		assertEquals(134, SweBank.getBalance("Ulrika").intValue());




	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(100000000, SEK));
		SweBank.addTimedPayment("Bob", "1", 10, 0, new Money(100000000, SEK), SweBank, "Ulrika");
		SweBank.tick();
		assertEquals(0, SweBank.getBalance("Bob").intValue());
	}
}
