package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiTurnManagementTests extends OpenApiTestsBase {

    @Test
    void rollDice(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void rollDice(String gameId, String playerName){}
        });

        post(
                testContext,
                "/games/group00/players/Alice/dice",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void rollDiceUnauthorized(final VertxTestContext testContext) {

        service.setDelegate(new ServiceAdapter(){
            @Override
            public void rollDice(String gameId, String playerName){}
        });

        post(
                testContext,
                "/games/group00/players/Alice/dice",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void declareBankruptcy(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void declareBankruptcy(String gameId, String playerName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/bankruptcy",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void declareBankruptcyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void declareBankruptcy(String gameId, String playerName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/bankruptcy",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
