import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Buffer {
    
    private ArrayList<String> buffer;
    private static int length;
    
    Buffer() {
        this.buffer = new ArrayList<String>();
        
        // Defaults
        this.length = 1;
    }
    
    synchronized String consume() {
        String product = null;
        
        if(this.buffer.isEmpty()) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        product = this.buffer.remove(0);
        notify();
        
        return product;
    }
    
    synchronized void produce(String product) {
        
        if(this.buffer.size() == this.length) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.buffer.add(product);
        notify();
    }
    
    static int count = 1;
    
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
    public void setLength(int length){
        this.length = length;
    }
    
}
