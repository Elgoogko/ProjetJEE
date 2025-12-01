package com.msclient.Controller.entities;

import com.actor.Actor;

public class Client implements Actor {
    private int idSession;
    private int id;
    private String pseudo;
    private Role role;

    public Client(int idSession, int id, String pseudo, Role role) {
        this.idSession = -1;
        this.id = id;
        this.pseudo = pseudo;
        this.role = Role.USER;
    }

    @Override
    public void sendWithActor(String s, Actor actor) {
        System.out.println("I DO NOTHIN");
    }

    @Override
    public void sendWithId(String s, int id) {
        System.out.println("I DO NOTHIN");
    }

    @Override
    public void receiveWithActor(String s, Actor actor) {
        System.out.println("I DO NOTHIN");
    }

    @Override
    public void receiveWithId(String s, int id) {
        System.out.println("I DO NOTHIN");
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean createNewSubActor(){
        return false;
    }

    public boolean deleteSubActor(int id){
        return false;
    }

    public boolean thisSubActorExist(int id){
        return false;
    }

}
