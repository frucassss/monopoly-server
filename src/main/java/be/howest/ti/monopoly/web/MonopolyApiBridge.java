package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.IService;
import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.exceptions.InsufficientFundsException;
import be.howest.ti.monopoly.logic.exceptions.MonopolyResourceNotFoundException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;
import be.howest.ti.monopoly.web.exceptions.InvalidRequestException;
import be.howest.ti.monopoly.web.exceptions.NotYetImplementedException;
import be.howest.ti.monopoly.web.tokens.MonopolyUser;
import be.howest.ti.monopoly.web.tokens.PlainTextTokens;
import be.howest.ti.monopoly.web.tokens.TokenManager;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonopolyApiBridge {
    private static final String FORBIDDEN_ACCES_MESSAGE = "This is a protected endpoint. Make sure the security-token you passed along is valid token for this game.";
    private static final Logger LOGGER = Logger.getLogger(MonopolyApiBridge.class.getName());

    private final IService service;
    private final TokenManager tokenManager;

    public MonopolyApiBridge(IService service, TokenManager tokenManager) {
        this.service = service;
        this.tokenManager = tokenManager;
    }

    public MonopolyApiBridge() {
        this(
                new MonopolyService(),
                new PlainTextTokens()
        );
    }


    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("playerAuth", BearerAuthHandler.create(tokenManager));


        LOGGER.log(Level.INFO, "Installing Failure handlers");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing Actual handlers");

        // General Game and API Info
        routerBuilder.operation("getInfo").handler(this::getInfo);
        routerBuilder.operation("getTiles").handler(this::getTiles);
        routerBuilder.operation("getTile").handler(this::getTile);
        routerBuilder.operation("getChance").handler(this::getChance);
        routerBuilder.operation("getCommunityChest").handler(this::getCommunityChest);

        // Managing Games
        routerBuilder.operation("getGames").handler(this::getGames);
        routerBuilder.operation("createGame").handler(this::createGame);
        routerBuilder.operation("joinGame").handler(this::joinGame);
        routerBuilder.operation("clearGameList").handler(this::clearGameList);

        // Game Info
        routerBuilder.operation("getGame").handler(this::getGame);
        routerBuilder.operation("getDummyGame").handler(this::getDummyGame);

        // Turn Management
        routerBuilder.operation("rollDice").handler(this::rollDice);
        routerBuilder.operation("declareBankruptcy").handler(this::declareBankruptcy);

        // Tax Management
        routerBuilder.operation("useEstimateTax").handler(this::useEstimateTax);
        routerBuilder.operation("useComputeTax").handler(this::useComputeTax);

        // Buying property
        routerBuilder.operation("buyProperty").handler(this::buyProperty);
        routerBuilder.operation("dontBuyProperty").handler(this::dontBuyProperty);

        // Improving property
        routerBuilder.operation("buyHouse").handler(this::buyHouse);
        routerBuilder.operation("sellHouse").handler(this::sellHouse);
        routerBuilder.operation("buyHotel").handler(this::buyHotel);
        routerBuilder.operation("sellHotel").handler(this::sellHotel);

        // Mortgage
        routerBuilder.operation("takeMortgage").handler(this::takeMortgage);
        routerBuilder.operation("settleMortgage").handler(this::settleMortgage);

        // Interaction with other player
        routerBuilder.operation("collectDebt").handler(this::collectDebt);
        routerBuilder.operation("trade").handler(this::trade);

        // Prison
        routerBuilder.operation("getOutOfJailFine").handler(this::getOutOfJailFine);
        routerBuilder.operation("getOutOfJailFree").handler(this::getOutOfJailFree);

        // Auctions
        routerBuilder.operation("getBankAuctions").handler(this::getBankAuctions);
        routerBuilder.operation("placeBidOnBankAuction").handler(this::placeBidOnBankAuction);
        routerBuilder.operation("getPlayerAuctions").handler(this::getPlayerAuctions);
        routerBuilder.operation("startPlayerAuction").handler(this::startPlayerAuction);
        routerBuilder.operation("placeBidOnPlayerAuction").handler(this::placeBidOnPlayerAuction);


        LOGGER.log(Level.INFO, "All handlers are installed");
        return routerBuilder.createRouter();
    }

    private void getInfo(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, new JsonObject()
                .put("name", "monopoly")
                .put("version", service.getVersion())
        );
    }

    private void getTiles(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getTiles());
    }

    private void getTile(RoutingContext ctx) {
        Request request = Request.from(ctx);
        Tile tile;
        if (request.hasTilePosition()) {
            int position = request.getTilePosition();
            tile = service.getTile(position);
        } else {
            String name = request.getTileName();
            tile = service.getTile(name);
        }
        Response.sendJsonResponse(ctx, 200, tile);
    }

    private void getChance(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getChance());
    }

    private void getCommunityChest(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getCommunityChest());
    }

    private void clearGameList(RoutingContext ctx) {
        throw new NotYetImplementedException("clearGameList");
    }

    private void createGame(RoutingContext ctx) {
        Request request = Request.from(ctx);
        int numberOfPlayers = request.getNumberOfPlayersFromBody();
        String prefix = request.getPrefixFromBody();

        Game game = service.createGame(prefix, numberOfPlayers);
        Response.sendJsonResponse(ctx, 200, retrieveBasicGameInfo(game));
    }

    private void getGames(RoutingContext ctx) {
        Request request = Request.from(ctx);
        RequestParameter numberOfPlayers = request.getQueryParameter("numberOfPlayers");
        RequestParameter started = request.getQueryParameter("started");
        RequestParameter prefix = request.getQueryParameter("prefix");

        Map<String, Game> games = service.getGamesFromService();
        List<JsonObject> list = new ArrayList<>();

        for (Map.Entry<String, Game> entry : games.entrySet()) {
            Game game = entry.getValue();
            if (isGameValidAccordingToQueries(game, started, numberOfPlayers, prefix)) {
                list.add(retrieveBasicGameInfo(game));
            }
        }

        Response.sendJsonResponse(ctx, 200, new JsonObject()
                .put("games", list)
        );

    }

    private void joinGame(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromBody();

        service.joinGame(gameId, playerName);

        String playerToken = tokenManager.createToken(
                new MonopolyUser(gameId, playerName)
        );
        Response.sendJsonResponse(ctx, 200, new JsonObject()
                .put("token", playerToken)
        );
    }

    private void getGame(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();

        // check if player who requested the game is authorized in game
        if (!isPlayerAuthorizedInGame(request, gameId)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        Game game = service.getGame(gameId);

        Response.sendJsonResponse(ctx, 200, game);
    }

    private void getDummyGame(RoutingContext ctx) {
        throw new NotYetImplementedException("getDummyGame");
    }

    private void useEstimateTax(RoutingContext ctx) {
        throw new NotYetImplementedException("useEstimateTax");
    }

    private void useComputeTax(RoutingContext ctx) {
        throw new NotYetImplementedException("useComputeTax");
    }

    private void rollDice(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.rollDice(gameId, playerName);

        Response.sendOkResponse(ctx);
    }

    private void declareBankruptcy(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.declareBankruptcy(gameId, playerName);

        Response.sendOkResponse(ctx);


    }

    private void buyProperty(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.buyProperty(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void dontBuyProperty(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.dontBuyProperty(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void collectDebt(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();
        String debtorName = request.getDebtorNameFromPath();


        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.collectDebt(gameId, playerName, propertyName, debtorName);
        Response.sendOkResponse(ctx);

    }

    private void takeMortgage(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.takeMortgage(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void settleMortgage(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.settleMortgage(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void buyHouse(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.buyHouse(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void sellHouse(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.sellHouse(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void buyHotel(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.buyHotel(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void sellHotel(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.sellHotel(gameId, playerName, propertyName);
        Response.sendOkResponse(ctx);
    }

    private void getOutOfJailFine(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.payPrisonFine(gameId, playerName);
        Response.sendOkResponse(ctx);
    }

    private void getOutOfJailFree(RoutingContext ctx) {
        Request request = Request.from(ctx);
        String gameId = request.getGameIdFromPath();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
        }

        service.useGetOutOfJailFreeCard(gameId, playerName);
        Response.sendOkResponse(ctx);
    }

    private void getBankAuctions(RoutingContext ctx) {
        throw new NotYetImplementedException("getBankAuctions");
    }

    private void placeBidOnBankAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("placeBidOnBankAuction");
    }

    private void getPlayerAuctions(RoutingContext ctx) {
        throw new NotYetImplementedException("getPlayerAuctions");
    }

    private void startPlayerAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("startPlayerAuction");
    }

    private void placeBidOnPlayerAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("placeBidOnPlayerAuction");
    }

    private void trade(RoutingContext ctx) {
        throw new NotYetImplementedException("trade");
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();
        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Failed request", cause);
        if (cause instanceof InvalidRequestException) {
            code = 400;
        } else if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof InsufficientFundsException) {
            code = 402;
        } else if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof MonopolyResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof IllegalMonopolyActionException) {
            code = 409;
        } else if (cause instanceof NotYetImplementedException) {
            code = 501;
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.")
                .allowedHeader("x-requested-with")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("origin")
                .allowedHeader("Content-Type")
                .allowedHeader("accept")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.PATCH)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT);
    }

    private boolean isGameValidAccordingToQueries(Game game, RequestParameter started, RequestParameter numberOfPlayers, RequestParameter prefix) {
        return (started == null || game.getStarted() == started.getBoolean()) &&
                (numberOfPlayers == null || game.getNumberOfPlayers() == numberOfPlayers.getInteger()) &&
                (prefix == null || game.getPrefix().equals(prefix.getString()));
    }

    private boolean isPlayerAuthorizedInGame(Request request, String gameId) {
        Game game = service.getGame(gameId);
        for (Player player : game.getPlayers()) {
            if (request.isAuthorized(gameId, player.getName())) {
                return true;
            }
        }
        return false;
    }

    private JsonObject retrieveBasicGameInfo(Game game) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("id", game.getId());
        jsonObject.put("numberOfPlayers", game.getNumberOfPlayers());
        jsonObject.put("started", game.getStarted());
        jsonObject.put("players", retrievePlayerNames(game));
        return jsonObject;
    }

    private List<String> retrievePlayerNames(Game game) {
        List<String> names = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            names.add(player.getName());
        }
        return names;
    }
}
