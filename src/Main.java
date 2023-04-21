import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[][] influencers=new int[1][5];
        influencers[0][0]=0;
        influencers[0][1]=348;
        influencers[0][2]=414;
        influencers[0][3]=3980;
        influencers[0][4]=686;

        IterateSimulations iterateSimulations = new IterateSimulations("C:\\Users\\USER\\Desktop\\hw1_ec",influencers);
        iterateSimulations.oneSimulation();
    }
}
