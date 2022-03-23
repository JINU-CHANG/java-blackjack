package blackjack.domain.players.participant;

import blackjack.domain.cards.CardDeck;
import blackjack.domain.cards.card.Card;
import blackjack.domain.players.participant.name.Name;
import blackjack.domain.result.BetAmount;
import blackjack.domain.state.finished.Finished;
import java.util.List;

public final class Player extends Participant {
    private final BetAmount betAmount;

    public Player(final String name, final int money, final List<Card> cards) {
        super(cards, new Name(name));
        this.betAmount = new BetAmount(money);
    }

    public void draw(CardDeck cardDeck, boolean isHit) {
        if (isHit) {
            addCard(cardDeck.pop());
            return;
        }
        stay();
    }

    public int getProfit(Dealer dealer) {
        return state.profit(betAmount.get(), (Finished) dealer.getState());
    }
}
