package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiImprovingPropertyTests extends OpenApiTestsBase {

    @Test
    void buyHouse(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void buyHouse(String gameId, String playerName, String propertyName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/properties/some-property/houses",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void buyHouseUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/houses",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void sellHouse(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void sellHouse(String gameId, String playerName, String propertyName){}
        });
        delete(
                testContext,
                "/games/group00/players/Alice/properties/some-property/houses",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void sellHouseUnauthorized(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/houses",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    //
    @Test
    void buyHotel(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            public void buyHotel(String gameId, String playerName, String propertyName){}
        });
        post(
                testContext,
                "/games/group00/players/Alice/properties/some-property/hotel",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void buyHotelUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/hotel",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void sellHotel(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public void sellHotel(String gameId, String playerName, String propertyName){}
        });
        delete(
                testContext,
                "/games/group00/players/Alice/properties/some-property/hotel",
                "group00-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void sellHotelUnauthorized(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property/hotel",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
