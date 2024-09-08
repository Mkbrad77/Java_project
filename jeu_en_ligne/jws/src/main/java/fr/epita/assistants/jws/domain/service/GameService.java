package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.converter.Converter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.utils.GameStateUtils;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.listAll;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    public GameRepository gameRepository;
    @Inject
    public PlayerRepository playerRepository;



@Transactional
    public List<GameEntity> getAllGames() {
        List<GameEntity> listGames = new ArrayList<>();
        for (GameModel gameModel : gameRepository.listAll()) {
            List<PlayerEntity> listPlayerEntity = new ArrayList<>();
            for (PlayerModel playerModel : playerRepository.list("game_id", gameModel.id)) {
                listPlayerEntity.add(new PlayerEntity(playerModel.id, playerModel.name, playerModel.lives, playerModel.posx, playerModel.posy));
            }
            GameStateUtils state = null;
            if (gameModel.state.equals("STARTING")) {
                state = GameStateUtils.STARTING;

            } else if (gameModel.state.equals("FINISHED")) {
                state = GameStateUtils.FINISHED;
            } else if (gameModel.state.equals("RUNNING")) {
                state = GameStateUtils.RUNNING;
            }
            listGames.add(new GameEntity(gameModel.id, gameModel.starttime, listPlayerEntity, state, gameModel.map, listPlayerEntity.size()));

        }
        return listGames;
    }

    @Transactional
    public GameEntity getGame(Long id) {
        GameModel gameModel = gameRepository.findById((long) id);
        if (gameModel == null) {
            return null;
        }
        List<PlayerEntity> playerEntities = new ArrayList<>();
        for (PlayerModel player : playerRepository.list("game_id", gameModel.id)) {
            playerEntities.add(new PlayerEntity(player.id, player.name, player.lives, player.posx, player.posy));
        }
        GameStateUtils states = GameStateUtils.RUNNING;
        if ( gameModel.state.equals("STARTING")) {
            states = GameStateUtils.STARTING;
        } else if (gameModel.state.equals("FINISHED"))
            states = GameStateUtils.FINISHED;
        return new GameEntity(gameModel.id, gameModel.starttime, playerEntities, states, new ArrayList<>(gameModel.map));
    }
   @Transactional
    public Long newPlayer(PlayerEntity playerEntity) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.name = playerEntity.name;
        playerModel.game = gameRepository.findById(Long.valueOf(playerEntity.game));
        playerModel.lives = playerEntity.lives;
        playerModel.posx = playerEntity.posX;
        playerModel.posy = playerEntity.posY;
        playerRepository.persist(playerModel);
        playerRepository.flush();
        playerModel.persist(playerModel);
        return playerModel.id;
    }

   @Transactional
    public GameEntity joinGame(String playerName, Long gameId) throws Exception {
        GameEntity game = getGame(gameId);
        GameEntity faild = new GameEntity();
        if (game == null) {
            throw new Exception();
        }
        boolean alreadyJoined = false;
        for (PlayerEntity playerEntity : game.playerEntities) {
            if (playerEntity.name.equals(playerName)) {
                alreadyJoined = true;
                break;
            }
        }
        if (alreadyJoined) {
            return null;
        }
        if (game.state == GameStateUtils.FINISHED) {
            faild.test2 = "Already FINISHED";
            return faild;
        }
        if (game.state == GameStateUtils.RUNNING) {
            faild.test2 = "Already RUNNING";
            return faild;
        }

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.name = playerName;
        playerEntity.lives = 3;
        if (game.playerEntities.size() == 1) {
            playerEntity.posX = 15;
            playerEntity.posY = 1; }
        else if (game.playerEntities.size() == 2) {
            playerEntity.posX = 15;
            playerEntity.posY = 13;
        } else if (game.playerEntities.size() == 3) {
            playerEntity.posX = 1;
            playerEntity.posY = 13;
        } else {
            faild.test2 = "TO MUCH JOIN";
            return faild;
        }
        playerEntity.game = gameId;
        playerEntity.id = newPlayer(playerEntity);
        game.playerEntities.add(playerEntity);
        GameModel gameModel = gameRepository.findById((gameId));
        GameModel.persist(gameModel);
        return getGame(gameId);
    }

    @Transactional
    public GameEntity startGame(Long gameId) {
        GameModel gameModel = gameRepository.findById(gameId);
        if (gameModel == null) {
            return null;
        }
        GameEntity game = getGame(gameId);
        if (game.state == GameStateUtils.FINISHED) {
            return null;
        }
        if (game.playerEntities.size() == 1) {
            gameModel.state = "FINISHED";
        } else {
            gameModel.state = "RUNNING";
        }
        gameRepository.persist(gameModel);
        gameRepository.flush();
        return getGame(gameId);
    }
    @Transactional
    public GameEntity move(Long gameid, Long playerid, int posX, int posY) {
        GameModel gameModel = gameRepository.findById(gameid);
        PlayerModel playerModel = playerRepository.findById(playerid);
        GameEntity faild = new GameEntity();
        if (gameModel == null || playerModel == null) {
            throw new RuntimeException("The game or the player does not exist");
        }
        if (gameModel.state != "RUNNING" || playerModel.lives <= 0 || gameModel.state == "FINISHED") {
            faild.test2 = "GAME NOT RUNNING";
            return faild;
        }

        if (Math.abs(playerModel.posx - posX) + Math.abs(playerModel.posy - posY) != 1){
            throw new RuntimeException("The player don't move in a cardinal direction");
        }

        if (!isMoveValid(gameModel, posX, posY)){
            faild.test2 = "The player go through walls";
            return faild;
        }
        playerModel.posx = posX;
        playerModel.posy = posY;
        gameRepository.persist(gameModel);
        playerRepository.persist(playerModel);
        gameRepository.flush();
        playerRepository.flush();
        return getGame(gameid);
    }
@Transactional
    private boolean isMoveValid(GameModel gameModel, int posX, int posY) {
        GameEntity gameEntity1 = getGame(gameModel.id);
        if (posX < 0 || posY < 0 || posX >= gameEntity1.getMap().size() || posY >= gameEntity1.getMap().get(0).length()){
            return false;
        }
        char block = gameEntity1.getMap().get(posX).charAt(posY);

        if (block == 'M' || block == 'W' || block == 'B'){
            return false;
        }
        return true;
    }
@Transactional
    public GameEntity CreateNewGame2(String name) {
        long game_id = GameRepository.insertGame(name);
        return getGame(game_id);
    }

}

