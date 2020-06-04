import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that is used to represent the current state of the puzzle
 *
 * @version 1.0 on 12/03/2017
 * @author Jake Sturgeon
 */
public class PuzzleState extends SearchState {

    //instance variables
    private int[][] puzzleState;
    private int g = 1;
    private int estRemCost;
    private String mode;
    /**
     * Constructor
     * @param puzzleState The current state of the puzzle
     * @param mode The method used to work out the estimated cost
     */
    public PuzzleState(int[][] puzzleState, String mode){
        this.mode = mode;
        this.puzzleState = puzzleState;
        this.estRemCost = getestRemCost();
    }

    /**
     * @return The current puzzles state
     */
    public int[][] getPuzzleState() {
        return puzzleState;
    }

    /**
     * @return The local cost of a move
     */
    @Override
    public int getLocalCost() {
        return this.g;
    }

    /**
     * @return The estimated remaining cost according to the mode specified
     */
    @Override
    public int getestRemCost() {
        if(mode.equals("hamming")) {
            setEstRemCost(hamming());
            return estRemCost;
        }else {
            setEstRemCost(manhattan());
            return estRemCost;
        }
    }

    /**
     * @param i set the estimated remaining cost
     */
    public void setEstRemCost(int i){
        this.estRemCost = i;
    }

    private int hamming(){
        int est = 0;
        for (int i = 0; i < puzzleState.length; i++){
            for (int j = 0; j < puzzleState[i].length; j++){
                if(RunPuzzleSearch.TARGET[i][j] != puzzleState[i][j])
                    est++;
            }
        }
        return est;
    }

    private int manhattan() {
        int est = 0;
        for (int i = 0; i < puzzleState.length; i++) {
            for (int j = 0; j < puzzleState[i].length; j++) {
                Position pos = findNumber(RunPuzzleSearch.TARGET,puzzleState[i][j]);
                est += Math.abs(i - pos.getX()) + Math.abs(j - pos.getY());
            }
        }
        return est;
    }

    private Position findNumber(int[][] puzzleState, int num){
        int i = 0;
        int j = 0;
        //loop through puzzle state to find the position of zero
        while(puzzleState[i][j] != num){
            i = (i+1) % puzzleState.length;
            if(i == puzzleState.length-1)
                j = (j+1) % puzzleState.length;
        }
        //return the position object
        return new Position(i,j);
    }

    //method used to copy the array
    private int[][] copy(int[][] array){
        //create the new array
        int[][] newArray = new int[array.length][array[0].length];
        //loop through old array and copy each element to the new array
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    /**
     * Used to check the current state against the goal state
     * @param searcher The currently used Search object
     * @return True if the current state is the target
     */
    //method to see if it has reached its goal
    boolean goalP(Search searcher) {
        PuzzleSearch pSearcher = (PuzzleSearch) searcher;
        int[][] target = pSearcher.getTarget();
        return Arrays.deepEquals(target, puzzleState);
    }

    /**
     * Used to work out all the possible the current state can take
     * @param searcher The currently used Search object
     * @return A linked list of all the possible moves that can take place
     */
    //method to add all the successors to a list
    public ArrayList<SearchState> getSuccessors(Search searcher) {
        ArrayList<PuzzleState> pslis = new ArrayList<PuzzleState>();
        ArrayList<SearchState> slis = new ArrayList<SearchState>();

        //find the position of zerp
        Position zeroPos = findNumber(puzzleState, 0);
        //zero moved left
        if(zeroPos.getY() - 1 >= 0) {
            int[][] temp = copy(puzzleState);
            temp[zeroPos.getX()][zeroPos.getY()] = puzzleState[zeroPos.getX()][zeroPos.getY() - 1];
            temp[zeroPos.getX()][zeroPos.getY() - 1] = 0;
            pslis.add(new PuzzleState(temp, this.mode));
        }
        //zero moved up
        if(zeroPos.getX() - 1 >= 0) {
            int[][] temp = copy(puzzleState);
            temp[zeroPos.getX()][zeroPos.getY()] = puzzleState[zeroPos.getX()-1][zeroPos.getY()];
            temp[zeroPos.getX()-1][zeroPos.getY()] = 0;
            pslis.add(new PuzzleState(temp, this.mode));
        }
        //zero moved down
        if(zeroPos.getX() + 1 < puzzleState.length) {
            int[][] temp = copy(puzzleState);
            temp[zeroPos.getX()][zeroPos.getY()] = puzzleState[zeroPos.getX() + 1][zeroPos.getY()];
            temp[zeroPos.getX() + 1][zeroPos.getY()] = 0;
            pslis.add(new PuzzleState(temp, this.mode));
        }
        //zero moved right
        if(zeroPos.getY() + 1 < puzzleState[0].length) {
            int[][] temp = copy(puzzleState);
            temp[zeroPos.getX()][zeroPos.getY()] = puzzleState[zeroPos.getX()][zeroPos.getY()+1];
            temp[zeroPos.getX()][zeroPos.getY()+1] = 0;
            pslis.add(new PuzzleState(temp, this.mode));
        }

        //add puzzle states to search list
        for (PuzzleState ps:pslis)
            slis.add((SearchState) ps);
        return slis;
    }
    /**
     * To see whether or not the current state is the same as another state
     * @param s2 The currently used Search State
     * @return True if it is the the two objects are the same state
     */
    //method to check if the two states are the same
    boolean sameState(SearchState s2) {
        PuzzleState puzzleState2 = (PuzzleState) s2;
        return Arrays.deepEquals(puzzleState2.getPuzzleState(), this.puzzleState);
    }

    /**
     * @return String representation of the current state
     */
    //return string of current state
    public String toString() {
        String str = "\n";
        for ( int[] p: puzzleState) {
            for (int x: p) {
                str += x + " ";
            }
            str += "\n";
        }
        return str;
    }

    /**
     * Test harness
     * @param args Arguments from the command line {not used}
     */
    //used to test the class
    public static void main(String[] args) {
        int[][] p1 = {{1, 0, 3}, {4, 2, 6}, {7, 5, 8}};
        int[][] p2 = {{4, 1, 3}, {7, 2, 5}, {0, 8, 6}};
        int[][] p3 = {{4, 1, 3}, {7, 0, 5}, {2, 8, 6}};

        int[][] p3Left = {{4, 1, 3}, {0, 7, 5}, {2, 8, 6}};
        int[][] p3Up = {{4, 0, 3}, {7, 1, 5}, {2, 8, 6}};
        int[][] p3Down = {{4, 1, 3}, {7, 8, 5}, {2, 0, 6}};
        int[][] p3Right = {{4, 1, 3}, {7, 5, 0}, {2, 8, 6}};

        final int[][] TARGET = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};


        PuzzleState state1 = new PuzzleState(p1, "");
        PuzzleState state2 = new PuzzleState(p1, "");
        PuzzleState state3 = new PuzzleState(p2, "");
        PuzzleState state5 = new PuzzleState(TARGET, "");
        PuzzleState state6 = new PuzzleState(p3, "");

        //Test Hamming and Manhattan
        System.out.println("To test getEstRemCost");
        System.out.println("-------------------------");
        int[][] test = {{7, 2, 4}, {5, 1, 8}, {3, 6, 0}};
        PuzzleState state7 = new PuzzleState(test, "");
        System.out.println("Initial Array");
        for (int i = 0; i < state6.getPuzzleState().length; i++) {
            for (int j = 0; j < state6.getPuzzleState()[i].length; j++) {
                System.out.print(state6.getPuzzleState()[i][j]);
            }
            System.out.println();
        }
        System.out.println("Hamming, 7 should be output: " + state7.hamming());
        System.out.println("Manhattan, 16 should be output: " + state7.manhattan());
        System.out.println("Therefore, if we getEstRemCost for state1 using manhattan should be 16 again: " + state7.getestRemCost());
        System.out.println("-------------------------");
        System.out.println();

        //Test getLocal cost
        System.out.println("To test getLocalCost");
        System.out.println("-------------------------");
        System.out.println("Getting the local cost of 1: " + state1.getLocalCost());
        System.out.println("-------------------------");
        System.out.println();

        //Test getSuccessors
        System.out.println("To see if getSuccessors works");
        System.out.println("-------------------------");
        System.out.println("Initial Array");
        for (int i = 0; i < state6.getPuzzleState().length; i++) {
            for (int j = 0; j < state6.getPuzzleState()[i].length; j++) {
                System.out.print(state6.getPuzzleState()[i][j]);
            }
            System.out.println();
        }
        ArrayList<SearchState> temp = state6.getSuccessors(new PuzzleSearch(p3));
        System.out.println("Seeing after moving the 0 left it makes the expected state: " + temp.get(0).sameState(new PuzzleState(p3Left, "")));
        System.out.println("Seeing after moving the 0 up it makes the expected state: " + temp.get(1).sameState(new PuzzleState(p3Up, "")));
        System.out.println("Seeing after moving the 0 down it makes the expected state: " + temp.get(2).sameState(new PuzzleState(p3Down, "")));
        System.out.println("Seeing after moving the 0 right it makes the expected state: " + temp.get(3).sameState(new PuzzleState(p3Right, "")));
        System.out.println("-------------------------");
        System.out.println();

        //Test goalP method
        System.out.println("To see if goalP works");
        System.out.println("-------------------------");
        System.out.println("Comparing state 1 with goal {should be false} = " + state1.goalP(new PuzzleSearch(TARGET)));
        System.out.println("Comparing goal with goal {should be true} = " + state5.goalP(new PuzzleSearch(TARGET)));
        System.out.println("-------------------------");
        System.out.println();

        //Test copy method
        System.out.println("To see if copy correctly copies the board");
        System.out.println("-------------------------");
        System.out.println("The board to copy");
        for (int i = 0; i < state1.getPuzzleState().length; i++) {
            for (int j = 0; j < state1.getPuzzleState()[i].length; j++) {
                System.out.print(state1.getPuzzleState()[i][j]);
            }
            System.out.println();
        }
        System.out.println("The copied board");
        int[][] copy = state1.copy(state1.getPuzzleState());
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                System.out.print(copy[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------");
        System.out.println();

        //Test find zero
        System.out.println("To see if find zero works");
        System.out.println("-------------------------");
        Position pos = state1.findNumber(state1.getPuzzleState(), 0);
        System.out.println("Finding zero in array" + state1 + "zero should be in (0, 1) (" + pos.getX() + ", " + pos.getY() + ")");
        pos = state3.findNumber(state3.getPuzzleState(),0);
        System.out.println("Finding zero in array" + state3 + "zero should be in (2, 0) (" + pos.getX() + ", " + pos.getY() + ")");
        System.out.println("-------------------------");
        System.out.println();


        //Test sameState method
        System.out.println("To see if Same state works");
        System.out.println("-------------------------");
        System.out.println("Comparing state 1 with itself {should be true} = " + state1.sameState(state1));
        System.out.println("Comparing state 1 with state 2 {should be true} = " + state1.sameState(state2));
        System.out.println("Comparing state 1 with state 3 {should be false} = " + state1.sameState(state3));
        System.out.println("-------------------------");
        System.out.println();
    }


}
