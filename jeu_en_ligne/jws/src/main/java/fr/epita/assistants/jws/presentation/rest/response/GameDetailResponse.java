package fr.epita.assistants.jws.presentation.rest.response;

import java.sql.Timestamp;

import fr.epita.assistants.jws.converter.players;
import fr.epita.assistants.jws.utils.GameStateUtils;

import java.util.ArrayList;
import java.util.List;

public class GameDetailResponse {
//public class CreateGameResponse {
    public String startTime;
    public GameStateUtils state;
    public List<players> players;
    public List<String> map;
    public long id;
    public /*CreateGameResponse()*/GameDetailResponse(){
        this.startTime = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.state = GameStateUtils.FINISHED;
        this.players = new ArrayList<>();
        this.map = new ArrayList<>();
        this.id = 1;
    }
    public /*CreateGameResponse()*/GameDetailResponse(GameDetailResponse response){
        this.startTime = response.startTime;
        this.state = response.state;
        this.players = response.players;
        this.map = response.map;
        this.id = response.id;
    }
    /*

    public CreateGameResponse(PlayerEntity player, String player1_name) {
        this.player.name = player1_name;
        this.player.posX = 1;
        this.player.posY = 1;
        this.id = 1;
    }*/
}
