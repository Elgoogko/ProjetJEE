package com.msclient.entities;

import java.io.*;
import java.util.ArrayList;

import com.actor.Actor;

public class MainClient implements Actor {

    private ArrayList<Client> listClient = new ArrayList<Client>();
    private static int id=1;

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
