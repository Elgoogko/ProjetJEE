package com.msfilm.controller.Managers;

import org.springframework.stereotype.Component;

import com.actors.ActorManager;

@Component
public class CommentManager extends ActorManager {
    public CommentManager() {
        super(new ActorManager.Builder().actorIdentifier("C").autoID(true).checkInterval(10000));
    }
}
