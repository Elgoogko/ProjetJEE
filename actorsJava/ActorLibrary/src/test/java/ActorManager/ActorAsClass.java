package ActorManager;

import org.json.JSONObject;

import com.actors.Actor;
public class ActorAsClass extends Actor {
    @Override
    public void receive(JSONObject message, String senderID) {
        throw new UnsupportedOperationException("Unimplemented method 'receive'");
    }

    @Override
    public JSONObject castToJSONObject() {
        throw new UnsupportedOperationException("Unimplemented method 'castToJSONObject'");
    }
}
