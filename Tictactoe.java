
import java.util.Scanner;

public class Tictactoe
{

    //just started hard mode and realized I coulda made the entire thing with a 1d array scREEE
    static char q[][] =
    {
        {
            ' ', ' ', ' '
        },
        {
            ' ', ' ', ' '
        },
        {
            ' ', ' ', ' '
        }
    };
    static Scanner conso = new Scanner(System.in);
    static boolean turn = true;
    static int moveNum = 0;
    static boolean won = false;
    static int nextRow = -1;
    static int nextCol = -1;
    static int gamemode = -1;
    static String winInput;

    public static void main(String[] args)
    {

        gamemode = beginPrint();
        printBoard();
        if (gamemode == 0)
        {
            while (!gameOver())
                gameMode0();
        }
        else if (gamemode == 1 || gamemode == 2)
        {
            while (!gameOver())
                gameMode1n2();
        }
    }

    public static void gameMode1n2()
    {
        if (turn)
        {
            System.out.print("Your Move: ");
            String inp = conso.nextLine();
            while (!putSmth(inp))
            {
                System.out.print("Your Move: ");
                inp = conso.nextLine();
            }
        }
        else
            putSmthAI();

    }

    public static boolean gameOver()
    {
        if (moveNum == 9)
        {
            System.out.println("It's a tie!");
            return true;
        }
        if (won)
        {
            String winner;
            if (gamemode == 0)
                winner = turn ? "Player A!" : "Player B!";
            else
                winner = turn ? "You!" : "The AI...";
            System.out.println("The Winner is: " + winner);
            return true;
        }

        return false;

    }

    static int evaluate(char b[][])
    {
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1]
                    && b[row][1] == b[row][2])
            {
                if (b[row][0] == 'X')
                    return +10;
                else if (b[row][0] == 'O')
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col]
                    && b[1][col] == b[2][col])
            {
                if (b[0][col] == 'X')
                    return +10;

                else if (b[0][col] == 'O')
                    return -10;
            }
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == 'X')
                return +10;
            else if (b[0][0] == 'O')
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == 'X')
                return +10;
            else if (b[0][2] == 'O')
                return -10;
        }

        return 0;
    }

    static Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return true;
        return false;
    }

    static int minimax(char board[][],int depth, Boolean isMax)
    {
        int score = evaluate(board);

        
        if (score == 10)
            return score;

        if (score == -10)
            return score;

        if (isMovesLeft(board) == false)
            return 0;

        if (isMax)
        {
            int best = -1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j] == ' ')
                    {
                        board[i][j] = 'X';

                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }

        else
        {
            int best = 1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j] == ' ')
                    {
                        board[i][j] = 'O';

                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));

                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }


    static String findBestMove(char board[][])
    {
        int bestVal = -1000;
        String bestMove = "";


        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == ' ')
                {
                    board[i][j] = 'X';


                    int moveVal = minimax(board, 0, false);

                    board[i][j] = ' ';

                    if (moveVal > bestVal)
                    {
                        bestMove = "" + i + j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    public static void putSmthAI()
    {
        int row, col;
        if (gamemode == 2)
        {
            String inp = findBestMove(q);
            row = Integer.parseInt("" + inp.charAt(0));
            col = Integer.parseInt("" + inp.charAt(1));
        }
        else
        {
            row = -1;
            col = -1;
            if (nextRow != -1 && nextCol != -1)
            {
                row = nextRow;
                col = nextCol;
                if (q[row][col] != ' ')
                {
                    nextRow = -1;
                    nextCol = -1;
                }
            }
            if (nextRow == -1 || nextCol == -1)
            {
                row = (int) (Math.random() * 3);
                col = (int) (Math.random() * 3);
                while (q[row][col] != ' ')
                {
                    row = (int) (Math.random() * 3);
                    col = (int) (Math.random() * 3);
                }
            }
        }
        System.out.println("The AI's Move: " + (char)(row+65) + " "+ (col +1));
        q[row][col] = turn ? 'O' : 'X';
        printBoard();
        winInput = "" + row + col;
        if (winCheck())
        {
            won = true;
            return;
        }
        turn = !turn;
        moveNum++;
        nextRow = -1;
        nextCol = -1;
    }

    public static boolean winCheck()
    {
        int row, col;
        if (validInput(winInput))//user input
        {
            row = getRow(winInput);
            col = getCol(winInput);
        }
        else//bot input
        {
            row = Integer.parseInt("" + winInput.charAt(0));
            col = Integer.parseInt("" + winInput.charAt(1));

        }
        int rowC = 0;
        int colC = 0;
        int diagPC = 0;
        int diagNC = 0;
        for (int i = 1; i < 3; i++)
        {
            if (q[(row + i) % 3][col] == (turn ? 'O' : 'X'))
            {
                if (gamemode == 1)
                {
                    nextRow = i == 1 ? ((row + i + 1) % 3) : ((row + i - 1) % 3);
                    nextCol = col;
                    if(q[nextRow][nextCol] != ' ')
                    {
                        nextRow = -1;
                        nextCol = -1;
                    }
                }
                colC++;
            }
            if (q[row][(col + i) % 3] == (turn ? 'O' : 'X'))
            {
                if (gamemode == 1)
                {
                    nextCol = i == 1 ? ((col + i + 1) % 3) : ((col + i - 1) % 3);
                    nextRow = row;
                    if(q[nextRow][nextCol] != ' ')
                    {
                        nextRow = -1;
                        nextCol = -1;
                    }
                }
                rowC++;
            }
            if (q[(row + i) % 3][(col + i) % 3] == (turn ? 'O' : 'X'))
            {
                int currRow = (row + i) % 3;
                int currCol = (col + i) % 3;
                if (gamemode == 1)
                {
                    nextRow = i == 1 ? ((row + i + 1) % 3) : ((row + i - 1) % 3);
                    nextCol = i == 1 ? ((col + i + 1) % 3) : ((col + i - 1) % 3);
                    if(q[nextRow][nextCol] != ' ')
                    {
                        nextRow = -1;
                        nextCol = -1;
                    }
                }
                if (!(currRow == 0 && currCol == 1) && !(currRow == 2 && currCol == 1) && !(currRow == 1 && currCol == 0) && !(currRow == 1 && currCol == 2))
                    diagNC++;
            }
            if (q[Math.abs((row - i)) % 3][Math.abs((col - i)) % 3] == (turn ? 'O' : 'X'))
            {
                int currRow = Math.abs((row - i)) % 3;
                int currCol = Math.abs((col - i)) % 3;
                if (currRow != row && currCol != col)
                {
                    if (gamemode == 1)
                    {
                        nextRow = i == 1 ? (Math.abs(row - i - 1) % 3) : ((row + i + 1) % 3);
                        nextCol = i == 1 ? (Math.abs(col - i - 1) % 3) : ((col + i + 1) % 3);
                        if(q[nextRow][nextCol] != ' ')
                    {
                        nextRow = -1;
                        nextCol = -1;
                    }
                    }
                    if (!(currRow == 0 && currCol == 1) && !(currRow == 2 && currCol == 1) && !(currRow == 1 && currCol == 0) && !(currRow == 1 && currCol == 2))
                        diagPC++;
                }
            }

        }
        if (rowC == 2 || diagPC == 2 || colC == 2 || diagNC == 2)
        {
            return true;
        }
        return false;
    }

    public static boolean putSmth(String input)
    {
        int col = -1, row = -1;
        if (validInput(input))
        {
            col = getCol(input);
            row = getRow(input);
        }
        else
        {
            System.out.println("Please enter a valid input (Letter_Number)");
            return false;
        }
        if (q[row][col] == ' ')//no prior inputs
        {
            q[row][col] = turn ? 'O' : 'X';
            printBoard();
            winInput = input;
            if (winCheck())
            {
                won = true;
                return true;
            }
            turn = !turn;
            moveNum++;
            return true;
        }
        else
        {
            System.out.println("There is already something there please choose another placement.");
            return false;
        }
    }

    public static boolean validInput(String input)
    {
        if (input.length() == 3)
        {
            int col = getCol(input);
            int row = getRow(input);
            if (col >= 0 && col <= 2 && row >= 0 && row <= 2)
                return true;
        }
        return false;
    }

    public static int getCol(String inp)
    {
        return Integer.parseInt("" + inp.charAt(2)) - 1;
    }

    public static int getRow(String inp)
    {
        char curr = inp.charAt(0);
        if (curr > 95)
            return (int) curr - 97;
        else
            return (int) curr - 65;
    }

    public static void gameMode0()
    {
        if (turn)
            System.out.print("Player A's Move: ");
        else
            System.out.print("Player B's Move: ");

        String inp = conso.nextLine();
        while (!putSmth(inp))
        {
            if (turn)
                System.out.print("Player A's Move: ");
            else
                System.out.print("Player B's Move: ");
            inp = conso.nextLine();
        }
    }

    public static void printBoard()
    {
        int let = 65;
        System.out.println("  | 1 ---- 2 ---- 3 |");
        System.out.println("---------------------");
        for (int i = 0; i < q.length; i++)
        {
            System.out.print((char) let);
            for (int j = 0; j < q[i].length; j++)
            {
                System.out.print(" | " + q[i][j] + " | ");

            }
            System.out.println("");
            System.out.println("---------------------");
            let++;
        }
        System.out.println("");
    }

    public static int beginPrint()
    {
        System.out.println("Welcome to Tic-Tac-No");
        System.out.println("Choose a Gamemode:");
        System.out.println("\t0: player vs player\n"
                + "\t1: player vs easy bot\n"
                + "\t2: player vs hard bot");
        System.out.print("Select: ");
        int gamemode = conso.nextInt();
        System.out.println("\nYou've Chosen: Gamemode " + gamemode);
        System.out.println("---------------------\n");
        conso.nextLine();
        return gamemode;
    }
}
//nts makes it look prettier
