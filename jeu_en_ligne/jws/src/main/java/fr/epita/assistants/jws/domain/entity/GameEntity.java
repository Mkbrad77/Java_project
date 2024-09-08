package fr.epita.assistants.jws.domain.entity;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.utils.GameStateUtils;

import java.sql.Timestamp;
import java.util.List;

public class GameEntity {
    public String test2;
    public long id;
    public int players;
    public Timestamp startTime;
    public List<String> map;
    public GameStateUtils state;


    public List<PlayerEntity> getPlayerEntities() {
        return playerEntities;
    }

    public List<PlayerEntity> playerEntities;

    public List<String> getMap() {
        return map;
    }

    public void setMap(List<String> map) {
        this.map = map;
    }
    public GameEntity(){
    }

    public GameEntity(long id, Timestamp startTime, List<PlayerEntity> playerEntities,GameStateUtils state, List<String> map, int players) {
        this.id = id;
        this.startTime = startTime;
        this.state = state;
        this.map =map;
        this.playerEntities = playerEntities;
        this.players = players;
    }

     public GameEntity(long id, Timestamp startTime, List<PlayerEntity> playerEntities,GameStateUtils state, List<String> map) {
        this.id = id;
        this.startTime = startTime;
        this.state = state;
        this.map =map;
        this.playerEntities = playerEntities;
        this.players = players;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getStarttime() {
        return startTime;
    }

    public void setStarttime(Timestamp starttime) {
        this.startTime = starttime;
    }

    public GameStateUtils getState() {
        return state;
    }

    public void setState(GameStateUtils state) {
        this.state = state;
    }

}