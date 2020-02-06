import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicBoolean; 

public class Producer extends Thread {
    
    Buffer buffer;
    
    private static boolean active;
    private static int sleep;
    private static int minInt;
    private static int maxInt;
    private int idP;
    
    Producer(Buffer buffer, int idP) {
        this.buffer = buffer;
        this.active = true;
        this.idP = idP;
        // Defaults
        this.sleep = 1000;
        this.minInt = 0;
        this.maxInt = 9;
    }
    
    public void stawp(){
        this.active = false;
    }
    
    
    @Override
    public void run() {
        
        System.out.println("Running Producer...");
        char[] operadores = {'+','-','*','/'};
        
        Random random = new Random(System.currentTimeMillis());
        
        while(this.active) {
            
            String product = "("
                    + operadores[random.nextInt(4)]
                    + " " + (random.nextInt((this.maxInt - this.minInt) + 1) + this.minInt)
                    + " " + (random.nextInt((this.maxInt - this.minInt) + 1) + this.minInt)
                    + ")";
            
            if(!(product.charAt(1) == '/' && product.charAt(5) == '0')){
                this.buffer.produce(product, this.idP);
                Buffer.print("Producer produced: " + product);
            } else {
                this.buffer.produce(product, this.idP);
                Buffer.print("Producer produced: " + product);
                System.out.println("Error: División entre cero, operación indefinida.");
            }
                
            
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void setSleep(int sleep){
        this.sleep = sleep;
    }
    
    public void setRange(int min, int max){
        this.minInt = min;
        this.maxInt = max;
    }
    
}
