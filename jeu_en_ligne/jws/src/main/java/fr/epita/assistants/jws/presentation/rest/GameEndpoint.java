package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.converter.Converter;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.service.GameService;
import fr.epita.assistants.jws.domain.service.PlayerService;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.request.JoinGameRequest;

import fr.epita.assistants.jws.presentation.rest.request.MovePlayerRequest;
import fr.epita.assistants.jws.presentation.rest.request.PutBombRequest;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class GameEndpoint {


    @Inject
    GameService gameService;

    @GET
    @Path("/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {

        return Response.ok(Converter.ListResponseConvert(gameService.getAllGames())).status(200, "OK").build();
    }
    @GET
    @Path("/games/{gameid}")
    public Response getGameInfo(@PathParam("gameid") Long gameid) {
        try {
             return Response.ok(Converter.gameResponseConvert(gameService.getGame(gameid))).status(200, "Game info").build();
        } catch (Exception e) {
             return Response.status(404, "Cannot found game with this id").build();
         }
    }



    @POST
    @Path("/games")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
     public Response createGame(CreateGameRequest request) throws IOException {
        if (request == null || request.name == null) {
            return Response.status(400, "Bad request (request or name is null)").build();
        }
        return Response.ok(Converter.gameResponseConvert(gameService.CreateNewGame2(request.name))).status(200, "Game created").build();
    }

    @PATCH
    @Path("/games/{gameid}/start")
    public Response startGame(@PathParam("gameid") Long gameid) {
        try {
            return Response.ok(Converter.gameResponseConvert(gameService.startGame(gameid))).status(200, "Game started successfully").build();
        } catch (Exception e) {
            return Response.status(404, "The game with this ID does not exist").build();
        }
    }

    @POST
    @Path("/games/{gameId}")
    public Response joinGame(@PathParam("gameId") long gameId, JoinGameRequest request) {
        if (request == null || request.getName() == null)
            return Response.status(400,
                    "The request is null, or the player name is null or the game cannot be started (already started, too many players)").build();
        try {
            GameEntity response_join = gameService.joinGame(request.getName(), gameId);
            if (response_join.test2 == "TO MUCH JOIN"){
                return Response.status(400, "TO MUCH JOIN").build();
            }
            else if(response_join.test2 == "Already FINISHED"){
                return Response.status(400, "Already FINISHED").build();
            }
            else if(response_join.test2 == "Already RUNNING"){
                return Response.status(400, "Already RUNNING").build();
            }
            return Response.ok(Converter.joinGameResponseConvert(response_join)).status(200, "Game successfully joined").build();
        } catch (Exception e) {
            return Response.status(404, "Game with this ID does not exist").build();
        }
    }

    @POST
    @Path("/games/{gameid}/players/{playerid}/move")
    public Response move(@PathParam("gameid") Long gameid, @PathParam("playerid") Long playerid, MovePlayerRequest request) {
        fr.epita.assistants.jws.domain.service.Game2Service gameDetailResponse = new fr.epita.assistants.jws.domain.service.Game2Service(gameid);
        PlayerService playerDetail = new PlayerService(playerid);
        if (!(gameDetailResponse.exists(gameid) && playerDetail.exists(playerid))) {
            return Response.status(404, "The game with this ID, or the player does not exist").build();
        }
        if (request == null) {
            return Response.status(400, "The game is not running or the player is already dead. Or, the player cannot move to the specified position").build();
        }
        try {
            GameEntity response_move = gameService.move(gameid, playerid, request.posX, request.posY);
            if (response_move.test2 == "GAME NOT RUNNING"){
                return Response.status(400, "GAME NOT RUNNING").build();
            }
            else if (response_move.test2 == "The player go through walls"){
                return Response.status(400, "The player go through walls").build();
            }
            return Response.ok(Converter.gameResponseConvert(response_move)).status(200, "Player successfully moved").build();
        } catch (Exception e) {
            return Response.status(404, "The game with this ID, or the player does not exist").build();
        }
    }
    @POST
    @Path("/games/{gameId}/players/{playerId}/bomb")
    public Object bomb(@PathParam("gameId") Long gameId, @PathParam("playerId") Long playerId, PutBombRequest request) {
        fr.epita.assistants.jws.domain.service.Game2Service gameDetailResponse = new fr.epita.assistants.jws.domain.service.Game2Service(gameId);
        PlayerService playerDetail = new PlayerService(playerId);
        if (gameDetailResponse.exists(gameId) && playerDetail.exists(playerId)) {
            return Response.status(400, "The request is null, or the game is not started or the player is already dead, or the coords are wrong.").build();
        }
        else {
            return Response.status(404, " The game with this ID, or the player does not exist").build();
        }
    }
}
