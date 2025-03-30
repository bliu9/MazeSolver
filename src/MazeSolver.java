/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        ArrayList<MazeCell> toReturn = new ArrayList<MazeCell>();
        Stack<MazeCell> toFlip = new Stack<>();

        MazeCell currentCell = maze.getEndCell();

        //Adds all the cells in the solution to the toFlip stack in end to start order
        while (!currentCell.equals(maze.getStartCell()))
        {
            //Add the current cell to the list of cells in the solution
            toFlip.push(currentCell);
            //Update the current cell to the parent of the current cell
            currentCell = currentCell.getParent();
        }
        toFlip.push(maze.getStartCell());

        //Flips the order of the toFlip arraylist by popping each element into the toReturn arraylist
        while (!toFlip.isEmpty())
        {
            toReturn.add(toFlip.pop());
        }

        return toReturn;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Call helper function to solve maze
        solveMazeDFSHelper(maze.getStartCell());
        return getSolution();
    }

    public void solveMazeDFSHelper(MazeCell cell)
    {
        int col = cell.getCol();
        int row = cell.getRow();
        //Base case: if the current cell is the end
        if ((maze.isValidCell(row,col) && maze.getCell(row,col).equals(maze.getEndCell())))
        {
            maze.getEndCell().setParent(cell);
            return;
        }

        //Recursive cases: explore north, east, south, west in that order
        //explore north cell
        if (maze.isValidCell(row-1,col))
        {
            //Set parent and explore status before moving on to next cell
            maze.getCell(row-1,col).setParent(cell);
            maze.getCell(row-1,col).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row-1,col));
        }
        //explore east cell
        if (maze.isValidCell(row,col+1))
        {
            //Set parent and explore status before moving on to next cell
            maze.getCell(row,col+1).setParent(cell);
            maze.getCell(row,col+1).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row,col+1));
        }
        //explore south cell
        if (maze.isValidCell(row+1,col))
        {
            //Set parent and explore status before moving on to next cell
            maze.getCell(row+1,col).setParent(cell);
            maze.getCell(row+1,col).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row+1,col));
        }
        //explore west cell
        if (maze.isValidCell(row,col-1))
        {
            //Set parent and explore status before moving on to next cell
            maze.getCell(row,col-1).setParent(cell);
            maze.getCell(row,col-1).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row,col-1));
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Call function to solve the maze
        solveMazeBFSHelper(maze.getStartCell());
        return getSolution();
    }

    public void solveMazeBFSHelper(MazeCell cell)
    {
        int row = cell.getRow();
        int col = cell.getCol();
        //base case: if the end cell is the same as the cell you're currently looking at
        if (cell.equals(maze.getEndCell()))
        {
            return;
        }

        //Add squares to explore to a queue (in north, east, south, west order)
        Queue<MazeCell> toExplore = new LinkedList<>();
        if (maze.isValidCell(row-1,col))
        {
            toExplore.add(maze.getCell(row-1,col));
        }
        if (maze.isValidCell(row,col+1))
        {
            toExplore.add(maze.getCell(row,col+1));
        }
        if (maze.isValidCell(row+1,col))
        {
            toExplore.add(maze.getCell(row+1,col));
        }
        if (maze.isValidCell(row,col-1))
        {
            toExplore.add(maze.getCell(row,col-1));
        }

        //Go through each cell the queue and explore the cells around it
        int size = toExplore.size();
        for (int i = 0; i<size;i++)
        {
            //Set parent and explored statuses before moving on to the next cell
            toExplore.peek().setParent(cell);
            toExplore.peek().setExplored(true);
            solveMazeBFSHelper(toExplore.remove());
        }

    }


    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
