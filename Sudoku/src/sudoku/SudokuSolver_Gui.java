package sudoku;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

//Made by Pranav Dantu. Understood backtracking algorithm from geeksforgeeks and youtube videos.
//Improvments to make: TODO- Consider using JTextArea[][] instead of JButton[][] to allow user to enter in values and check with correct solution
public class SudokuSolver_Gui implements ActionListener
{
	final int game_size = 9; //Establish a constant to avoid hardcoding
	JButton solve = new JButton(); //solve button 
	private JButton[][] cells = new JButton[game_size][game_size]; //2D array of buttons to represent the puzzle board
	//Colors
	private Color white = Color.WHITE;
	private Color red = Color.RED;
	private Color green = Color.GREEN;
	JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	
	//Initialize sudokuboard
	int[][] board = { {8, 0, 9, 0, 0, 4, 0, 0, 5}, 
            	      {0, 5, 3, 2, 0, 0, 0, 0, 8}, 
            	      {0, 0, 0, 0, 0, 1, 7, 0, 0}, 
            	      {7, 0, 0, 3, 4, 0, 8, 0, 0}, 
            	      {5, 0, 0, 0, 0, 6, 0, 3, 2}, 
            	      {0, 0, 4, 8, 0, 0, 9, 0, 0}, 
            	      {0, 6, 0, 0, 5, 7, 0, 2, 0}, 
            	      {0, 1, 0, 0, 0, 0, 0, 6, 0}, 
            	      {0, 2, 0, 4, 0, 0, 0, 0, 0} };
	
	//Constructor for gui that adds the buttons and sets the layout for the frame
	public SudokuSolver_Gui()
	{
		solve.addActionListener(this);
		fillBoard(frame,cells,board);
		solve.setText("Solve");
		frame.setTitle("Sudoku Puzzle");
		//frame.add(panel,BorderLayout.CENTER);
		frame.add(solve,BorderLayout.SOUTH);
		frame.setLayout(new GridLayout(10,10));
		frame.setSize(500,500);
		frame.setVisible(true);

		
	}
	public static void main(String[] args)
	{
		SudokuSolver_Gui gui = new SudokuSolver_Gui();
		//gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	//returns the initial board prior to any changes
	public int[][] getInitial()
	{
		return board;
	}
	
	//returns the board once it has been solved
	public int[][] getSolved()
	{
		if(solveBoard(0,0,board))
		{
			return board;
		}
		return null;
	}
	
	//Sets the buttons in the board to the appropriate values 
	public void fillBoard(JFrame frame, JButton[][] cells, int[][] arr)
	{
		for(int i = 0; i < game_size; i++)
		{
			for(int j = 0; j < game_size; j++)
			{
				cells[i][j] = new JButton();
				//cells[2][i].setBorder(BorderFactory.createMatteBorder(2,2,2,2,red));
				cells[i][j].setText(Integer.toString(board[i][j]));
				if(board[i][j] == 0)
				{
					cells[i][j].setBackground(red);
				}
				else
				{
					cells[i][j].setBackground(white);
				}
				frame.add(cells[i][j]);
				cells[i][j].addActionListener(this);
				//System.out.println("Here");
			}
		}
	}
	//ActionEvent method for when the solve button is clicked 
	//TODO: Create action event for when other buttons are clicked to input numbers that solve the puzzle
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object fire = e.getSource();
		System.out.println("Here");
		if(fire == solve)
		{
			System.out.println("Here");
			if(solveBoard(0,0,board))
			{
				resetBoard();
				updateBoard(getSolved());	
			}
			return;
		}
		
	}
	//resets the board to allow for it to be solved
	private void resetBoard()
	{
		for(int i = 0; i < game_size; i++)
		{
			for(int j = 0; j < game_size; j++)
			{
				cells[i][j].setText("");
			}
		}
	}
	//Updates the state of the board to the solved pieces, changes color of the board to green
	private void updateBoard(int[][] arr)
	{
		for(int i = 0; i < game_size; i++)
		{
			for(int j = 0; j < game_size; j++)
			{
				if(cells[i][j].getBackground() == red)
				{
					cells[i][j].setBackground(green);
				}
				cells[i][j].setText(Integer.toString(arr[i][j]));
			}
		}
	}
	
	//Solves the board
	public static boolean solveBoard(int row, int col, int[][]board)
	{
		boolean done = true;
		//TODO: This code will be necessary in the recursive case when checking from a certain position instead of the entire board each time
		if(col == board.length && row == board.length)
		{
			return true;
		}
		if(col == board.length)
		{
			row++;
			col = 0;
		}
		if(row == 9)
		{
			return true;
		}
		
		//Find Empty Square
		for(int i = row; i < board.length; i++)
		{
			for(int j = col; j < board.length; j++)
			{
				if(isEmptySquare(i,j,board)) //Made isEmptySquare method that returns a boolean 
				{
					done = false;
					row = i;
					col = j;
					break;
				}
			}
			if(!done)
			{
				break;
			}
		}
		if(done)
		{
			return true;
		}
	
			for(int i = 1;i <= board.length; i++)
			{
				if(isValidSpot(i,row,col,board))
				{
					board[row][col] = i;
					if(solveBoard(0,0,board)) //TODO: Not necessary to start from the initial position of board, can update accordingly 
					{
						return true;
					}
					else
					{
						board[row][col] = 0;
					}
				}
			 }
	
		
		return false;
	}
	public static boolean isValidSpot(int val , int row, int col, int[][] board)
	{
		//First, check if a number already exists in this square. In this case, the square is not equal to 0. 
		if(board[row][col] != 0)
		{
			return false;
		}
		//Next, check if there's an existing value in the row and column. Establisth two different variables to do this
		for(int r = 0, c = col; r  < board.length; r++)
		{
			if(board[row][r] == val || board[r][c] == val)
			{
				return false;
			}
		}
		
		//Now, check if there's an existing value in the subgrid. Logic is that the start of the row and column for a 9x9 is always (x-x%3).
		int subrStart = row - row % 3;
		int subcStart = col - col % 3;
		for(int rowS = subrStart; rowS < subrStart + 3; rowS++)
		{
			for(int colS =subcStart; colS < subcStart + 3; colS++)
			{
				if(board[rowS][colS] == val) //If the value already exists in the subgrid, then you may not input this value into the particular cell
				{
					return false;
				}
			}
		}
		return true;
	}
	
	//Checks to see if the particular square is an empty square 
	public static boolean isEmptySquare(int row, int col, int[][] board)
	{
		if(board[row][col] == 0)
		{
			return true;
		}
		return false;
	}

}