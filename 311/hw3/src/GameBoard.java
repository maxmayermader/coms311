import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
//    int[] grid;
//    ArrayList<Vehicle> vehicles;
    GameState firstGameState;
    HashMap<HashKey, GameState> hashMap;

    //que used for bfs search
    Deque<int[]> que;
    /**
     * Game board constructor
     */
    public GameBoard(){
        //this.pairs = new ArrayList<>();
        this.hashMap = new HashMap<HashKey, GameState>();
        this.firstGameState = new GameState();
        //this.grid = new int[36];
        //Arrays.fill(grid, -1);
        //this.vehicles = new ArrayList<>();
    }

    public void readInput(String FileName) throws IOException {
        this.firstGameState.populateGrid(FileName);
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







    /**
     * GameState class
     * used for having game state of grid and keeping track of vehicles
     */
    class GameState {
        int[] grid;
        ArrayList<Vehicle> vehicles;
        Pair move;

        public GameState() {
            this.grid = new int[36];
            this.vehicles = new ArrayList<>();
            Arrays.fill(grid, -1);
        }

        public GameState(ArrayList<Vehicle> vehicles, int id, char direcMoved){
            this.grid = new int[36];
            Arrays.fill(this.grid, -1);
            this.vehicles = new ArrayList<>();
            //add cars and adjust position of moved car
            for (Vehicle car : vehicles) {
                Vehicle currCar = car.clone();
                if (currCar.getId() == id){
                    if (direcMoved == 'L'){
                        currCar.setBegin(currCar.getBegin()-1);
                        currCar.setEnd(currCar.getEnd()-1);
                    } else if (direcMoved == 'R') {
                        currCar.setBegin(currCar.getBegin()+1);
                        currCar.setEnd(currCar.getEnd()+1);
                    } else if (direcMoved == 'U') {
                        currCar.setBegin(currCar.getBegin()-1);
                        currCar.setEnd(currCar.getEnd()-1);
                    } else if (direcMoved == 'D') {
                        currCar.setBegin(currCar.getBegin()+1);
                        currCar.setEnd(currCar.getEnd()+1);
                    }
                }
                this.vehicles.add(currCar);
            }

            //make the grid
            for (int i=0; i<this.vehicles.size(); i++){
                Vehicle currVehicle = this.vehicles.get(i);
                for (int j=0; j<currVehicle.getLength(); j++){
                    if (currVehicle.getDirec() == 0) {
                        this.grid[currVehicle.begin + j] = currVehicle.getId();
                    } else {
                        this.grid[currVehicle.begin + 6 * j] = currVehicle.getId();
                    }
                }
            }
            System.out.println("This is a new grid");
            System.out.println(printGrid());
        }

        public void populateGrid(String FileName) throws FileNotFoundException {
            File file = new File(FileName);
            Scanner scnr = new Scanner(file);
            int numCars = scnr.nextInt();
            scnr.nextLine();

            for (int i = 0; i < numCars; i++) {
                String line = scnr.nextLine();
                String[] nums = line.split(" ");
                int begin = Integer.valueOf(nums[0]) - 1;
                int end = Integer.parseInt(nums[nums.length - 1]) - 1;
                Vehicle currVehicle = new Vehicle(i, begin, end);
                vehicles.add(currVehicle);
                for (int j = 0; j < currVehicle.getLength(); j++) {
                    if (currVehicle.getDirec() == 0) {
                        this.grid[currVehicle.begin + j] = currVehicle.getId();
                    } else {
                        this.grid[currVehicle.begin + 6 * j] = currVehicle.getId();
                    }
                }

            }
            //testing: TODO
            System.out.println(vehicles.toString());
            System.out.println(printGrid());
            System.out.println(getNextMoves(grid).toString());
        }

        private ArrayList<GameState> getNextMoves(int[] startGrid){
            //TODO
            ArrayList<GameState> grids = new ArrayList<>();
            //go through all vehicles to see which ones can move
            for (int i=0; i<vehicles.size(); i++){
                Vehicle currVehicle = vehicles.get(i);
                if (currVehicle.direc == 0){
                    if (currVehicle.canMoveRight() ) {
                        if (grid[currVehicle.getEnd()] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'R'));
                        }
                    } if (currVehicle.canMoveLeft()) {
                        if (grid[(currVehicle.getBegin() - 1) - 1] == -1)
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'L'));
                    }
                } else {
                    if (currVehicle.canMoveUp()) {
                        //TODO check logic. potential bug
                        if (grid[(currVehicle.getBegin() - 6) - 1] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'U'));
                        }
                    }  if (currVehicle.canMoveDown() ) {
                        if (grid[(currVehicle.getEnd() + 6) - 1] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'D'));
                        }
                    }
                }
            }

            return grids;
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
    }






    /**
     * HashKey class.
     * Given
     * TODO:
     */
    class HashKey {
        int[] c; // attribute
        public HashKey(int[] inputc) {
            c = new int[inputc.length];
            c = inputc;
        }
        public boolean equals(Object o) {
            boolean flag = true;
            if (this == o) return true; // same object
            if ((o instanceof HashKey)) {
                HashKey h = (HashKey)o;
                int[] locs1 = h.c;
                int[] locs = c;
                if (locs1.length == locs.length) {
                    for (int i=0; i<locs1.length; i++) {
                        // mismatch
                        if (locs1[i] != locs[i]) {
                            flag = false;
                            break;
                        }
                    }
                }
                else // different size
                    flag = false;
            }
            else // not an instance of HashKey
                flag = false;
            return flag;
        }
        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
            return Arrays.hashCode(c); // using default hashing of arrays
        }
    }

    /**
     * pair class
     * This is actually used to represent the car that moved and the direction
     */
    private class Pair{
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
         public Vehicle(int id, int begin, int end, int direc, int length){
             this.id = id;
             this.begin = begin;
             this.end = end;
             this.direc = direc;
             this.length = length;
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

        public boolean canMoveRight(){
            if (this.direc == 0){
                if (this.end%6 == 0){
                    return false;
                }
                return true;
            }
           return false;
        }
         public boolean canMoveLeft(){
             if (this.direc == 0){
                 if (this.begin%6 == 1){
                     return false;
                 }
                 return true;
             }
             return false;
         }
         public boolean canMoveUp(){
            if (this.direc == 1){
               if (this.begin <= 6){
                   return false;
               }
               return true;
            }
            return false;
         }
         public boolean canMoveDown(){
             if (this.direc == 1){
                 if (this.end >= 31){
                     return false;
                 }
                 return true;
             }
             return false;
         }

         @Override
         public Vehicle clone(){
            return new Vehicle(this.id, this.begin, this.end, this.direc, this.length);
         }
    }
}
