package sudoku;
import java.util.*;

//Task: Solve Sudoku puzzle using Backtracking Algorithm

public class SudokuSolver 
{
	public static void main(String[] args)
	{
		int[][] board = { {3, 0, 6, 5, 0, 8, 4, 0, 0}, 
		                  {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
					      {0, 8, 7, 0, 0, 0, 0, 3, 1}, 
					      {0, 0, 3, 0, 1, 0, 0, 8, 0}, 
					      {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
					      {0, 5, 0, 0, 9, 0, 6, 0, 0}, 
					      {1, 3, 0, 0, 0, 0, 2, 5, 0}, 
					      {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
					      {0, 0, 5, 2, 0, 6, 3, 0, 0} };
		//System.out.println(Arrays.deepToString(solveBoard(0,0,board)));
		print(0,0,board);
		
		System.out.println(solveBoard(0,0,board));
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
		//First, check if a number already exists in this square.In this case, the square is not equal to 0. 
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
		int subrStart = row - row % (int)Math.sqrt(board.length);
		int subcStart = col - col % (int)Math.sqrt(board.length);
		for(int rowS = subrStart; rowS < subrStart + (int)Math.sqrt(board.length); rowS++)
		{
			for(int colS =subcStart; colS < subcStart + (int)Math.sqrt(board.length); colS++)
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
	public static void print(int row, int col, int[][] board) 
	{ 
	 if(solveBoard(0,0,board))
	 {
        for (int r = 0; r < 9; r++) { 
            for (int d = 0; d < 9; d++) { 
                System.out.print(board[r][d]); 
                System.out.print(" "); 
            } 
            System.out.print("\n"); 
  
            if ((r + 1) % (int)Math.sqrt(9) == 0) { 
                System.out.print(""); 
            } 
        } 
	     
	  }
	else
	{
		System.out.println("No Solution");
	}
	}
}