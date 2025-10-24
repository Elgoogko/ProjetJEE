/**
 * 
 * Interface Actor
 * 
 * Setup id to allow interaction with a micro service wich don't know a speficif actor
 * Example : Client and Film are in different micro service
 * So if we send an message between them with actor structur that can be interpreted
 * So id allow to just take id get more information or specific information later
 * 
 */
interface Actor {

    /**
     * 
     * 
     * static idList; structur of id with subactor if there is subactor
     * int id; id of the actor
     * 
     */

    
    /**
     * Send a message to a specific actor
     * 
     * @param message 
     * @param actor 
     */
    public void sendWithActor(String message, Actor actor);

    /**
     * Receive message from a specific actor
     * 
     * @param message
     * @param actor
     */
    public void receiveWithActor(String message, Actor actor);

    /**
     * 
     * Send message to an actor with his id
     * 
     * @param message
     * @param id
     */
    public void sendWithId(String message, int id);

    /**
     * 
     * Receive a message from an actor with given id
     * 
     * @param message
     * @param id
     */
    public void receiveWithId(String message, int id);

    /**
     * Creattion of a subACtor
     * At ovverride with the actor structur in actor
     * write a method to generate new id
     * 
     * @return true if the actor is correctly created, false if not
     */
    public boolean createNewSubActor();

    /**
     * Delete of a subACtor
     * At ovverride with the actor structur in actor
     * write a method to remove id
     * 
     * @param id of actor to remove
     * @return true if the actor is correctly delete, false else
     * 
     */
    public boolean deleteSubActor(int id);

    /**
     * Check if there is an actor who have this id
     * 
     * @param id which want to know if there is an actor with it
     * @return true if there is an actor who have id  in this obkectb type
     */
    public boolean thisSubActorExist(int id);

    /**
     * getter of id
     * @return id of the actor
     */
    public int getId();
}