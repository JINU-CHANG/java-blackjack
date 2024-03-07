package domain;

import static domain.CardNumber.ACE;
import static domain.CardNumber.EIGHT;
import static domain.CardNumber.FIVE;
import static domain.CardNumber.FOUR;
import static domain.CardNumber.JACK;
import static domain.CardNumber.KING;
import static domain.CardNumber.QUEEN;
import static domain.CardNumber.SEVEN;
import static domain.CardNumber.TEN;
import static domain.CardNumber.THREE;
import static domain.CardNumber.TWO;
import static domain.CardShape.CLOVER;
import static domain.CardShape.DIAMOND;
import static domain.CardShape.HEART;
import static domain.CardShape.SPADE;
import static domain.Result.*;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("참여자의 승패무를 판단한다.")
    void playerResult() {
        //given
        Hands sum18 = new Hands(List.of(new Card(EIGHT, CLOVER),
                new Card(TEN, DIAMOND))); // 18
        Hands sum21 = new Hands(
                List.of(new Card(JACK, HEART), new Card(ACE, SPADE)));

        Hands sum20 = new Hands(
                List.of(new Card(SEVEN, SPADE), new Card(TWO, SPADE),
                        new Card(ACE, SPADE)));

        Player loser = new Player("레디", sum18);
        Player winner = new Player("제제", sum21);
        Player tier = new Player("수달", sum20);

        Players players = new Players(List.of(loser, winner, tier));
        Dealer dealer = new Dealer(CardDeck.generate(), sum20);

        Game game = new Game(dealer, players);

        //when & then
        Map<Player, Result> expected = Map.of(loser, LOSE, winner, WIN, tier, TIE);
        Assertions.assertThat(game.getResult()).isEqualTo(expected);
    }

    @DisplayName("딜러의 승패무를 판단한다.")
    @Test
    void dealerResult() {
        // given
        Hands sum18 = new Hands(List.of(new Card(EIGHT, CLOVER),
                new Card(TEN, DIAMOND))); // 18

        Hands sum21 = new Hands(
                List.of(new Card(JACK, HEART), new Card(ACE, SPADE)));

        Hands sum20 = new Hands(
                List.of(new Card(SEVEN, SPADE), new Card(TWO, SPADE),
                        new Card(ACE, SPADE)));

        Player loser1 = new Player("레디", sum18);
        Player loser2 = new Player("피케이", sum18);
        Player winner = new Player("제제", sum21);
        Player tier = new Player("브라운", sum20);

        Players players = new Players(List.of(loser1, loser2, winner, tier));
        Dealer dealer = new Dealer(CardDeck.generate(), sum20);

        Game game = new Game(dealer, players);

        // when
        Map<Result, Integer> expected = Map.of(WIN, 2, LOSE, 1, TIE, 1);

        // then
        Assertions.assertThat(game.getDealerResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("딜러가 버스트일때 참여자가 버스트가 아니면 WIN")
    void all() {
        //given
        Hands dealerBustHands = new Hands(
                List.of(new Card(EIGHT, DIAMOND), new Card(TWO, DIAMOND), new Card(TWO, DIAMOND),
                        new Card(JACK, CLOVER)));
        Dealer bustDealer = new Dealer(CardDeck.generate(), dealerBustHands);

        Hands winner1PlayerHandsSum19 = new Hands(
                List.of(new Card(FIVE, DIAMOND), new Card(THREE, CLOVER), new Card(SEVEN, HEART),
                        new Card(FOUR, DIAMOND))
        );

        Player winner1 = new Player("레디", winner1PlayerHandsSum19);

        Hands winner2PlayerHandsSum20 = new Hands(
                List.of(new Card(QUEEN, CLOVER), new Card(KING, SPADE))
        );
        Player winner2 = new Player("브라운", winner2PlayerHandsSum20);

        Hands loserPlayerHandsSum22 = new Hands(
                List.of(new Card(SEVEN, CLOVER), new Card(QUEEN, HEART), new Card(FIVE, SPADE))
        );
        Player loser = new Player("제제", loserPlayerHandsSum22);

        Players players = new Players(List.of(winner1, winner2, loser));

        //when
        Game game = new Game(bustDealer, players);

        Map<Player, Result> expectedPlayerResult = Map.of(winner1, WIN, winner2, WIN, loser, LOSE);
        Map<Result, Integer> expectedDealerResult = Map.of(WIN, 1, LOSE, 2);

        //then
        Assertions.assertThat(game.getResult()).isEqualTo(expectedPlayerResult);
        Assertions.assertThat(game.getDealerResult()).isEqualTo(expectedDealerResult);
    }
}
