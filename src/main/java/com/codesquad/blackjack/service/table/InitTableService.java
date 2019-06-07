package com.codesquad.blackjack.service.table;

import com.codesquad.blackjack.domain.Game;
import com.codesquad.blackjack.dto.GameDto;
import com.codesquad.blackjack.service.MessageService;
import com.codesquad.blackjack.socket.GameSession;
import com.codesquad.blackjack.socket.SocketRequest;
import com.codesquad.blackjack.socket.SocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.codesquad.blackjack.domain.Game.GameStatus.BLACKJACK;
import static com.codesquad.blackjack.domain.ResponseType.*;

@Component
public class InitTableService implements TableService {

    public static final int DOUBLE = 1;
    public static final int WITHOUT_DOUBLE = 2;

    private final MessageService messageService;

    @Autowired
    public InitTableService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handleRequest(GameSession gameSession, Game game, SocketRequest request) {
        int bettingChip = (int) request.getRequest();
        game.initializeGame();
        game.init(bettingChip);

        if (game.isBlackjack()) {
            GameDto gameDto = game._toGameDto(BLACKJACK, game.finishGame(BLACKJACK));
            messageService.sendToAll(new SocketResponse<>(INFO, gameDto), gameSession);
//            userRepository.save(game.getUser());
            return;
        }

        messageService.sendToAll(new SocketResponse<>(INFO, game._toGameDto()), gameSession);

        SocketResponse selection = (game.hasGamerEnoughChip(100))
                        ? new SocketResponse<>(SELECTION, DOUBLE)
                        : new SocketResponse<>(SELECTION, WITHOUT_DOUBLE);

        messageService.sendToAll(selection, gameSession);
    }

}
