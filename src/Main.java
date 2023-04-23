import java.io.*;
import java.util.*;

public class Main {
    public static String inputFile="C:\\Users\\USER\\Desktop\\hw1_ec";
    public static String influencerList="ID1_ID2";
    public static String edges_data="chic_choc_data";
    public static String costs_data="costs";
    public static String addition_file="additions";
    public static String addition_template="addition";
    public static void main(String[] args) {

        IterateSimulations iterateSimulations = new IterateSimulations(inputFile,edges_data,costs_data,addition_file,addition_template);
        //iterateSimulations.oneSimulation();
        iterateSimulations.runSimulations(125,0,influencerList);
    }
}
