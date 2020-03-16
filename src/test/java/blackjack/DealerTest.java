package blackjack;

import blackjack.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DealerTest {
    private List<Card> cards;
    private CardDeck cardDeck;
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        cards = new ArrayList<>(
                Arrays.asList(
                        new Card(Symbol.CLOVER, Type.EIGHT),
                        new Card(Symbol.CLOVER, Type.ACE),
                        new Card(Symbol.DIAMOND, Type.JACK),
                        new Card(Symbol.HEART, Type.SEVEN),
                        new Card(Symbol.SPADE, Type.QUEEN),
                        new Card(Symbol.HEART, Type.TEN)
                )
        );
        cardDeck = new CardDeck(cards);
        dealer = Dealer.getDealer();
    }

    @DisplayName("카드 덱에서 뽑았을 때 유저가 가지고 있는 카드 수와 덱의 카드 수가 동시에 변하는지 확인")
    @Test
    void addCardTest() {
        dealer.receiveDistributedCards(cardDeck);
        assertThat(dealer.getCardsSize()).isEqualTo(2);
        assertThat(cardDeck.size()).isEqualTo(4);
    }

    @DisplayName("현재 보유 중인 카드의 총 점수 계산")
    @Test
    void calculateScoreTest() {
        dealer.receiveDistributedCards(cardDeck);
        int score = dealer.calculateScore();
        assertThat(score).isEqualTo(19);
    }

    @DisplayName("딜러의 점수가 16이하인지 확인")
    @Test
    void isUnderCriticalScore() {
        dealer.receiveDistributedCards(cardDeck);
        assertThat(dealer.isReceivableOneMoreCard()).isFalse();
    }

    @DisplayName("딜러의 이름을 반환하는지 확인")
    @Test
    void getNameTest() {
        User dealer = Dealer.getDealer();
        assertThat(dealer.getName()).isEqualTo("딜러");
    }

    @DisplayName("딜러의 초기 카드 오픈상태 확인")
    @Test
    void getInitialCardsTest() {
        dealer.receiveDistributedCards(cardDeck);
        assertThat(dealer.getInitialCards()).containsExactly(new Card(Symbol.CLOVER, Type.EIGHT));
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        Field dealer_instance = Dealer.class.getDeclaredField("dealerInstance");
        dealer_instance.setAccessible(true);
        dealer_instance.set(null, null);
    }
}