package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.utils.GameStateUtils;

public class GameListResponse {
    public long id;

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setState(GameStateUtils state) {
        this.state = state;
    }

    public int players;
    public GameStateUtils state;

    public void setId(long id) {
        this.id = id;
    }

    public GameListResponse() {
        this.id = 0;
        this.players = 0;
        this.state = GameStateUtils.FINISHED;
    }

    public GameListResponse(long id, int players, GameStateUtils state) {
        this.id = id;
        this.players = players;
        this.state = state;
    }

}
