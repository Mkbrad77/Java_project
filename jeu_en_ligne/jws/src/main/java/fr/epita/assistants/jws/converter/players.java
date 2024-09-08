package fr.epita.assistants.jws.converter;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;


public class players {

     public long id;
     public String name;
     public int lives;

    public int posX;
    public int posY;

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

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public players(PlayerEntity player){
        this.id = player.id;
        this.name = player.name;
        this.lives = player.lives;
        this.posX = player.posX;
        this.posY = player.posY;
    }
      public players(){
        this.id = 1;
        this.name = "";
        this.lives = 0;
        this.posX = 0;
        this.posY = 0;
    }

}
