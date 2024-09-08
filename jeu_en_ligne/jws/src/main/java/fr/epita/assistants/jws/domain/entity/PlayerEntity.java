package fr.epita.assistants.jws.domain.entity;

import fr.epita.assistants.jws.data.model.GameModel;

import java.sql.Timestamp;

public class PlayerEntity {
    public long id;

    public Long game;

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public String name;
    public int lives;

    public int posX;
    public int posY;
    public Timestamp lastbomb;
    public Timestamp lastmovement;

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

    public PlayerEntity() {
        this.id = 1;
        this.lives = 3;
        this.name = "";
        this.posX = 0;
        this.posY = 0;
    }

    public PlayerEntity(long id, String name, int lives, int posX, int posY) {
        this.id = id;
        this.name = name;
        this.lives = lives;
        this.posX = posX;
        this.posY = posY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
