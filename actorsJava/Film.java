public class Film implements Actor {
    
    private String name;

    public Film(String n){
        this.name = n;
    }

    @Override
    public void send(String message, Actor actor){
        actor.receive(message, this);
    }

    @Override
    public void receive(String message, Actor actor){
        if (message == "regarder"){
            System.out.println("le client "+actor.getName()+" regarde le film "+this.name);
        }
    }

    @Override
    public String getName(){
        return this.name;
    }
    
}
