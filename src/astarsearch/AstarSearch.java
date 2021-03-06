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

        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Kadir\\Desktop\\map.txt")));
        int rows = countRows();
        int columns = countColumn();
        int startX = 0,startY = 0,endX = 0,endY = 0;
        
        String[][] myArray = new String[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < myArray.length; i++) {
                String[] line = sc.nextLine().toUpperCase().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    myArray[i][j] = (line[j]);
                    if (myArray[i][j].equals("S")) {
                        startX = j;
                        startY = i;
                        System.out.println("start x: " + startX + " y: " + startY);
                    }
                    if (myArray[i][j].equals("E")) {
                        endX = j;
                        endY = i;
                        System.out.println("end x: " + endX + " y: " + endY);
                    }
                }
            }
        }

        //print map
        //printMap(myArray);

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

        while (!openList.isEmpty()) {

            int x = current.getX();
            int y = current.getY();
            int up = current.getY() + 1;
            int down = current.getY() - 1;
            int right = current.getX() + 1;
            int left = current.getX() - 1;
            int f_costUp = MAX_VALUE;
            int f_costDown = MAX_VALUE;
            int f_costRight = MAX_VALUE;
            int f_costLeft = MAX_VALUE;

            printNode(openList);
            openList.remove(openList.get(0));
            //System.out.println("open empty? " + openList.isEmpty());
            if (!closedList.contains(current)) {
                closedList.add(current);
            }

            System.out.println("----------------------------");
            if (current.equals(end)) {
                return;
            }
            //System.out.println("x " + current.getX() + " y " + current.getY());
            //up
            try {
                if ((myArray[up][x]).equals(".") && !closedList.contains(myArray[up][x])) {
                    f_costUp = g_cost + heuristic(x, up, endX, endY);
                    //System.out.println("up "+f_costUp);
                    if (!openList.contains(myArray[up][x])) {
                        openList.add(current);
                    }
                }
            } catch (Exception e) {

            }

            //down
            try {
                if ((myArray[down][x]).equals(".") && !closedList.contains(myArray[down][x])) {
                    f_costDown = current.g() + heuristic(x, down, endX, endY);
                    //System.out.println("down "+f_costDown);
                    if (!openList.contains(myArray[down][x])) {
                        openList.add(current);
                    }
                }
            } catch (Exception e) {

            }

            //right
            try {
                if ((myArray[y][right]).equals(".") && !closedList.contains(myArray[y][right])) {
                    f_costRight = g_cost + heuristic(right, y, endX, endY);
                    //System.out.println("right "+f_costRight);
                    if (!openList.contains(myArray[y][right])) {
                        openList.add(current);
                    }
                }
            } catch (Exception e) {

            }

            //left
            try {
                if ((myArray[y][left]).equals(".") && !closedList.contains(myArray[y][left])) {
                    f_costLeft = g_cost + heuristic(left, y, endX, endY);
                    //System.out.println("left "+f_costLeft);
                    if (!openList.contains(myArray[y][left])) {
                        openList.add(current);
                    }
                }
            } catch (Exception e) {

            }

            //deciding which f_cost is the lowest to select the current node
            if (f_costUp <= f_cost) {
                current = new Node(x, up);
                f_cost = f_costUp;

            }
            if (f_costDown <= f_cost) {
                current = new Node(x, down);
                f_cost = f_costDown;

            }
            if (f_costRight <= f_cost) {
                current = new Node(right, y);
                f_cost = f_costRight;

            }
            if (f_costLeft <= f_cost) {
                current = new Node(left, y);
                f_cost = f_costLeft;
                    openList.add(current);
                }

            }

            //astar(current.getX(), current.getY(), endX, endY, myArray);
            myArray[current.getY()][current.getX()] = "X";
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

    public static void printNode(ArrayList<Node> list) {
        for (Node node : list) {
            System.out.println("x: " + node.getX() + " y: " + node.getY());
        }
    }
}
