package blackjack.controller;

import blackjack.domain.CardDeckGenerator;
import blackjack.domain.GameMachine;
import blackjack.domain.GameResult;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.User;
import blackjack.domain.player.Users;
import blackjack.view.InputView;
import blackjack.view.ResultView;

public class GameController {

    public void run() {
        GameMachine gameMachine = new GameMachine(CardDeckGenerator.createCardDeckByCardNumber());

        Dealer dealer = gameMachine.createDealer();
        Users users = gameMachine.createUsers(InputView.inputUsers());
        ResultView.printPlayersCards(dealer, users);

        playTurn(gameMachine, dealer, users);
        ResultView.printTotalCardResult(dealer, users);

        GameResult gameResult = GameResult.createPlayerGameResult(dealer, users);
        ResultView.printGameResult(gameResult);
    }

    private void playTurn(GameMachine gameMachine, Dealer dealer, Users users) {
        if (!gameMachine.hasBlackJack(dealer, users)) {
            turnOfUsers(gameMachine, users);
            turnOfDealer(gameMachine, dealer);
        }
    }

    private void turnOfUsers(GameMachine gameMachine, Users users) {
        for (User user : users.getUsers()) {
            processForUser(gameMachine, user);
        }
    }

    private void processForUser(final GameMachine gameMachine, final User user) {
        while (user.canDrawCard() && InputView.inputDrawCardAnswer(user)) {
            gameMachine.drawCardToPlayer(user);
            ResultView.printPlayerCards(user);
        }
    }

    private void turnOfDealer(GameMachine gameMachine, Dealer dealer) {
        if (gameMachine.checkPlayerReceiveCard(dealer)) {
            ResultView.printDealerReceiveCard();
        }
    }
}