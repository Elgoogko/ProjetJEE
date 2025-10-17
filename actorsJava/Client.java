public class Client implements Actor {
    
    private String name;

    public Client(String n){
        this.name = n;
    }

    @Override
    public void send(String message, Actor actor){
        actor.receive(message, this);
    }

    @Override
    public void receive(String message, Actor actor){

    }

    @Override
    public String getName(){
        return this.name;
    }

}
