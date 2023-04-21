import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java .util.LinkedList;

public class IterateSimulations {
    private String inputFile;
    private int[][] influencers;
    public IterateSimulations(String inputFile, int[][] influencers){
        this.inputFile = inputFile;
        this.influencers = influencers;
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
        int [][] doubleArr = new int[readList.size()-1][2];
        int index=-1;
        for(String[] stArr:readList){
            if(index!=-1){
                doubleArr[index][0]=Integer.parseInt(stArr[0]);
                doubleArr[index][1]=Integer.parseInt(stArr[1]);
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
            br.close();
        }
        catch(Exception e){
            return null;
        }
        int [][][] tripleArr = new int[readList.size()][][];
        int indexOuter=0;
        int indexIneer=0;
        for(List<String[]> stLis:readList){
            tripleArr[indexOuter]=new int[stLis.size()][2];
            for(String[] stArr:stLis){
                tripleArr[indexOuter][indexIneer][0]=Integer.parseInt(stArr[0]);
                tripleArr[indexOuter][indexIneer][1]=Integer.parseInt(stArr[1]);
                indexIneer++;
            }
            indexOuter++;
        }
        return tripleArr;
    }


    public void oneSimulation(){
        int[][] costs=csvReadDouble("costs");
        int[][] edges=csvReadDouble("chic_choc_data");
        if(costs==null || edges==null)
            return;
        Simulation simulation = new Simulation();
        simulation.overridenVertex(4039);
        simulation.setItems(edges,new int[6][0][0],costs);
        int score=simulation.simulate(influencers[0]);
        System.out.println(score);
    }
}
