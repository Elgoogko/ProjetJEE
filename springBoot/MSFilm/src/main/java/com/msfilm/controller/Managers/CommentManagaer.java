package com.msfilm.controller.Managers;

import org.springframework.stereotype.Component;

import com.actors.ActorManager;

@Component
public class CommentManagaer extends ActorManager {
    public CommentManagaer(){
        super(new ActorManager.Builder().actorIdentifier("C").autoID(true).checkInterval(10000));
    }
}
