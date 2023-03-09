package domain.player;

import domain.card.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DealerTest {

    @Test
    @DisplayName("카드가 한장뿐인 리스트를 반환한다.")
    void showCardsTest() {
        //given
        final Dealer dealer = Dealer.create(0);

        final List<Rank> ranks = List.of(Rank.EIGHT, Rank.SIX, Rank.SEVEN);
        final Deck deck = Deck.from(TestCardGenerator.from(ranks));

        ranks.forEach(i -> dealer.takeCard(deck.dealCard()));

        //when
        final List<Card> cards = dealer.showCards();

        //then
        assertThat(cards)
                .hasSize(1)
                .isEqualTo(List.of(Card.of(Suit.CLUBS, Rank.EIGHT)));
    }

    @Nested
    class CanHitTest {

        @Test
        @DisplayName("카드 점수의 총합이 16 이하면 true를 리턴한다")
        void givenHitNumber_thenReturnTrue() {
            //given
            final Dealer dealer = Dealer.create(15);

            //when
            final boolean dealerHit = dealer.canHit();

            //then
            assertThat(dealerHit).isTrue();
        }

        @Test
        @DisplayName("카드 점수의 총합이 17 이상이면 false를 리턴한다")
        void givenStayNumber_thenReturnFalse() {
            //given
            final Dealer dealer = Dealer.create(17);

            //when
            final boolean dealerHit = dealer.canHit();

            //then
            assertThat(dealerHit).isFalse();
        }
    }

}