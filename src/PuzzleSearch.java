

/**
 * Created by Jake on 27/02/2017.
 */
public class PuzzleSearch extends Search {

    //instance variables
    private int[][] target;

    //constructor
    /**
     * Constructor
     * @param target The target state
     */
    public PuzzleSearch(int[][] target){
        this.target = target;
    }

    /**
     * @return The target state
     */
    //getter for target
    public int[][] getTarget() {
        return target;
    }

    /**
     * Test harness
     * @param args Arguments from the command line {not used}
     */
    //The test harness
    public static void main(String[] args){
        final int[][] TARGET = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        PuzzleSearch search = new PuzzleSearch(TARGET);

        System.out.println("To see if getTarget work correctly");
        System.out.println("-------------------------");
        System.out.println("The actual target board");
        for (int i = 0; i < TARGET.length; i++) {
            for (int j = 0; j < TARGET[i].length; j++) {
                System.out.print(TARGET[i][j]);
            }
            System.out.println();
        }
        System.out.println("The getTarget board");
        for (int i = 0; i < search.getTarget().length; i++) {
            for (int j = 0; j < search.getTarget()[i].length; j++) {
                System.out.print(search.getTarget()[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------");
        System.out.println();
    }

}
