package guru.springframework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTest {

    @Test
    void testMultiplicationOfDollar() {
        Money five = new Money(5d, "USD");
        Money seven = new Money(7d, "USD");
        assertEquals(new Money(35d, "USD"), five.times(seven));
    }

    @Test
    void testMultiplicationOfFranc() {
        Money five = new Money(5d, "CHF");
        Money seven = new Money(7d, "CHF");
        assertEquals(new Money(35d, "CHF"), five.times(seven));
    }

    @Test
    void testMultiplicationOfFrancAndDollar() {
        Money five = new Money(5d, "USA");
        Money seven = new Money(7d, "CHF");
        assertNotEquals(new Money(35d, "USD"), five.times(seven));
    }

    @Test
    void testEqualityOfDollar() {
        assertEquals(new Money(5d, "USD"), new Money(5d, "USD"));
    }

    @Test
    void testEqualityOfFranc() {
        assertEquals(new Money(5d, "CHF"), new Money(5d, "CHF"));
    }

    @Test
    void testDifferentMoneyNotEqual() {
        assertNotEquals(new Money(5d, "USD"), new Money(5d, "CHF"));
    }

    @Test
    void testCurrencyDollar() {
        Money dollar = new Money(1d, "USD");
        assertEquals("USD", dollar.getCurrency());
    }

    @Test
    void testCurrencyFranc() {
        Money franc = new Money(1d, "CHF");
        assertEquals("CHF", franc.getCurrency());
    }

    @Test
    void testCurrencyConversionUSDtoCHF() {
        Money dollar = new Money(12d, "USD");
        Bank bank = new Bank();
        assertEquals(bank.exchange(new Pair("USD", "CHF"), dollar).getAmount(), Double.valueOf(11.67));
    }

    @Test
    void testCurrencyConversionGBPtoCHF() {
        Money pound = new Money(20d, "GBP");
        Bank bank = new Bank();
        assertEquals(bank.exchange(new Pair("GBP", "CHF"), pound).getAmount(), Double.valueOf(23.76));
    }

    @Test
    void testCurrencyConversionNotFound() {
        Money pound = new Money(20d, "GBP");
        Bank bank = new Bank();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> bank.exchange(new Pair("GBP", "XXX"), pound));

        String expectedMessage = "Conversion not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
