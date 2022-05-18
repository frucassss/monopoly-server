package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Move;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class CommunityChest extends Card{
    private static final int HOUSE_REPAIR_COST = 40;
    private static final int HOTEL_REPAIR_COST = 115;

    public CommunityChest(String communitypostDescription,Player player, Game game, Move move) {
        super(player, game, move);
        processDifferentCommunityCards(communitypostDescription);
    }

    private void processDifferentCommunityCards(String description){
        switch (description){
            case "Advance to Go (Collect $200)":
                advanceTo("Go");
                break;
            case "Bank error in your favor. Collect $200":
                player.collect(200);
                break;
            case "Doctor's fee. Pay $50":
                player.pay(50);
                break;
            case "From sale of stock you get $50":
                player.collect(50);
                break;
            case "Get Out of Jail Free":
                player.addGetOutOfJailFreeCard();
                break;
            case "Go to Jail. Go directly to jail, do not pass Go, do not collect $200":
                goToJail();
                break;
            case "Holiday fund matures. Receive $100":
                player.collect(100);
                break;
            case "Income tax refund. Collect $20":
                player.collect(20);
                break;
            case "It is your birthday. Collect $10 from every player":
                collectMoneyFromEachPlayers();
                break;
            case "Life insurance matures. Collect $100":
                player.collect(100);
                break;
            case "Pay hospital fees of $100":
                player.pay(100);
                break;
            case "Pay school fees of $50":
                player.pay(50);
                break;
            case "Receive $25 consultancy fee":
                player.collect(25);
                break;
            case "ou are assessed for street repair. $40 per house. $115 per hotel":
                generalRepair(HOUSE_REPAIR_COST,HOTEL_REPAIR_COST);
                break;
            case "You have won second prize in a beauty contest. Collect $10":
                player.collect(10);
                break;
            case "You inherit $100":
                player.collect(100);
                break;
            default:
                break;
        }
    }

    private void collectMoneyFromEachPlayers() {
        int totalAmountToCollect = 10 * (game.getNumberOfPlayers()-1);
        player.collect(totalAmountToCollect);
        for (Player gamePlayer : game.getPlayers()){
            if (!gamePlayer.equals(player)){
                gamePlayer.pay(10);
            }
        }
    }
}
