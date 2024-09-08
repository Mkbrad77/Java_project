package fr.epita.assistants.jws.errors;

public class BadRequestErrors extends RuntimeException{
    public BadRequestErrors(String message){
        super(message);
    }
}