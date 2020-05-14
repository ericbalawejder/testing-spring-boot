package guru.springframework;

import java.util.Objects;

public class Money {

    private final Double amount;
    private final String currency;

    Money(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    Money times(Money value) {
        return new Money(this.amount * value.amount, this.currency);
    }

    String getCurrency() {
        return this.currency;
    }

    Double getAmount() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) &&
                Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money {" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}