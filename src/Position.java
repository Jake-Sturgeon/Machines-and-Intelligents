/**
 * Class that represents the position of an object in an array
 *
 * @version 1.0 on 12/03/2017
 * @author Jake Sturgeon
 */
public class Position {
    //instance variables
    private int x;
    private int y;

    //constructor used to store the location of and object in an array

    /**
     * Constructor that stores the z and y position of an object in an array
     * @param x The x coordinate of the object
     * @param y The x coordinate of the object
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x Set the x location
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y Set the y location
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return Return the x location
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return Return the y location
     */
    public int getY() {
        return y;
    }

    public static void main(String[] args) {
        Position position = new Position(1,1);

        System.out.println("To see if setX and getX work correctly");
        System.out.println("-------------------------");
        System.out.println("Current state of Position x = " + position.getX() + " y = " + position.getY());
        position.setX(2);
        position.setY(2);
        System.out.println("After setting the new state to (2, 2)");
        System.out.println("Current state of Position x = " + position.getX() + " y = " + position.getY());
        System.out.println("-------------------------");
        System.out.println();
    }
}


