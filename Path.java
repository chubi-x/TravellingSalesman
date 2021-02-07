
package tsp;

import java.util.*;

/**
 *
 * @author PC
 */
public class Path {
    //changed fitness flag
    private boolean changedFitness = true;
    //initialize path fitness double to 0
    private double pathFitness = 0;
    //initialize cities array list that holds all the cities in a path
    private ArrayList<City> pathCities = new ArrayList<>();
    //path constructor that takes Genetic Algorithm as a parameter
    public Path(GA GA){
        // for each city in the initial path, add null to the path cities array list
        GA.getInitialPath().forEach((City city)->pathCities.add(null));
    }
    //path constructor that adds cities to a path and shuffles them
    public Path(ArrayList<City> pathCities){
        //add all cities to path
        this.pathCities.addAll(pathCities);
        //shuffle cities
        Collections.shuffle(this.pathCities);
    }
    //method that returns the cities in a path
    public ArrayList<City> getPathCities(){
        //set changedFitness to true
        changedFitness = true;
        //return the path cities
        return pathCities;
    }
    
    //method that returns the total distance of the cities in a path
    public double totalDistance(){
        //initialize sizeOfCities variable
        int sizeOfCities = this.pathCities.size();
        return (double) (this.pathCities.stream().mapToDouble((City city) -> {
            //get index of current city
            int cityIndex = Path.this.pathCities.indexOf(city);
            //initialize return value double
            double returnValue = 0;
            //check if index of city is less than overall size of cities minus 1
            if (cityIndex<sizeOfCities -1) { // to make sure no city is repeated
                //measure the distance between current city and next city
                returnValue = city.measureDistance(Path.this.pathCities.get(cityIndex+1));
            }
            //return the return value
            return returnValue;
        }).sum()+ this.pathCities.get(0).measureDistance(this.pathCities.get(sizeOfCities-1))); //calculate sum of stream elements and add to (distance between the first city and the second to last city)
        
    }
    //method that returns fitness of a path
    public double getPathFitness(){
        //check if path fitness is changed
        if(changedFitness == true){
            //set path fitness to 1 divided by total distance times a convenient number
            pathFitness = (1/totalDistance()*10000);
            //set changedFitness to false
            changedFitness = false;
        }
        //return the path fitness
        return pathFitness;
    }
    
    //overriden tostring method that converts cities arraylist to arrray
    @Override
    public String toString(){
        return Arrays.toString(pathCities.toArray());
}
}
      