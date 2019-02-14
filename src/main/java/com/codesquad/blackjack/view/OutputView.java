package com.codesquad.blackjack.view;

import com.codesquad.blackjack.domain.card.Card;
import com.codesquad.blackjack.domain.user.User;
import com.codesquad.blackjack.dto.CardsDto;

public class OutputView {

    public static void printInitCards(Card dealerCard, CardsDto playerCards) {
        System.out.println("*** 딜러의 카드 : " + dealerCard);
        System.out.println("*** 플레이어의 카드 : " + playerCards);
        System.out.println("*** 플레이어의 합산결과 : " + playerCards.getTotal());
    }

    public static void printAllCardsOnTable(CardsDto dealerCards, CardsDto playerCards) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("*** 딜러의 카드 : " + dealerCards);
        System.out.println("*** 딜러의 합산결과 : " + dealerCards.getTotal());
        System.out.println("*** 플레이어의 카드 : " + playerCards);
        System.out.println("*** 플레이어의 합산결과 : " + playerCards.getTotal());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public static void printEndByBlackjack(User endByBlackjack) {
        if(endByBlackjack == null) {
            System.out.println("*** 무승부입니다(블랙잭)");
            return;
        }

        System.out.println("*** 블랙잭으로 " + endByBlackjack + "의 승리입니다.");
    }

    public static void printEnd(User end) {
        if(end == null) {
            System.out.println("*** 무승부입니다");
            return;
        }

        System.out.println("*** 합산결과 " + end + "의 승리입니다.");
    }

    public static void printEndByBurst() {
        System.out.println("*** 플레이어 버스트로 딜러의 승리입니다.");
    }

    public static void printEndByDealerBurst() {
        System.out.println("*** 딜러 버스트로 플레이어의 승리입니다.");
    }
}
