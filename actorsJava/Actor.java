interface Actor {
    public void send(String message, Actor actor);
    public void receive(String message, Actor actor);
    public String getName();
}