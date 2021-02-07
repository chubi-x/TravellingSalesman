/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author PC
 */
public class Population {
    //population constructor that takes population size and GA class as parameters
     public Population(int sizeOfPopulation, GA GA){
         //provide a range from 0 to the population size(excluding it)
         // for each path in the add the initial path to the paths arraylist
         IntStream.range (0,sizeOfPopulation).forEach(path->paths.add(new Path(GA.getInitialPath())));
     }
     //constructor that initializes all the paths
     public Population(int sizeOfPopulation, ArrayList<City>pathCities){
          //provide a range from 0 to the population size (excluding it)
         //  for each path in the population, add a new path to the path cities arraylist
         IntStream.range(0,sizeOfPopulation).forEach(path-> paths.add(new Path(pathCities)));
     }
    //initialize paths arraylist with the population size as the size
     private ArrayList<Path> paths = new ArrayList<>(GA.sizeOfPopulation);
     
     //method that returns the paths in a population
     public ArrayList<Path> getPaths(){
         //return the paths array list
         return paths;
     }
     // method that sorts paths by fitness
    public void sortPathsByFitness(){    
        //call sort method on paths
        paths.sort((path1,path2)->{
            //initialize sorting flag
            int sortFlag = 0;
        //check if fitness of first path is greater than second path and set the flag to negative
        if(path1.getPathFitness()> path2.getPathFitness()) sortFlag = -1;
        //check if fitness of first path is less than second path and set the flag to positive
        else if (path1.getPathFitness()< path2.getPathFitness()) sortFlag = 1;
        //return flag
        return sortFlag;
        });
    } 
}
