package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class OpenApiGameInfoTests extends OpenApiTestsBase {

    @Test
    void getGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public Game getGame(String gameId){
                Game game = new Game("Group",2,2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                game.newPlayer("Robin");
                return game;
            }
        });
        get(
                testContext,
                "/games/Group_2",
                "Group_2-Robin",
                this::assertOkResponse
        );
    }

    @Test
    void getGameUnauthorized(final VertxTestContext testContext) {
        get(
                testContext,
                "/games/game-id",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void getDummyGame(final VertxTestContext testContext) {
        get(
                testContext,
                "/games/dummy",
                null,
                response -> assertNotYetImplemented(response, "getDummyGame")
        );
    }
}
