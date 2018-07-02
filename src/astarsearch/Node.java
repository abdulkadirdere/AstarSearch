package astarsearch;

import java.lang.reflect.Array;

/**
 *
 * @author Kadir
 */
public class Node {

    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int g(){
        return 1;
    }
}
