public class Main {
    
    public static void main(String[] args){
        
        Client a = new Client("client a");
        Film f1 = new Film("film numero 1");

        a.send("regarder", f1);

    }

}
