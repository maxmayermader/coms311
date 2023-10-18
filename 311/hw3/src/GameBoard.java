import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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
    //private ArrayList<Pair> pairs;
    /**
     * Grid for game states
     * [
     */
    int[] grid;
    ArrayList<Vehicle> vehicles;

    //que used for bfs search
    Deque<int[]> que;
    /**
     * Game board constructor
     */
    public GameBoard(){
        //this.pairs = new ArrayList<>();
        this.grid = new int[36];
        Arrays.fill(grid, -1);
        this.vehicles = new ArrayList<>();
    }

    public void readInput(String FileName) throws IOException {
        File file = new File(FileName);
        Scanner scnr = new Scanner(file);
        int numCars = scnr.nextInt();
        scnr.nextLine();
        for(int i=0; i<numCars; i++){
            String line = scnr.nextLine();
            String[] nums = line.split(" ");
            int begin = Integer.valueOf(nums[0])-1;
            int end = Integer.parseInt(nums[nums.length-1])-1;
            Vehicle currVehicle = new Vehicle(i,begin, end);
            vehicles.add(currVehicle);
            for (int j = 0; j<currVehicle.getLength(); j++){
                if (currVehicle.getDirec() == 0){
                    grid[currVehicle.begin+j] = currVehicle.getId();
                } else {
                    grid[currVehicle.begin+6*j] = currVehicle.getId();
                }
            }

        }
        //testing: TODO
        System.out.println(vehicles.toString());
        System.out.println(printGrid());
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
        //TODO
        //BFS();
        return -1;
    }

    //TODO
    private void BFS(int[] startGrid) {
    }

    private ArrayList<int[]> getNextMoves(int[] startGrid){
        ArrayList<int[]> grids = new ArrayList<>();
        //go through all vehicles to see which ones can move
        for (int i=0; i<vehicles.size(); i++){
            Vehicle currVehicle = vehicles.get(0);
            if (currVehicle.direc == 0){
                //if (currVehicle.canMoveRight)
            } else {

            }
        }

        return null;
    }



    public String printGrid(){
        String printed = "";
        for (int i=0; i<36; i++){
            printed += grid[i] != -1 ? grid[i]+", " : "_, ";
                    //grid[i]+", ";
            if ((i+1)%6 == 0)
                printed+="\n";
        }
        return printed;
    }

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

     private class Vehicle{
        final int id;
        //direction: 0 horizontal, 1 vertical
        final int direc;
        //length of vehicle
       final int length;
        //beginning index
        int begin;
        //end index
        int end;
        int row;
        int col;

        /**
         * Constructor for vehicle
         * @param id
         * @param begin
         * @param end
         */
        public Vehicle(int id, int begin, int end){
            this.id = id;
            this.begin = begin;
            this.end = end;

            if (end-begin < 6){
                direc = 0;
                length = end-begin+1;
                row = (int) Math.floor(end/6.0);
            } else {

                direc = 1;
                length = (end-begin)/6+1;
                col = begin%6;

            }
        }

        public int getBegin() {
            return begin;
        }

        public int getDirec() {
            return direc;
        }

        public int getEnd() {
            return end;
        }

        public int getId() {
            return id;
        }

        public int getLength() {
            return length;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public void setEnd(int end) {
            this.end = end;
        }

//        public boolean canMoveRight(){
//            if (this.direc == 0){
//                if (this.end+1 != ())
//            }
//        }
//         public boolean canMoveLeft(){
//
//         }
//         public boolean canMoveUp(){
//
//         }
//         public boolean canMoveDown(){
//
//         }
    }
}
