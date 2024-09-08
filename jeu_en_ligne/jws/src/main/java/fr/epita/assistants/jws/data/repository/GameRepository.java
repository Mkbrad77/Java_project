package fr.epita.assistants.jws.data.repository;


import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.utils.GameStateUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.epita.assistants.jws.utils.GamePathsUtils.readMapFromPath;

@ApplicationScoped
public class GameRepository implements PanacheRepositoryBase<GameModel, Long> {
    public long id;
    public Timestamp startTime;
    public List<PlayerModel> player;
    public List<String> map;
    private GameStateUtils state;


    public void setPlayer(List<PlayerModel> player) {
        this.player = player;
    }

    public void GameModel(long id, String state, Timestamp startTime, List<PlayerModel> player, List<String> map) {
        this.id = id;
        this.state = GameStateUtils.valueOf(state);
        this.startTime = startTime;
        this.player = player;
        this.map = map;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(GameStateUtils state) {
        this.state = state;
    }

    public Timestamp getStarttime() {
        return startTime;
    }

    public void setStarttime(Timestamp starttime) {
        this.startTime = starttime;
    }

    public List<PlayerModel> getPlayer() {
        return player;
    }

    public List<String> getMap() {
        return map;
    }

    public void setMap(List<String> map) {
        this.map = map;
    }

    public GameModel gameModel;

     public static GameModel findId(Long id) {
        return GameModel.findById(id);
    }
    public static Long insertGame(String name) {
        GameModel gameModel = new GameModel();
        PlayerModel playerModel = new PlayerModel();
        playerModel.game = gameModel;
        playerModel.name = name;
        playerModel.lives = 3;
        playerModel.posx = 1;
        playerModel.posy = 1;
        gameModel.players = new ArrayList<>(List.of(playerModel));
        gameModel.map = new ArrayList<>(Arrays.asList("9M8M", "1M2G2W1M2G1W1G1W1M2W2G1M", "1M1G2M2W1G1M1G1M3W2M1G1M",
                "1M3W1M1W5M1W1M1G2W1M", "1M1W1M3W1M3W1M3W1M1G1M", "1M1G2W1M3W1M1W1G1W1M3W1M",
                "1M1G1M2G1M1W3M1G1M2W1M1W1M", "5M1G1W3M1G1W5M", "1M1W1M1G1W1M1W3M1W1M1G1W1M1W1M",
                "1M1G2W1M1G1W1G1M2W1G1M3G1M", "1M1G1M3W1M3W1M2W1G1M1G1M", "1M1G2W1M1W5M1G1M1W2G1M",
                "1M1G2M3W1M1G1M3W2M1G1M", "1M2G1W1G1M1G1W2G1W1M2W2G1M", "9M8M"));
        gameModel.starttime = new Timestamp(System.currentTimeMillis());
        gameModel.state = "STARTING";
        GameModel.persist(gameModel);
        //gameModel.players.add(playerModel);
        return gameModel.id;
    }

    public static boolean gameExists(Long id) {
        GameModel gameModel1 = GameModel.findById(id);
        if (gameModel1 != null) {
            return true;
        }
        else {
            return false;
        }
    }
}

