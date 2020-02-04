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
    
    Producer(Buffer buffer) {
        this.buffer = buffer;
        this.active = true;
        
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
                    + " " + random.nextInt(10)
                    + " " + random.nextInt(10)
                    + ")";
            
            this.buffer.produce(product);
            Buffer.print("Producer produced: " + product);
            
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
