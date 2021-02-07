/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class Salesman {
    //create testing file
   public static File testFile = new File("C:\\Users\\PC\\Documents\\NetBeansProjects\\TravelingSalesman\\src\\tsp\\test4-20.txt");
   //declare initial path array list
    public static ArrayList<City> initialPath = new ArrayList<>(Arrays.asList());
    //main method
    public static void main(String[] args){
        //start time variable
        long startTime = System.nanoTime(); 
        //try catch block that reads coordinates from given files
         try {
             //initiate new scanner
             try (Scanner scanner = new Scanner(testFile)) {
                 //check if scannner has a next line
                 while (scanner.hasNextLine()) {
                     //line variable that holds the nextline in scanner
                     String line = scanner.nextLine();
                     //line array that splits the lines into an array
                     String lineArray[] = line.split(" ");                      
                     //add parsed cities and coordinates to the initial path
                      initialPath.add(new City(lineArray[0],Integer.parseInt(lineArray[1]),Integer.parseInt(lineArray[2])));
                 }
             }
             //catch block that throws exception when file is not found
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    } 
        //create an instance of the salesman class
        Salesman salesman = new Salesman();
        //initialize an initial population instance, passing in the initial path
        Population initialPopulation = new Population(GA.sizeOfPopulation,Salesman.initialPath);
        //initialize a generation number to 0
        int genNum = 0;
        //sort the paths in the population by fitness
        initialPopulation.sortPathsByFitness();
        //call new GA instance with the initial path as argument
        GA GA = new GA(Salesman.initialPath);
        //while loop that performs evolution function on initial population
        while(genNum<GA.noOfGens){
            //go to the next generation
            genNum++;
            //evolve the initial path
            initialPopulation = GA.evolutionFunction(initialPopulation);
            //sort the paths by fitness
            initialPopulation.sortPathsByFitness();
        }
       //print the optimal path
        System.out.println("The Optimal path is ");
        //loop through the optimal path and print each city
       for (Object city : initialPopulation.getPaths().get(0).getPathCities().toArray()) {
           System.out.print(city + "-> " );
       }
       //add the first city to the end of the path to complete the circuit
       System.out.print( initialPopulation.getPaths().get(0).getPathCities().toArray()[0]);
       //print the total distance
        System.out.println("\nWith a total distance of "+ String.format("%.2f",initialPopulation.getPaths().get(0).totalDistance()));
        //note the end time
        long endTime = System.nanoTime(); 
        //calculate difference between start time and end time to find out total time
        long totalTime = endTime-startTime;
        //print the total time
        System.out.println("Time taken for code is:"+totalTime+ " nano seconds" );
    }
}
