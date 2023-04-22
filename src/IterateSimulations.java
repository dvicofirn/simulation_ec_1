import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java .util.LinkedList;

public class IterateSimulations {
    private String inputFile;
    private String edges_data;
    private String costs_data;
    private String addition_file;
    private String addition_template;
    public IterateSimulations(String inputFile, String edges_data, String costs_data, String addition_file, String addition_template){
        this.inputFile = inputFile;
        this.edges_data=edges_data;
        this.costs_data=costs_data;
        this.addition_file=addition_file;
        this.addition_template=addition_template;
    }

    public int[][] csvReadDouble(String csvName){
        List<String[]> readList = new LinkedList<>();
        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(this.inputFile+"\\"+csvName+".csv"))
            );
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] curr=line.split(",");
                readList.add(curr);
            }
            br.close();
        }
        catch(Exception e){
            return null;
        }
        int [][] doubleArr = new int[readList.size()-1][readList.get(0).length];
        int index=-1;
        for(String[] stArr:readList){
            if(index!=-1){
                for(int i=0; i<stArr.length;i++){
                    doubleArr[index][i]=Integer.parseInt(stArr[i]);
                }
            }
            index++;
        }
        return doubleArr;
    }

    public int[][][] csvReadTriple(String csvName){
        List<List<String[]>> readList = new LinkedList<>();
        List<String[]> tempList=null;
        try{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(this.inputFile+"\\"+csvName+".csv"))
            );
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] curr=line.split(",");
                if(curr[0].charAt(0)>'9' || curr[0].charAt(0)<'0'){
                    if(tempList!=null) {
                        readList.add(tempList);
                    }
                    tempList = new ArrayList<>();
                }
                else{
                    tempList.add(curr);
                }
            }
            readList.add(tempList);
            br.close();
        }
        catch(Exception e){
            return null;
        }
        int [][][] tripleArr = new int[readList.size()-1][][];
        int indexOuter=-1;
        int indexIneer;
        for(List<String[]> stLis:readList){
            if(indexOuter!=-1){
                indexIneer=0;
                tripleArr[indexOuter]=new int[stLis.size()][stLis.get(0).length];
                for(String[] stArr:stLis){
                    for(int i=0; i<stArr.length; i++){
                        tripleArr[indexOuter][indexIneer][i]=Integer.parseInt(stArr[i]);
                    }
                    indexIneer++;
                }
            }
            indexOuter++;
        }
        return tripleArr;
    }


    public void oneSimulation(int[] influencers){
        int[][] edges=csvReadDouble(edges_data);
        int[][] costs=csvReadDouble(costs_data);

        int[][][] addition=csvReadTriple(addition_file+"\\"+addition_template+"1");
        if(costs==null || edges==null || addition==null)
            return;
        Simulation simulation = new Simulation();
        simulation.overridenVertex(4039);
        simulation.setItems(edges,addition,costs);
        int score=simulation.simulate(influencers);
        System.out.println(score);
    }

    public double multiSimulation(int iterations ,int[] influencers, int[][] edges, int[][] costs){
        int[][][] addition;
        int sumScore=0;
        int moduler=0;
        for(int i=1; i<=iterations; i++){
            addition=csvReadTriple(addition_file+"\\"+addition_template+(i-moduler));
            if(addition==null){
                if(i==1)
                    return 0;
                moduler=i-1;
                i--;
                continue;
            }
            Simulation simulation = new Simulation();
            simulation.overridenVertex(4039);
            simulation.setItems(edges,addition,costs);
            int score=simulation.simulate(influencers);
            //System.out.println("round " + i + " scoring: " + score);
            sumScore+=score;
        }
        //System.out.println("average "+(sumScore/(double)iterations));
        return sumScore/(double)iterations;
    }

    public void runSimulations(int iterations, int nOfGroups, String influencerList){
        int[][] edges=csvReadDouble(edges_data);
        int[][] costs=csvReadDouble(costs_data);
        if(costs==null || edges==null)
            return;
        int[][] influencers = csvReadDouble(influencerList);
        if(nOfGroups==0 || nOfGroups>influencers.length){
            nOfGroups=influencers.length;
        }
        double groupScore;
        for(int i=0; i<nOfGroups; i++){
            groupScore=multiSimulation(iterations,influencers[i],edges,costs);
            System.out.println("Group "+ (i+1) +" scored: "+groupScore);
        }
    }
}
