package com.msclient;

import org.springframework.stereotype.Component;

import com.actors.ActorManager;
import com.actors.Status;
import com.msclient.Entity.User;
import com.msclient.Entity.UserAsActor;

@Component
public class UserManager extends ActorManager {

    public UserManager() {
        super(new ActorManager.Builder().actorIdentifier("UA").autoID(false).maximumNumberOfActors(1000));
    }

    public UserAsActor createActorForUser(User user) {
        UserAsActor actor = new UserAsActor();
        actor.setID(user.getId().toString()); // ID utilisateur comme identifiant
        actor.setStatus(Status.ALIVE);
        actor.setReference(this); // lie l'acteur au manager
        actor.setLifetime(3600000);
        this.addActor(actor);
        return actor;
    }

}
