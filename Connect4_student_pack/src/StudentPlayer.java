import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<Integer> validSteps = board.getValidSteps();
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int step : validSteps) {
            Board boardCopy = new Board(board);
            boardCopy.step(playerIndex, step);

            int value = minimax(4, boardCopy, alpha, beta, false);

            if (value > bestValue) {
                bestValue = value;
                bestMove = step;
            }
            alpha = Math.max(alpha, bestValue);

            if (beta <= alpha) {
                break; // Átbéta vágás
            }
        }

        return bestMove;
    }

    // Minimax algorithm with alpha-beta pruning
    public int minimax(int depth, Board board, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || board.gameEnded()) {
            return calcScore(board);
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (int step : board.getValidSteps()) {
                Board boardCopy = new Board(board);
                boardCopy.step(playerIndex, step);

                int value = minimax(depth - 1, boardCopy, alpha, beta, false);
                bestValue = Math.max(bestValue, value);
                alpha = Math.max(alpha, bestValue);

                if (beta <= alpha) {
                    break; // Átbéta vágás
                }
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int step : board.getValidSteps()) {
                Board boardCopy = new Board(board);
                boardCopy.step(3 - playerIndex, step);

                int value = minimax(depth - 1, boardCopy, alpha, beta, true);
                bestValue = Math.min(bestValue, value);
                beta = Math.min(beta, bestValue);

                if (beta <= alpha) {
                    break; // Átbéta vágás
                }
            }
            return bestValue;
        }
    }

    public int calcScore(Board board) {
        int playerIndex = this.playerIndex; // Játékos indexe (1 vagy 2)

        int score = 0;

        // Ellenőrizd a sorokat, oszlopokat és átlókat
        for (int row = 0; row < boardSize[0]; row++) {
            for (int col = 0; col < boardSize[1]; col++) {
                int rowScore = evaluateRow(board, row, col, playerIndex);
                int colScore = evaluateColumn(board, row, col, playerIndex);
                int diagScore = evaluateDiagonal(board, row, col, playerIndex);

                // Súlyok finomhangolása
                score +=  rowScore + colScore +  diagScore;
            }
        }
        return score;
    }

    public int evaluateRow(Board board, int row, int col, int playerIndex) {
        int boardSizeX = 7;
        int opponentIndex = 3 - playerIndex; // Az ellenfél indexe (1 vagy 2)

        int maxScore = 0;

        for (int startCol = Math.max(0, col - nToConnect + 1); startCol <= col; startCol++) {
            int endCol = Math.min(boardSizeX, startCol + nToConnect);

            int playerCount = 0;
            int opponentCount = 0;

            for (int c = startCol; c < endCol; c++) {
                if (board.getState()[row][c] == playerIndex) {
                    playerCount++;
                } else if (board.getState()[row][c] == opponentIndex) {
                    opponentCount++;
                }
            }

            if (opponentCount == 0) {
                // Nincs ellenfél korong a sorban
                if (playerCount == nToConnect) {
                    // Játékos nyert
                    return MAX; // Maximum érték a nyerésért
                } else {
                    // Pontokat adunk az összefüggő saját korongokért
                    maxScore = Math.max(maxScore, playerCount * 10); // Finomhangolás itt
                }
            } else if (playerCount == 0) {
                // Nincs saját korong a sorban
                if (opponentCount == nToConnect) {
                    // Az ellenfél nyert
                    return -MAX; // Minimum érték az ellenfél nyeréséért
                }
                // Pontokat adunk az összefüggő ellenfél korongokért
                maxScore = Math.max(maxScore, opponentCount * 10); // Finomhangolás itt
            }
        }
        return maxScore;
    }

    public int evaluateColumn(Board board, int row, int col, int playerIndex) {
        int boardSizeY = 6;
        int opponentIndex = 3 - playerIndex; // Az ellenfél indexe (1 vagy 2)

        int maxScore = 0;

        for (int startRow = Math.max(0, row - nToConnect + 1); startRow <= row; startRow++) {
            int endRow = Math.min(boardSizeY, startRow + nToConnect);

            int playerCount = 0;
            int opponentCount = 0;

            for (int r = startRow; r < endRow; r++) {
                if (board.getState()[r][col] == playerIndex) {
                    playerCount++;
                } else if (board.getState()[r][col] == opponentIndex) {
                    opponentCount++;
                }
            }

            if (opponentCount == 0) {
                // Nincs ellenfél korong az oszlopban
                if (playerCount == nToConnect) {
                    // Játékos nyert
                    return MAX; // Maximum érték a nyerésért
                } else {
                    // Pontokat adunk az összefüggő saját korongokért
                    maxScore = Math.max(maxScore, playerCount * 10); // Finomhangolás itt
                }
            } else if (playerCount == 0) {
                // Nincs saját korong az oszlopban
                if (opponentCount == nToConnect) {
                    // Az ellenfél nyert
                    return -MAX; // Minimum érték az ellenfél nyeréséért
                }
                // Pontokat adunk az összefüggő ellenfél korongokért
                maxScore = Math.max(maxScore, opponentCount * 10); // Finomhangolás itt
            }
        }

        return maxScore;
    }

    public int evaluateDiagonal(Board board, int row, int col, int playerIndex) {
        int boardSizeX = 7;
        int boardSizeY = 6;
        int opponentIndex = 3 - playerIndex; // Az ellenfél indexe (1 vagy 2)

        int maxScore = 0;

        // Átlók értékelése bal felsőtől jobb alsóig
        for (int startRow = row, startCol = col; startRow >= 0 && startCol >= 0 && startRow >= row - nToConnect + 1 && startCol >= col - nToConnect + 1; startRow--, startCol--) {
            int endRow = Math.min(boardSizeY, startRow + nToConnect);
            int endCol = Math.min(boardSizeX, startCol + nToConnect);

            int playerCount = 0;
            int opponentCount = 0;

            for (int r = startRow, c = startCol; r < endRow && c < endCol; r++, c++) {
                if (board.getState()[r][c] == playerIndex) {
                    playerCount++;
                } else if (board.getState()[r][c] == opponentIndex) {
                    opponentCount++;
                }
            }

            if (opponentCount == 0) {
                if (playerCount == nToConnect) {
                    // Játékos nyert
                    return MAX; // Maximum érték a nyerésért
                } else {
                    // Pontokat adunk az összefüggő saját korongokért
                    maxScore = Math.max(maxScore, playerCount * 10); // Finomhangolás itt
                }
            } else if (playerCount == 0) {
                if (opponentCount == nToConnect) {
                    // Az ellenfél nyert
                    return -MAX; // Minimum érték az ellenfél nyeréséért
                }
                // Pontokat adunk az összefüggő ellenfél korongokért
                maxScore = Math.max(maxScore, opponentCount * 10); // Finomhangolás itt
            }
        }

        // Átlók értékelése bal alsótól jobb felsőig
        for (int startRow = row, startCol = col; startRow >= 0 && startCol < boardSizeX && startRow >= row - nToConnect + 1 && startCol <= col + nToConnect - 1; startRow--, startCol++) {
            int endRow = Math.min(boardSizeY, startRow + nToConnect);
            int endCol = Math.max(0, startCol - nToConnect + 1);

            int playerCount = 0;
            int opponentCount = 0;

            for (int r = startRow, c = startCol; r < endRow && c >= endCol; r++, c--) {
                if (board.getState()[r][c] == playerIndex) {
                    playerCount++;
                } else if (board.getState()[r][c] == opponentIndex) {
                    opponentCount++;
                }
            }

            if (opponentCount == 0) {
                if (playerCount == nToConnect) {
                    // Játékos nyert
                    return MAX; // Maximum érték a nyerésért
                } else {
                    // Pontokat adunk az összefüggő saját korongokért
                    maxScore = Math.max(maxScore, playerCount * 10); // Finomhangolás itt
                }
            } else if (playerCount == 0) {
                if (opponentCount == nToConnect) {
                    // Az ellenfél nyert
                    return -MAX; // Minimum érték az ellenfél nyeréséért
                }
                // Pontokat adunk az összefüggő ellenfél korongokért
                maxScore = Math.max(maxScore, opponentCount * 10); // Finomhangolás itt
            }
        }

        return maxScore;
    }

}
