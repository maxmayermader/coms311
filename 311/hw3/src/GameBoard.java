import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard {
//// primary class for this file
//
//// attributes
//// specified methods
//// Any other helper methods
//    }
//
//// as described above
//// Any other helper attributes and methods
//    }
    private ArrayList<Pair> pairs;
    /**
     * Game board constructor
     */
    public GameBoard(){
        this.pairs = new ArrayList<>();
    }


    public void readInput(String FileName) throws IOException {
        File file = new File(FileName);
        Scanner scnr = new Scanner(file);
        int numCars = scnr.nextInt();
//        for(int i=0; i<numCars; i++){
//            char direc;
//            int num1 = scnr.nextInt();
//            int num2 = scnr.nextInt();
//
//            if (num1+1 == num2) {
//
//            }
//            pairs.add(new Pair(i, ))
//        }
    }

    /**
     * Method used to get the shortest path of moved
     *
     * @return ArrayList of Pairs
     */
    public ArrayList<Pair> getPlan(){
        return null;
    }


    /**
     * The objective of the method is to compute and return the number of shortest
     * plans that can realize the game objective for the initial game setup
     *
     * @return the number of shortest paths
     */
    public int getNumOfPaths(){
        return -1;
    }

    //    class <helperclass> {
//    }


    /**
     * pair class
     * This is actually used to represent the car that moved and the direction
     */
    public class Pair{
        int id;
        char direction; // {’e’, ’w’, ’n’, ’s’}
        public Pair(int i, char d) { id = i; direction = d; }
        int getDirection() { return direction; }
        int getId() { return id; }
        void setDirection(char d) { direction = d; }
        void setId(int i) { id = i; }
    }
}
