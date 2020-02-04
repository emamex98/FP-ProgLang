import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    
    Buffer buffer;
    
    private static boolean active;
    private static int sleep;
    
    Consumer(Buffer buffer) {
        this.buffer = buffer;
        this.active = true;
        
        // Defaults
        this.sleep = 1000;
    }
    
    public void stawp(){
        this.active = false;
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        
        while(active) {
            String product = this.buffer.consume();
            
            Buffer.print("Consumer consumed: " + product);
            
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
    
}