package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.utils.GameStateUtils;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.listAll;

import java.util.ArrayList;
import java.util.List;

public class Game2Service {

    @Inject
    public GameRepository gameRepository;
    @Inject
    public PlayerRepository playerRepository;

    GameEntity gameEntity;
    public Game2Service(Long id) {
        //GameModel game = GameRepository.findId(id);
        if (GameRepository.findId(id) != null) {
            List<PlayerEntity> playerEntities = getPlayerEntityList(GameRepository.findId(id).players);
            //GameStateUtils.RUNNING
            GameStateUtils state1 = GameStateUtils.RUNNING;
            if (GameRepository.findId(id).state.equals("STARTING")) {
                state1 = GameStateUtils.STARTING;
            } else if (GameRepository.findId(id).state.equals("FINISHED")){
                state1 = GameStateUtils.FINISHED;
            }
            this.gameEntity = new GameEntity(GameRepository.findId(id).id, GameRepository.findId(id).starttime, playerEntities, state1, GameRepository.findId(id).map);
        }
        else {
            gameEntity = null;
        }
    }

    private List<PlayerEntity> getPlayerEntityList(List<PlayerModel> players) {
        List<PlayerEntity> playerEntities = new ArrayList<>();
        for (PlayerModel p : players) {
            PlayerEntity playerEntity = new PlayerEntity(p.id, p.name, p.lives, p.posx, p.posy);
            playerEntities.add(playerEntity);
        }
        return playerEntities;
    }
     public boolean exists(Long id) {
        return GameRepository.gameExists(id);
    }
}
