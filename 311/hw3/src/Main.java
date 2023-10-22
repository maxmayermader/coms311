import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        GameBoard gb = new GameBoard();

        gb.readInput("src/boards/1.txt");
//        gb.readInput(args[0]);
        ArrayList<Pair> path = gb.getPlan();
        for (int i=0; i<path.size(); i++)
            System.out.println(path.get(i).id + " " + path.get(i).direction);
        System.out.println(gb.getNumOfPaths());


    }
}