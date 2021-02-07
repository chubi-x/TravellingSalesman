/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

/**
 *
 * @author PC
 */
public class City {
    private  int x; //x cooridnate of city
    private  int y; //y coordinate of city
    private  String name; //city name
    
    //city constructor
    public City(String name,int x, int y){
        this.name = "city "+ name;
        this.x = x;
        this.y =y;  
    }
   
    //method that returns the name of city
    public String getName(){
        return this.name;
    }   
    //overriden toString method that returns name of city
    @Override
    public String toString(){
        return getName();
    }
    
    //method that measures distance between current city and passed in city
    public double measureDistance(City city){
        //return the ecludian distance between the cities
        return Math.sqrt((Math.pow((this.x - city.x), 2)+ Math.pow((this.y - city.y),2)));
    }
    
}
