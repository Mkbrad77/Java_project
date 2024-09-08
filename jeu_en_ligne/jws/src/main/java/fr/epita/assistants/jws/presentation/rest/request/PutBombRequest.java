package fr.epita.assistants.jws.presentation.rest.request;

public class PutBombRequest {
    public int posX;
    public int posY;

    public PutBombRequest(){
        this.posX = 0;
        this.posY = 0;
    }

    public PutBombRequest(int posX, int posY) {
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