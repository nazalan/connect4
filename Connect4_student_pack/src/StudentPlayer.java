import static java.lang.Math.max;
import static java.lang.Math.min;

public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }


    static int MAX = 1000;
    static int MIN = -1000;

    static int four=4;

    @Override
    public int step(Board board) {
        int[] values=calcCol(board);
        int value=minimax(0, 0, true, values, MIN, MAX);

        return 0;
    }
    public static int findIndex(int arr[], int t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

    public int minimax(int depth, int nodeIndex, Boolean maximizingPlayer, int values[], int alpha, int beta){
        if (depth == 3)
            return values[nodeIndex];

        if (maximizingPlayer)
        {
            int best = MIN;
            for (int i = 0; i < 2; i++)
            {
                int val = minimax(depth + 1, nodeIndex * 2 + i,
                        false, values, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                if (beta <= alpha)
                    break;
            }
            return best;
        }
        else
        {
            int best = MAX;
            for (int i = 0; i < 2; i++)
            {

                int val = minimax(depth + 1, nodeIndex * 2 + i,
                        true, values, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }

    public int calcScore()
    {
        return 0;
    }

    private int[] calcCol(Board board) {
        int sum[] = {50, 50, 50, 50, 50, 50, 50};
        for(int col = 0; col < 7; col++) {
            for (int row=0; row<6; row++) {
                if (board.getState()[row][col] == 2) {
                    sum[col]++;
                }
                else if (board.getState()[row][col] == 1) {
                    sum[col]--;
                }
            }
            System.out.println("Oszlop: " + col+ " eretek: "+sum[col]);
        }
        return sum;
    }
//
//    private boolean CalcNDialog(int row, int col, int playerIndex) {
//        int nInADiagonal = 0;
//
//        int stepLeftUp = min(nToConnect - 1, min(row, col));
//        int stepRightDown = min(nToConnect, min(boardSize[0] - row, boardSize[1] - col));
//
//        if ((stepLeftUp + stepRightDown) < nToConnect)
//            return false;
//
//        for (int diagonalStep = -stepLeftUp; diagonalStep < stepRightDown; diagonalStep++) {
//            if (state[row + diagonalStep][col + diagonalStep] == playerIndex) {
//                nInADiagonal++;
//                if (nInADiagonal == nToConnect) {
//                    winner = playerIndex;
//                    return true;
//                }
//            } else {
//                nInADiagonal = 0;
//            }
//        }
//        return false;
//    }
//
//    private boolean calcDialog(int row, int col, int playerIndex) {
//        int nInASkewDiagonal = 0;
//
//        int stepLeftDown = min(nToConnect - 1, min(boardSize[0] - row - 1, col));
//        int stepRightUp = min(nToConnect, min(row + 1, boardSize[1] - col));
//
//        if ((stepRightUp + stepLeftDown) < nToConnect)
//            return false;
//
//        for (int skewDiagonalStep = -stepLeftDown; skewDiagonalStep < stepRightUp; skewDiagonalStep++) {
//            if (state[row - skewDiagonalStep][col + skewDiagonalStep] == playerIndex) {
//                nInASkewDiagonal++;
//                if (nInASkewDiagonal == nToConnect) {
//                    winner = playerIndex;
//                    return true;
//                }
//            } else
//                nInASkewDiagonal = 0;
//        }
//        return false;
//    }

}
