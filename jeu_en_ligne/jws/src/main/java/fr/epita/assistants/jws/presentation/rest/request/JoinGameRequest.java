package fr.epita.assistants.jws.presentation.rest.request;

public class JoinGameRequest {
    public String name;
    public JoinGameRequest(){
        this.name = "";
    }

    public JoinGameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}