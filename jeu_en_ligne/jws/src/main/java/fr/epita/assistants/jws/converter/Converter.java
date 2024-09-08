package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import fr.epita.assistants.jws.utils.GameStateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class Converter {
    public static List<GameListResponse> ListResponseConvert(List<GameEntity> games) {
        List<GameListResponse> gameListResponses = new ArrayList<>();
        for (GameEntity game : games) {
            GameListResponse response = new GameListResponse();
            response.setId(game.id);
            response.setPlayers(game.playerEntities.size());
            response.setState(game.state);
            gameListResponses.add(response);
        }
        return gameListResponses;
    }

    public static GameDetailResponse gameResponseConvert(GameEntity game) {
        GameDetailResponse response = new GameDetailResponse();
        response.id = game.id;
        response.state = game.state;
        response.startTime = game.startTime.toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME);
        response.players = new ArrayList<>();
        for (PlayerEntity player : game.playerEntities) {
            players playerResponse = new players();// changement ici
            playerResponse.id = player.id;
            playerResponse.name = player.name;
            playerResponse.posX = player.posX;
            playerResponse.posY = player.posY;
            playerResponse.lives = player.lives;
            response.players.add(playerResponse); // et ici
        }
        response.map = new ArrayList<>(game.map);
        return response;
    }

    public static GameDetailResponse joinGameResponseConvert(GameEntity game) {
            List<PlayerEntity> playerResponses = game.getPlayerEntities().stream()
                    .map(player -> new PlayerEntity(player.getId(), player.getName(), player.getLives(), player.posX, player.posY))
                    .collect(Collectors.toList());
            GameDetailResponse response = new GameDetailResponse();
            response.id = game.id;
            response.map = game.map;
            response.state = GameStateUtils.STARTING;
            List<players> playerConv_resp = new ArrayList<>();
            for (PlayerEntity player : playerResponses){
                players playerC = new players(player);
                playerConv_resp.add(playerC);
            }
            response.players = playerConv_resp;
            response.startTime = String.valueOf(game.startTime);
            return response;
    }


}
