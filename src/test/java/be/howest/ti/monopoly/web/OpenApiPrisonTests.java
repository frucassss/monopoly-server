package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiPrisonTests extends OpenApiTestsBase {

    @Test
    void getOutOfJailFine(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void payPrisonFine(String gameId, String playerName){}
        });
        post(
                testContext,
                "/games/group00/prison/Alice/fine",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getOutOfJailFineUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/prison/Alice/fine",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void getOutOfJailFree(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void useGetOutOfJailFreeCard(String gameId, String playerName){}
        });
        post(
                testContext,
                "/games/group00/prison/Alice/free",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getOutOfJailFreeUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/prison/Alice/free",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
