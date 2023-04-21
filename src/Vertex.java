import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
public class Vertex {
    private int id;
    private int neighbourPurchased;
    private boolean purchasedState;
    private List<Vertex> adjacencyList;
    private int adjacencyListLen;

    public Vertex(int id, boolean purchasedState, LinkedList adjacencyList, int adjacencyListLen){
        this.id=id;
        this.purchasedState=purchasedState;
        this.adjacencyList=adjacencyList;
        this.adjacencyListLen=adjacencyListLen;
        updatePurchasedState();
    }
    private void updatePurchasedState(){
        this.neighbourPurchased=0;
        for (Vertex vertex:adjacencyList) {
            if (vertex.isPurchasedState())
                this.neighbourPurchased++;
        }
    }
    public void incrementPurchasedState(){
        this.neighbourPurchased++;
    }
    public Vertex(int id, boolean purchasedState){
        this(id,purchasedState,new LinkedList<Vertex>(),0);
    }
    public Vertex(int id, LinkedList adjacencyList, int adjacencyListLen){
        this(id, false, adjacencyList, adjacencyListLen);
    }
    public Vertex(int id){
        this(id,false, new LinkedList<Vertex>(),0);
    }

    public int getId() {
        return id;
    }

    public boolean isPurchasedState() {
        return purchasedState;
    }

    public int getNeighbourPurchased() {
        return neighbourPurchased;
    }

    public void newEdge(Vertex vertex){
        this.adjacencyList.add(vertex);
        this.adjacencyListLen++;
        if(vertex.isPurchasedState())
            this.neighbourPurchased++;
    }

    public double probability(){
        if(this.adjacencyListLen==0)
            return 0;
        return this.neighbourPurchased/(double)this.adjacencyListLen;
    }

    public Queue<Vertex> setPurchased(){
        Queue<Vertex> toBePossible = new LinkedList<>();
        if(!this.purchasedState){
            this.purchasedState=true;
            for (Vertex vertex:adjacencyList) {
                if (!vertex.isPurchasedState() && vertex.getNeighbourPurchased()==0){
                    toBePossible.add(vertex);}
                vertex.incrementPurchasedState();
            }
        }
        return toBePossible;
    }
    public String adjacString(){
        return this.adjacencyList.toString();
    }

    @Override
    public String toString() {
        return ""+ this.id;
    }
}
