import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    
    Buffer buffer;
    
    private static boolean active;
    private static int sleep;
    private int idC;
    
    Consumer(Buffer buffer, int idC) {
        this.buffer = buffer;
        this.active = true;
        this.idC = idC;
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
            try{
                String product = this.buffer.consume();
                String[] splited_p = product.split("\\$");
                float result = 0;
                

                switch(splited_p[1].charAt(1)){
                case '+':
                    result = Character.getNumericValue(splited_p[1].charAt(3))+Character.getNumericValue(splited_p[1].charAt(5));
                    break;
                case '-':
                    result = Character.getNumericValue(splited_p[1].charAt(3))- Character.getNumericValue(splited_p[1].charAt(5));
                    break;
                case '/':
                    result = (float) Character.getNumericValue(splited_p[1].charAt(3))/Character.getNumericValue(splited_p[1].charAt(5));
                    break;
                case '*':
                    result = Character.getNumericValue(splited_p[1].charAt(3))*Character.getNumericValue(splited_p[1].charAt(5));
                    break;
                }
                               
                Buffer.print("Consumer consumed: " +splited_p[0]+" "+splited_p[1]+" : "+result);
                Buffer.insertRowC(splited_p[0],this.idC,splited_p[1],result);

            } catch(IndexOutOfBoundsException error) {}
            
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