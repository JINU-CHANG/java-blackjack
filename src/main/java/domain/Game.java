package domain;

import domain.participant.Dealer;
import domain.participant.Player;
import domain.participant.Players;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Game {

    private final Dealer dealer;
    private final Players players;

    public Game(final Dealer dealer, final Players players) {
        this.dealer = dealer;
        this.players = players;
    }

    public Map<Player, Result> getPlayersResult() {
        final Map<Player, Result> playerResult = new LinkedHashMap<>();

        for (Player player : players.getPlayers()) {
            playerResult.put(player, player.calculateResult(dealer));
        }
        return playerResult;
    }

    public Map<Result, Integer> getDealerResult() {
        Map<Result, Integer> dealerResult = new EnumMap<>(Result.class);
        for (Result value : getPlayersResult().values()) {
            Result reversed = value.reverse();
            dealerResult.put(reversed, dealerResult.getOrDefault(reversed, 0) + 1);
        }
        return dealerResult;
    }
}
