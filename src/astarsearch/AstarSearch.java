package astarsearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import astarsearch.Node;

/**
 *
 * @author Kadir
 */
public class AstarSearch {

    public static void main(String[] args) throws IOException {
        //input map file
        //File file = new File("C:\\Users\\A232838\\Desktop\\map.txt");

        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Kadir\\Desktop\\map.txt")));
        int rows = countRows();
        int columns = countColumn();
        String[][] myArray = new String[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < myArray.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    myArray[i][j] = (line[j]);
                }
            }
        }
        int[][] myTemp = new int[rows][columns];
        for (int k = 0; k < rows; k++) {
            for (int l = 0; l < columns; l++) {
                if ((myArray[k][l]).equals(".")) {
                    myTemp[k][l] = 0;
                } else if ((myArray[k][l]).equals("W")) {
                    myTemp[k][l] = 2;
                } else if ((myArray[k][l]).equals("w")) {
                    myTemp[k][l] = 2;
                } else if ((myArray[k][l]).equals("S")) {
                    myTemp[k][l] = 1;
                } else if ((myArray[k][l]).equals("E")) {
                    myTemp[k][l] = 1;
                }
            }
        }

        //System.out.println(Arrays.deepToString(myTemp).replace("], ", "]\n"));
        //print map
        //printMap(myArray);
        //System.out.println("start: "+myArray[4][0]);
        int startX = 0;
        int startY = 4;
        int endX = 19;
        int endY = 4;

        astar(startX, startY, endX, endY, myArray);

        //System.out.println("final map:");
        printMap(myArray);
    }

    public static void astar(int startX, int startY, int endX, int endY, String[][] myArray) {
        Node start = new Node(startX, startY);
        Node end = new Node(endX, endY);

        int f_cost = 0;
        int g_cost = 1;
        int h_cost = heuristic(startX, startY, endX, endY);
        f_cost = g_cost + h_cost;

        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = start;
            
            openList.remove(start);
            closedList.add(current);
            
            int x = current.x;
            int y = current.y;
            int up = current.y + 1;
            int down = current.y - 1;
            int right = current.x + 1;
            int left = current.x - 1;


            if (current.equals(end)) {
                return;
            }
            
            //up
            if ((myArray[up][x]).equals(".")) {
                f_cost = g_cost + heuristic(x,up,endX,endY);
                myArray[up][x] = "X";
                System.out.println("up "+f_cost);
            }
            //down
            if ((myArray[down][x]).equals(".")) {
                f_cost = g_cost + heuristic(x,down,endX,endY);
                myArray[down][x] = "X";
                System.out.println("down "+f_cost);
            }
            //right
            if ((myArray[y][right]).equals(".")) {
                 f_cost = g_cost + heuristic(right,y,endX,endY);
                myArray[y][right] = "X";
                System.out.println("right "+f_cost);
            }
            //left
            if ((myArray[y][left]).equals(".")) {
                 f_cost = g_cost + heuristic(left,y,endX,endY);
                myArray[y][left] = "X";
                System.out.println("left "+f_cost);
            }

        }
    }

    //method to calcualte heuristic cost: Manhattan distance
    public static int heuristic(int startX, int startY, int endX, int endY) {
        int dist = Math.abs(endX - startX) + Math.abs(endY - startY);
        return dist;
    }

    public static void printMap(String[][] board) {
        System.out.println(Arrays.deepToString(board).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + "\n");
    }

    public static int countRows() throws FileNotFoundException, IOException {
        int rows = 0;
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kadir\\Desktop\\map.txt"));
        while (br.readLine() != null) {
            rows++;
        }
        System.out.println("Rows: " + rows);
        return rows;
    }

    public static int countColumn() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kadir\\Desktop\\map.txt"));
        String thisLine = br.readLine();
        int count = 0;
        for (int i = 0; i < thisLine.length(); i++) {
            count++;
        }
        count = count / 2;
        System.out.println("Column: " + count);
        return count;
    }
}
