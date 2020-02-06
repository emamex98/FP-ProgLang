import java.util.Random;

public class SchemeOperation extends Thread {
    
    private int id;
    private int nT;
    private String[] operadores = {"+","-","*","/"};
    
    public SchemeOperation(int id, int nT){
        this.id = id;
        this.nT = nT;
    }
    
    @Override
    public void run() {
        System.out.println("Soy el hilo " + this.id);
        Random random = new Random();
        for(int i = 0; i < this.nT; i++){
            String operacion = "("
                    +operadores[random.nextInt(4)]
                    +" "+random.nextInt(10)
                    +" "+random.nextInt(10)
                    +")";
            System.out.println(operacion);
        }
    }
    
}
