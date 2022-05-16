package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiBuyingPropertyTests extends OpenApiTestsBase {

    @Test
    void buyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void buyProperty(String gameId, String playerName, String propertyName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/properties/some-property",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void buyPropertyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void buyProperty(String gameId, String playerName, String propertyName){}
        });

        post(
                testContext,
                "/games/group00/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void dontBuyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void dontBuyProperty(String gameId, String playerName, String propertyName){}
        });
        delete(
                testContext,
                "/games/group00/players/Alice/properties/some-property",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void dontBuyPropertyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void dontBuyProperty(String gameId, String playerName, String propertyName){}
        });

        delete(
                testContext,
                "/games/group00/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
