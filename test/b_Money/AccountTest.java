package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 5, 10, new Money(1000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}

	@Test
	public void testTimedPayment() throws AccountExistsException, AccountDoesNotExistException {
		testAccount.addTimedPayment("payment2", 1, 1, new Money(3000, SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(9997000, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(10000000-1000, testAccount.getBalance().getAmount(), 0.01);;
	}

	@Test
	public void testGetBalance() {
		Money balance = testAccount.getBalance();
		assertTrue(new Money(10000000, SEK).equals(balance));	}
}
