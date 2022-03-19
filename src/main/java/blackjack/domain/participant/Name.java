package blackjack.domain.participant;

import java.util.Objects;

public class Name {

    private final String value;

    public Name(String value) {
        validate(value);
        value = value.strip();
        this.value = value;
    }

    private void validate(String value) {
        Objects.requireNonNull(value, "[ERROR] 이름은 null일 수 없습니다.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름은 한 글자 이상이어야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Name name = (Name)o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}