package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiMortgageTests extends OpenApiTestsBase {

    @Test
    void takeMortgage(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void takeMortgage(String gameId, String playerName, String propertyName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/properties/some-property/mortgage",
                "group00-Alice",
                response -> assertOkResponse(response)
        );

    }

    @Test
    void takeMortgageUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/mortgage",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void settleMortgage(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/mortgage",
                "some-token",
                response -> assertNotYetImplemented(response, "settleMortgage")
        );
    }

    @Test
    void settleMortgageUnauthorized(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/mortgage",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

}
