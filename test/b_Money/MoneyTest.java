package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, SEK100.getAmount().intValue());
		assertEquals(1000, EUR10.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500.0, SEK100.universalValue(), 0.01);
		assertEquals(3000.0, SEK200.universalValue(), 0.01);
		assertEquals(1500.0, EUR10.universalValue(), 0.01);
		assertEquals(3000.0, EUR20.universalValue(), 0.01);
	}

	@Test
	public void testEqualsMoney() {
		assertFalse(SEK100.equals(SEK200));
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money add = EUR10.add(SEK100);
		int res = (int)((1000) + (10000 * 0.15 / 1.5));
		assertEquals(res, add.getAmount(), 0.01);;
	}




	@Test
	public void testSub() {
		Money subbed = SEK200.sub(SEK100);
		int res = (int) (20000 - 10000 * 0.15 / 0.15);
		assertEquals(res, subbed.getAmount(), 0.01);;
	}

	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero());
		assertTrue(SEK0.isZero());

	}

	@Test
	public void testNegate() {
		assertEquals(-10000, SEK100.negate().getAmount(), 0);
		assertEquals(-1000, EUR10.negate().getAmount(), 0);


	}

	@Test
	public void testCompareTo() {
		assertEquals(-1, SEK0.compareTo(SEK200));

	}
}
