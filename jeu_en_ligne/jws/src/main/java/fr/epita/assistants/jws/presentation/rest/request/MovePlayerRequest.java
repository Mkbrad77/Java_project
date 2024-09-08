package fr.epita.assistants.jws.presentation.rest.request;

public class MovePlayerRequest {
    public int posX;
    public int posY;

    public MovePlayerRequest(){
        this.posX = 0;
        this.posY = 0;

    }

    public MovePlayerRequest(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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
}