package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());

	}

	@Test
	public void testGetRate() {
		double precision = 0.01;

		assertEquals(1.5, EUR.getRate(), precision);
		assertEquals(0.15, SEK.getRate(), precision);
		assertEquals(0.20, DKK.getRate(), precision);
	}


	@Test
	public void testSetRate() {
		EUR.setRate(123.90);
		double precision = 0.01;
		assertEquals(123.90, EUR.getRate(), precision);
	}

	@Test
	public void testGlobalValue() {
		Money moneyInSEK = new Money(100, SEK);
		Money moneyInDKK = new Money(100, DKK);

		double globalValueInSEK = SEK.universalValue(moneyInSEK.getAmount());
		double globalValueInDKK = DKK.universalValue(moneyInDKK.getAmount());

		assertEquals(15.0, globalValueInSEK, 0.01);
		assertEquals(20.0, globalValueInDKK, 0.01);
	}

	@Test
	public void testValueInThisCurrency() {

		var amountSekToEur = EUR.valueInThisCurrency(1000, SEK);
		assertEquals(100, amountSekToEur, 0.01);
	}


}
