package fr.epita.assistants.jws.data.repository;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
public class PlayerRepository implements PanacheRepositoryBase<PlayerModel, Long> {

    public long id;
    public String name;
    public int lives;

    public int posX;
    public int posY;
    private Timestamp lastbomb;
    private Timestamp lastmovement;
    private GameModel game;
    private List<PlayerModel> playerModels;
    public PlayerModel playerModel;

    public void setGame(GameModel game) {
        this.game = game;
    }
/*
    public void PlayerModel() {
        this.id = 1;
        this.lives = 3;
        this.name = "";
        this.posX = 0;
        this.posY = 0;
    }*/

    public void PlayerModel(long id, Timestamp lastbomb, Timestamp lastmovement, int lives, String name, long posx, long posy, long position, List<PlayerModel> playerModels) {
        this.id = id;
        this.lastbomb = lastbomb;
        this.lastmovement = lastmovement;
        this.lives = lives;
        this.name = name;
        this.posX = posX;
        this.posY = posX;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getLastbomb() {
        return lastbomb;
    }

    public void setLastbomb(Timestamp lastbomb) {
        this.lastbomb = lastbomb;
    }

    public Timestamp getLastmovement() {
        return lastmovement;
    }

    public void setLastmovement(Timestamp lastmovement) {
        this.lastmovement = lastmovement;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public GameModel getGame() {
        return game;
    }

    public void setPlayerModels(List<PlayerModel> playerModels) {
        this.playerModels = playerModels;
    }

    public void PlayerModel() {
        this.id = 1;
        this.lives = 3;
        this.name = "";
        this.posX = 0;
        this.posY = 0;
    }

    public void PlayerModel(long id, String name, int lives, int posX, int posY) {
        this.id = id;
        this.name = name;
        this.lives = lives;
        this.posX = posX;
        this.posY = posY;
    }
     public static boolean exists(Long id) {
        PlayerModel playerModel = PlayerModel.findById(id);
        return playerModel != null;
    }

      public static PlayerModel findId(Long id) {
        return PlayerModel.findById(id);
    }

}