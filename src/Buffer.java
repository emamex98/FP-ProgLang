import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class Buffer {
    
    private ArrayList<String> buffer;
    private static int length;
    private int id;
    private static JTable tablitaP, tablitaC;
    private static DefaultTableModel modelP, modelC;
    private static JTextField txtCompleted;
    private static JProgressBar progressB;
    private static int percentage;
    
    Buffer(JTable tablitaP, JTable tablitaC, JTextField textito, JProgressBar progressB) {
        this.buffer = new ArrayList<String>();
        this.id = 0;
        this.tablitaP = tablitaP;
        this.tablitaC = tablitaC;
        this.modelC = (DefaultTableModel) tablitaC.getModel();
        this.modelP = (DefaultTableModel) tablitaP.getModel();
        this.txtCompleted = textito;
        this.progressB = progressB;
        // Defaults
        this.percentage = 0;
        this.length = 1;
    }
    
    synchronized String consume() {
        String product = null;
        
        if(this.buffer.isEmpty()) {
            try {
                if(this.length > 1)
                    wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        product = this.buffer.remove(0);
        Buffer.delRowP(this.buffer.size());
        notify();
        
        return product;
    }
    
    synchronized void produce(String product, int idP) {
        
        if(this.buffer.size() >= this.length) {
            try {
                if(this.length > 1)
                    wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(this.buffer.size() < this.length){
            this.id++;
            this.buffer.add(this.id+"$"+product);
            Buffer.insertRowP(this.id+"",idP,product);
            this.progressB.setValue(this.getPercentage());
            notify();
        }
        
    }
    
    static int count = 1;
    
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
    synchronized static void insertRowC(String id, int idC, String operation, float r){
        Buffer.modelC.insertRow(0, new Object[]{id,idC,operation,r});
        Buffer.tablitaC.setModel(modelC);
        Buffer.txtCompleted.setText(id);
    }
    
    synchronized static void insertRowP(String id, int idP, String operation){
        Buffer.modelP.insertRow(0, new Object[] {id,idP,operation});
        Buffer.tablitaP.setModel(modelP);
    }
    
    synchronized static void delRowP(int index){
        try{
            Buffer.modelP.removeRow(index);
            Buffer.tablitaP.setModel(modelP);
        } catch(ArrayIndexOutOfBoundsException error){
            
        }
    }
    
    public void clearModelC(){
        this.modelC.setRowCount(0);
        Buffer.tablitaC.setModel(modelC);
    }
    
    public void clearModelP(){
        this.modelP.setRowCount(0);
        Buffer.tablitaP.setModel(modelP);
    }
    
    public void setLength(int length){
        this.length = length;
    }
    
    public int getPercentage(){
        return (int) (this.buffer.size()*100/this.length);
    }
    
}
