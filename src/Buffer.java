import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
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
    
    Buffer(JTable tablitaP, JTable tablitaC, JTextField textito) {
        this.buffer = new ArrayList<String>();
        this.id = 0;
        this.tablitaP = tablitaP;
        this.tablitaC = tablitaC;
        this.modelC = (DefaultTableModel) tablitaC.getModel();
        this.modelP = (DefaultTableModel) tablitaP.getModel();
        this.txtCompleted = textito;
        // Defaults
        this.length = 1;
    }
    
    synchronized String consume() {
        String product = null;
        
        if(this.buffer.isEmpty()) {
            try {
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
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.id++;
        this.buffer.add(this.id+"$"+product);
        Buffer.insertRowP(this.id+"",idP,product);
        notify();
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
        Buffer.modelP.removeRow(index);
        Buffer.tablitaP.setModel(modelP);
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
    
    public int getSize(){
        return this.buffer.size();
    }
}
