package astarsearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import astarsearch.Node;
import static java.lang.Integer.MAX_VALUE;

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
                String[] line = sc.nextLine().toUpperCase().trim().split(" ");
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

        System.out.println("final map:");
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
        Node current = start;

        int x = current.x;
        int y = current.y;
        int up = current.y + 1;
        int down = current.y - 1;
        int right = current.x + 1;
        int left = current.x - 1;

        while (!openList.isEmpty()) {

            openList.remove(openList.get(0));
            System.out.println("open empty? "+openList.isEmpty());
            closedList.add(current);
            
            if (current.equals(end)) {
                return;
            }

            //up
            try {
                System.out.println("1 "+((myArray[up][x]).equals(".") && !closedList.contains(myArray[up][x])));
                if ((myArray[up][x]).equals(".") && !closedList.contains(myArray[up][x])) {
                    int f_costUp = g_cost + heuristic(x, up, endX, endY);
                    if (f_costUp <= f_cost) {
                        current = new Node(x, up);
                        f_cost = f_costUp;
                    }
                    //myArray[up][x] = "X";
                    System.out.println("up " + f_cost);
                    if (!openList.contains(myArray[up][x])) {
                        openList.add(new Node(x, up));
                    }
                }
            } catch (Exception e) {

            }
            //down
            try {
                System.out.println("2 "+((myArray[down][x]).equals(".") && !closedList.contains(myArray[down][x])));    
                if ((myArray[down][x]).equals(".") && !closedList.contains(myArray[down][x])) {
                    int f_costDown = current.g() + heuristic(x, down, endX, endY);
                    if (f_costDown <= f_cost) {
                        current = new Node(x, down);
                        f_cost = f_costDown;
                    }
                    //myArray[down][x] = "X";
                    System.out.println("down " + f_cost);
                    if (!openList.contains(myArray[down][x])) {
                        openList.add(new Node(x, down));
                    }
                }
            } catch (Exception e) {

            }
            //right
            try {
                System.out.println("3 "+((myArray[y][right]).equals(".") && !closedList.contains(myArray[y][right])));
                if ((myArray[y][right]).equals(".") && !closedList.contains(myArray[y][right])) {
                    int f_costRight = g_cost + heuristic(right, y, endX, endY);
                    if (f_costRight <= f_cost) {
                        current = new Node(right, y);
                        f_cost = f_costRight;
                    }
                    //myArray[y][right] = "X";
                    System.out.println("right " + f_cost);
                    if (!openList.contains(myArray[y][right])) {
                        openList.add(new Node(right, y));
                    }
                }
            } catch (Exception e) {

            }
            //left
            try {
                System.out.println("4 "+((myArray[y][left]).equals(".") && !closedList.contains(myArray[y][left])));
                if ((myArray[y][left]).equals(".") && !closedList.contains(myArray[y][left])) {
                    int f_costLeft = g_cost + heuristic(left, y, endX, endY);
                    if (f_costLeft <= f_cost) {
                        current = new Node(y, left);
                        f_cost = f_costLeft;
                    }

                    //myArray[y][left] = "X";
                    System.out.println("left " + f_cost);
                    if (!openList.contains(myArray[y][left])) {
                        openList.add(new Node(y, left));
                    }
                }
            } catch (Exception e) {

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
        int count = thisLine.length();
        count = count / 2;
        System.out.println("Column: " + count);
        return count;
    }
}
