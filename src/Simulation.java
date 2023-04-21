import java.util.Random;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
public class Simulation {

    private int nVertex;
    private int[][] edges;
    private int [][][] additions;
    private int [][] costs;
    private Vertex[] graph;
    public Simulation(){
        unSetnVertex();
    }

    public void unSetnVertex(){
        this.nVertex=0;
    }
    public void overridenVertex(int nVertex){
        this.nVertex=nVertex;
    }
    public void setMaxnVertex(){
        int mVal=0;
        for(int i=0;i<edges.length;i++){
            if(mVal<edges[i][1]){
                mVal=edges[i][1];
            }
        }
        this.nVertex=mVal+1;
    }
    public void setItems(int [][] edges, int[][][] additions, int[][] costs){
        this.edges=edges;
        this.additions=additions;
        if(nVertex==0){
            setMaxnVertex();
        }
        this.costs=costs;
    }
    public int simulate(int[] influencers){
        //graph initialization
        this.graph=new Vertex[nVertex];
        for(int i=0;i<nVertex;i++){
            this.graph[i]=new Vertex(i);
        }
        for(int i=0;i<edges.length;i++){
            this.graph[edges[i][0]].newEdge(this.graph[edges[i][1]]);
            this.graph[edges[i][1]].newEdge(this.graph[edges[i][0]]);
        }

        //Variables initialization
        Random random = new Random();
        List<Vertex> purchased = new LinkedList<>();//may disable
        Queue<Vertex> addPurchased = new LinkedList<>();
        Queue <Vertex> possibles = new LinkedList<>();
        Queue <Vertex> tempPossibles = new LinkedList<>();
        Queue <Queue<Vertex>> addPossibles= new LinkedList<>();
        int score;//=0;
        //add purchased to influencers
        for(int i=0;i<influencers.length;i++){
            purchased.add(graph[influencers[i]]);
            addPossibles.add(graph[influencers[i]].setPurchased());
        }
        for(Queue<Vertex> lst:addPossibles){
            for(Vertex vertex:lst){
                if(!vertex.isPurchasedState()){
                    possibles.add(vertex);
                }
            }
        }
        addPossibles.clear();

        System.out.println(purchased);
        System.out.println("Start");

        //start simulation
        for(int round=0;round<additions.length;round++){
            System.out.println("round "+(round+1));
            for(int i=0;i<additions[round].length;i++){
                this.graph[additions[round][i][0]].newEdge(this.graph[additions[round][i][1]]);
                this.graph[additions[round][i][1]].newEdge(this.graph[additions[round][i][0]]);
            }

            for(Vertex vertex: possibles){
                if(random.nextDouble()<=vertex.probability()){
                    //score++;
                    addPurchased.add(vertex);
                    purchased.add(vertex);
                }
                else{
                    tempPossibles.add(vertex);
                }
            }
            possibles.clear();
            addPossibles.add(tempPossibles);

            //Change status
            for(Vertex vertex:addPurchased){
                addPossibles.add(vertex.setPurchased());
            }
            addPurchased.clear();
            //New possibilities
            for(Queue<Vertex> lst:addPossibles){
                for(Vertex vertex:lst){
                    if(!vertex.isPurchasedState()){
                        possibles.add(vertex);
                    }
                }
            }
            tempPossibles.clear();
            addPossibles.clear();

            System.out.println(purchased);
            System.out.println("bought " + purchased.size());
        }
        score=purchased.size();
        for(int i=0;i<influencers.length;i++){
            for(int j=0; j<costs.length;j++){
                if(influencers[i]==costs[j][0]){
                    score-=costs[j][1];
                    break;
                }
            }
        }

        return score;
    }



}
