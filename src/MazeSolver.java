/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
        ArrayList<MazeCell> toFlip = new ArrayList<>();

        MazeCell currentCell = maze.getEndCell();
        while (!currentCell.equals(maze.getStartCell()))
        {
            toFlip.add(currentCell);
            currentCell = currentCell.getParent();
        }
        toFlip.add(maze.getStartCell());

        for (int i = toFlip.size()-1; i>=0;i--)
        {
            toReturn.add(toFlip.get(i));
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
        solveMazeDFSHelper(maze.getStartCell());
        return getSolution();
    }

    public void solveMazeDFSHelper(MazeCell cell)
    {
        int col = cell.getCol();
        int row = cell.getRow();
        //base case: if the current cell is the end
        if ((maze.isValidCell(row,col) && maze.getCell(row,col).equals(maze.getEndCell())))
        {
            maze.getEndCell().setParent(cell);
            return;
        }

        //recursive cases: explore north, east, south, west in that order
        if (maze.isValidCell(row-1,col))
        {
            maze.getCell(row-1,col).setParent(cell);
            maze.getCell(row-1,col).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row-1,col));
        }
        if (maze.isValidCell(row,col+1))
        {
            maze.getCell(row,col+1).setParent(cell);
            maze.getCell(row,col+1).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row,col+1));
        }
        if (maze.isValidCell(row+1,col))
        {
            maze.getCell(row+1,col).setParent(cell);
            maze.getCell(row+1,col).setExplored(true);
            solveMazeDFSHelper(maze.getCell(row+1,col));
        }
        if (maze.isValidCell(row,col-1))
        {
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

        //add squares to explore to a queue
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

        //go through the queue and explore the cells around it
        int size = toExplore.size();
        for (int i = 0; i<size;i++)
        {
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
