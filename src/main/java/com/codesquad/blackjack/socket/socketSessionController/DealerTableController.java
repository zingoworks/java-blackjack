package com.codesquad.blackjack.socket.socketSessionController;

import com.codesquad.blackjack.domain.Game;
import com.codesquad.blackjack.dto.ResultDto;
import com.codesquad.blackjack.service.MessageService;
import com.codesquad.blackjack.socket.GameSession;
import com.codesquad.blackjack.socket.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DealerTableController implements TableController {

    private final MessageService messageService;

    @Autowired
    public DealerTableController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handleTurn(GameSession gameSession, Game game) {
//        log.debug("DEALERTURN 실행");

        //룰에 따라 카드 뽑든 안뽑든해
        game.dealerTurn();


        //딜러 버스트나버렸어
        if(game.isBurst()) {
            game.endByPlayerWin(game.getNormalPrize());
            messageService.sendToAll(new ResultDto("BURST", "USER"), gameSession);
        } else {
            //노말하게 드로우가 끝났어
            messageService.sendToAll(new ResultDto("NORMAL", game.end(game.getNormalPrize())), gameSession);
        }

        //모든 카드 뿌려주고
        //종료결과, 승자, 잔여칩 보여줘
        //유저 저장해줘

        messageService.sendToAll(new SocketMessage<>("INIT", game._toGameDto()), gameSession);
//        userRepository.save(game.getUser());
        return;
    }
}
