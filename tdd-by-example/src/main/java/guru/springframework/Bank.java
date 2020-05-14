package guru.springframework;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private static final Map<Pair, Double> MARKET_EXCHANGE;
    static {
        Map<Pair, Double> money = new HashMap<>();
        money.put(new Pair("USD", "CHF"), 0.972125);
        money.put(new Pair("CHF", "USD"), 1.02867);
        money.put(new Pair("GBP", "USD"), 1.22374);
        money.put(new Pair("USD", "GBP"), 0.817167);
        money.put(new Pair("CHF", "GBP"), 0.841879);
        money.put(new Pair("GBP", "CHF"), 1.18782);
        MARKET_EXCHANGE = Collections.unmodifiableMap(money);
    }

    Money exchange(Pair conversion, Money money) {
        if (!MARKET_EXCHANGE.containsKey(conversion)) {
            throw new IllegalArgumentException("Conversion not found");
        }
        return new Money(
                round(MARKET_EXCHANGE.get(conversion) * money.getAmount()),
                money.getCurrency());
    }

    private Double round(Double value) {
        if (2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(Double.toString(value))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
