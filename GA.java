
package tsp;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Chubiyojo Adejoh M00796245
 */
public class GA {
    //initialize mutation rate double
    public static final double mutRate = 0.01; 
    //initialize crossover selection value
    public static final int crossOverVal =  3;
    //initialize number of generations
    public static final int noOfGens = 200;
    //initialize population size variable
    public static final int sizeOfPopulation = 10;
     //initialize tournament selection population variable that holds routes to be used in selection tournament
    public static final int tourneySelectionPopulation = 3;
    //initialize initial path array list to null
    //initialize number of optimal paths variable
    public static final int optimalPaths = 1;
    private ArrayList<City> initialPath = null;
    //Genetic Algorithm constructor with initial path as parameter
    public GA(ArrayList<City> initialPath){
        this.initialPath = initialPath;
    }
    //method that returns the initial path
    public ArrayList<City> getInitialPath(){
        return initialPath;
    }
    //evolution method that performs crossover and mutation on passed in population
    public Population evolutionFunction(Population population){
        return populationMutatition(crossOver(population));
    }
    //crossover method that performs crossover on population
    Population crossOver (Population population){
        //instantiate new crosover population given the size of old population and passing in instance of Genetic Algorithm class
        Population coPopulation = new Population(population.getPaths().size(),this);
        //provide a range from zero to the optimal paths. In the range, for each city in cross over population paths,get the city in the original population paths and set the paths to it.
       IntStream.range(0,optimalPaths).forEach(city->coPopulation.getPaths().set(city,population.getPaths().get(city))); //ask maha
       // for the remaining paths, set a range between the optimal path and the crossover population
       IntStream.range(optimalPaths,coPopulation.getPaths().size()).forEach(city->{
           //for each city in the range, use tournament selection to select two paths
           Path path1 =  tourneyPopulationSelection(population).getPaths().get(0);
           Path path2 =  tourneyPopulationSelection(population).getPaths().get(0);
           //call crossoverPath method on new paths
           coPopulation.getPaths().set(city,crossOverPath(path1,path2));
       });
       return coPopulation;    
    }
    //mutation method that performs mutation on population
    Population populationMutatition (Population population){
        // get paths of population and filter it, call mutatePath method on all non optimal paths 
        population.getPaths().stream().filter(city->population.getPaths().indexOf(city)>=optimalPaths).forEach(path->mutatePath(path));
        return population;
    }
        //method that performs mutation on a path
    Path mutatePath (Path path){
        //get cities in the path and filter them for when a random number is less than the mutation rate
        path.getPathCities().stream().filter(city->Math.random()<mutRate).forEach(cityA->{
            //for each city, A, initialize an integer that holds the product of a random number and the size of the cities list, which will be the index of a random city
            int b = (int) (path.getPathCities().size() * Math.random());
            //initialize a random city from the cities list
            City cityB = path.getPathCities().get(b);
            //switch the positions of cities A and B
            path.getPathCities().set(path.getPathCities().indexOf(cityA),cityB);
            path.getPathCities().set(b,cityA);
        });
        return path;
    }
    //method that performs crossover on two paths
    Path crossOverPath(Path path1,Path path2){
        //initialize new crossover path
        Path crossOverPath = new Path(this);
        //initialize first temporary path to path1
        Path tempPath1 = path1;
        //initialize second temporary path to path2
        Path tempPath2 = path2;
        //randomly switch temporary paths
        if(Math.random()<0.5){
            tempPath1 = path2;
            tempPath2 = path1;
        }
        //loop through the first part of the crosover path cities
        for(int i =0;i<crossOverPath.getPathCities().size()/2;i++){
            //set the first few cities of the crossover path to the first specified path
            crossOverPath.getPathCities().set(i,tempPath1.getPathCities().get(i));
        }
        //fill the null cities in the crossover path
        return fillCrossOverPath(crossOverPath, tempPath2);
    }
    //method that fills null cities in crossover path
    private Path fillCrossOverPath(Path crossOverPath,Path secondPath){
        //filter second path to check if the crossOverPath contains a city
        secondPath.getPathCities().stream().filter(city->!crossOverPath.getPathCities().contains(city)).forEach(cityA->{
            //for each city that is not in the cross over path, loop through second path
           for(int i = 0;i<secondPath.getPathCities().size();i++){
               //check if the crossover path contain a null city
               if(crossOverPath.getPathCities().get(i) == null){
                   //set the value of the null city to the non existing city in the crossover path
                   crossOverPath.getPathCities().set(i,cityA);
                   break;
               }
           } 
        });
        return crossOverPath;
    }

    //tournament selection method that picks random paths from the population for crossover and mutation
    Population tourneyPopulationSelection (Population population){
       //initialize tournament population variable that takes the tournament selection size and GA class instance as arguments
        Population tourneyPopulation = new Population(tourneySelectionPopulation,this);
        //randomly select a number of paths in the tournament population
        IntStream.range(0,tourneySelectionPopulation).forEach(path->tourneyPopulation.getPaths().set(path,population.getPaths().get((int) (Math.random()*population.getPaths().size()))));
        //sort the paths by fitness
        tourneyPopulation.sortPathsByFitness();
        //return tournament population
        return tourneyPopulation;
    }
}
