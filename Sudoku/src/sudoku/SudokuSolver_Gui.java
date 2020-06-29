package sudoku;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

//Made by Pranav Dantu. Understood backtracking algorithm from geeksforgeeks and youtube videos.
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
	int[][] board = { {3, 0, 6, 5, 0, 8, 4, 0, 0}, 
            {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
		      {0, 8, 7, 0, 0, 0, 0, 3, 1}, 
		      {0, 0, 3, 0, 1, 0, 0, 8, 0}, 
		      {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
		      {0, 5, 0, 0, 9, 0, 6, 0, 0}, 
		      {1, 3, 0, 0, 0, 0, 2, 5, 0}, 
		      {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
		      {0, 0, 5, 2, 0, 6, 3, 0, 0} };
	
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
	public int[][] getInitial()
	{
		return board;
	}
	
	
	public int[][] getSolved()
	{
		if(solveBoard(0,0,board))
		{
			return board;
		}
		return null;
	}
	
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
	
	
	public static boolean solveBoard(int row, int col, int[][]board)
	{
		int count = 0;
		boolean done = true;
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
				if(isEmptySquare(i,j,board))
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
					if(solveBoard(0,0,board))
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
		//Next, check if there's an existing value in the row and column
		for(int r = 0, c = col; r  < board.length; r++)
		{
			if(board[row][r] == val || board[r][c] == val)
			{
				return false;
			}
		}
		//Now, check if there's an existing value in the subgrid
		int subrStart = row - row % 3;
		int subcStart = col - col % 3;
		for(int rowS = subrStart; rowS < subrStart + 3; rowS++)
		{
			for(int colS =subcStart; colS < subcStart + 3; colS++)
			{
				if(board[rowS][colS] == val)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static boolean isEmptySquare(int row, int col, int[][] board)
	{
		if(board[row][col] == 0)
		{
			return true;
		}
		return false;
	}

}