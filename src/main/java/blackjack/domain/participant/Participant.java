package blackjack.domain.participant;

import java.util.List;
import java.util.Objects;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;

public abstract class Participant {

    private static final int BLACKJACK_CARD_SIZE = 2;
    private static final int BLACKJACK_SCORE = 21;
    private static final int BUST_STANDARD = 21;

    private final Name name;
    protected final Cards cards;

    protected Participant(Name name, Cards cards) {
        validateName(name);
        validateCards(cards);
        this.name = name;
        this.cards = cards;
    }

    private void validateCards(Cards cards) {
        Objects.requireNonNull(cards, "[ERROR] 카드는 null 일 수 없습니다.");
    }

    private void validateName(Name name) {
        Objects.requireNonNull(name, "[ERROR] 이름은 null 일 수 없습니다.");
    }

    public void hit(Card card) {
        cards.add(card);
    }

    public int calculateScore() {
        return cards.calculateScore();
    }

    public boolean isBust() {
        return cards.isOverScoreThan(BUST_STANDARD);
    }

    public boolean isBlackjack() {
        return cards.isSameSize(BLACKJACK_CARD_SIZE) && cards.isSameScore(BLACKJACK_SCORE);
    }

    public boolean isHittable() {
        return cards.isLessScoreThan(getHitStandard());
    }

    public List<Card> showFirstCards() {
        return cards.getFrontCards(getFirstOpenCardSize());
    }

    protected abstract int getHitStandard();

    protected abstract int getFirstOpenCardSize();

    public List<Card> getCards() {
        return cards.getValues();
    }

    public String getName() {
        return name.getValue();
    }
}