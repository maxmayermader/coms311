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
    HashMap<HashKey, GameState> hm;

    //que used for bfs search
    Deque<GameState> que;
    int numPaths;
    /**
     * Game board constructor
     */
    public GameBoard(){
        //this.pairs = new ArrayList<>();
        this.hm = new HashMap<HashKey, GameState>();
        this.firstGameState = new GameState();
        this.que = new ArrayDeque<>();
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
        GameState init = firstGameState;
        int iteration = 0;

        que.add(init);
        hm.put(init.getHashKey(), init); //Inserting in queue until all its neighbour vertices are marked.

        while (! que.isEmpty()){
            //Removing that vertex from queue,whose neighbour will be visited now
            GameState v = que.poll();
            iteration++;

             System.out.println("Iteration: " + iteration);
             System.out.println("Current State: " + v.getMoves());
            System.out.println(v.toString());

            if(v.isGameOver()){
                return v.getMoves();
            }

            //processing all the neighbours of v
            ArrayList<GameState> gameStates = v.getNextMoves();
            for (int i=0; i<gameStates.size(); i++){
                //is the neigbour in the hashmap
                if (! hm.containsKey(gameStates.get(i).getHashKey())){
                    this.numPaths++;
                    que.add(gameStates.get(i));
                    addToHM(gameStates.get(i));
                }
            }
        }

        return null;
    }


    /**
     * The objective of the method is to compute and return the number of shortest
     * plans that can realize the game objective for the initial game setup
     *
     * @return the number of shortest paths
     */
    public int getNumOfPaths(){
        return this.numPaths;
    }



    public void addToHM(GameState GS){
        HashKey hk = GS.getHashKey();
        hm.put(hk, GS);
    }


    /**
     * GameState class
     * used for having game state of grid and keeping track of vehicles
     */
     class GameState {
        int[] grid;
        ArrayList<Vehicle> vehicles;

        ArrayList<Pair> moves;

        public GameState() {
            this.grid = new int[36];
            this.vehicles = new ArrayList<>();
            Arrays.fill(grid, -1);
        }

        public GameState(ArrayList<Vehicle> vehicles, int id, char direcMoved, ArrayList<Pair> pairs){
            this.grid = new int[36];
            Arrays.fill(this.grid, -1);
            this.vehicles = new ArrayList<>();
            this.moves = new ArrayList<>();
            if (pairs != null)
                this.moves.addAll(pairs);
            //add cars and adjust position of moved car
            for (Vehicle car : vehicles) {
                Vehicle currCar = car.clone();
                if (currCar.getId() == id){
                    if (direcMoved == 'w'){
                        currCar.setBegin(currCar.getBegin()-1);
                        currCar.setEnd(currCar.getEnd()-1);
                    } else if (direcMoved == 'e') {
                        currCar.setBegin(currCar.getBegin()+1);
                        currCar.setEnd(currCar.getEnd()+1);
                    } else if (direcMoved == 'n') {
                        currCar.setBegin(currCar.getBegin()-6);
                        currCar.setEnd(currCar.getEnd()-6);
                    } else if (direcMoved == 's') {
                        currCar.setBegin(currCar.getBegin()+6);
                        currCar.setEnd(currCar.getEnd()+6);
                    }
                }
                if (currCar.getId() == id){
                    this.moves.add(new Pair(id, direcMoved));
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
//            System.out.println("This is a new grid");
//            System.out.println(printGrid());
        }

        public void populateGrid(String FileName) throws FileNotFoundException {
            File file = new File(FileName);
            Scanner scnr = new Scanner(file);
            int numCars = scnr.nextInt();
            scnr.nextLine();

            for (int i = 0; i < numCars; i++) {
                String line = scnr.nextLine();
                String[] nums = line.trim().split("\\s+");
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
//            System.out.println(vehicles.toString());
//            System.out.println(grid.toString());
//            System.out.println(getNextMoves().toString());
        }

        private ArrayList<GameState> getNextMoves(){
            //TODO
            ArrayList<GameState> grids = new ArrayList<>();
            //go through all vehicles to see which ones can move
            for (int i=0; i<vehicles.size(); i++){
                Vehicle currVehicle = vehicles.get(i);

                if (currVehicle.direc == 0){
                    if (currVehicle.canMoveRight() ) {
                        //System.out.println();
                        if (grid[currVehicle.getEnd()+1] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'e', this.moves));
                        }
                    } if (currVehicle.canMoveLeft()) {
                        if (grid[(currVehicle.getBegin() - 1)] == -1)
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'w', this.moves));
                    }
                } else {
                    if (currVehicle.canMoveUp()) {
                        //TODO check logic. potential bug
                        if (grid[(currVehicle.getBegin()) - 6] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 'n', this.moves));
                        }
                    }  if (currVehicle.canMoveDown() ) {
                        if (grid[(currVehicle.getEnd()) + 6] == -1) {
                            grids.add(new GameState(this.vehicles, currVehicle.getId(), 's', this.moves));
                        }
                    }
                }
            }

            return grids;
        }

        public boolean isGameOver(){
            if (vehicles.get(0).getEnd() == 17)
                return true;
            return false;
        }
        public HashKey getHashKey(){
            return new HashKey(this.grid);
        }

        public ArrayList<Pair> getMoves() {
            return moves;
        }

        public String toString(){
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


      class Vehicle{
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
                if (this.end%6 == 5){
                    return false;
                }
                return true;
            }
           return false;
        }
         public boolean canMoveLeft(){
             if (this.direc == 0){
                 if (this.begin%6 == 0){
                     return false;
                 }
                 return true;
             }
             return false;
         }
         public boolean canMoveUp(){
            if (this.direc == 1){
               if (this.begin <= 5){
                   return false;
               }
               return true;
            }
            return false;
         }
         public boolean canMoveDown(){
             if (this.direc == 1){
                 if (this.end >= 30){
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
class Pair{
    int id;
    char direction; // {’e’, ’w’, ’n’, ’s’}
    public Pair(int i, char d) { id = i; direction = d; }
    int getDirection() { return direction; }
    int getId() { return id; }
    void setDirection(char d) { direction = d; }
    void setId(int i) { id = i; }
}