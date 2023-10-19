import java.util.ArrayList;

public class StudentPlayer extends Player {
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    static int boardSizeX = 7;
    static int boardSizeY = 6;

    @Override
    public int step(Board board) {
        ArrayList<Integer> validSteps = board.getValidSteps();

        if (validSteps.isEmpty()) {
            return -1;
        }

        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int step : validSteps) {
            Board boardCopy = new Board(board);
            boardCopy.step(playerIndex, step);

            int value = minimax(6, boardCopy, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = step;
            }
        }

        return bestMove;
    }

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
                    break;
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
                    break;
                }
            }
            return bestValue;
        }
    }

    public int calcScore(Board board) {
        int score = 0;

        for (int row = 0; row < boardSize[0]; row++) {
            for (int col = 0; col < boardSize[1]; col++) {
                int rowScore = evaluateRow(board, row, col, playerIndex);
                int colScore = evaluateColumn(board, row, col, playerIndex);
                int diagonalScore1 = evaluateDiagonalFromTopLeft(board, row, col, playerIndex);
                int diagonalScore2 = evaluateDiagonalFromBottomLeft(board, row, col, playerIndex);
                score += rowScore + colScore + diagonalScore1 + diagonalScore2;
            }
        }
        return score;
    }

    public int evaluateRow(Board board, int row, int col, int playerIndex) {
        int opponentIndex = 3 - playerIndex;

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
                if (playerCount == 4) {
                    maxScore = 1000;
                } else if (playerCount == 3) {
                    maxScore = 20;
                } else if (playerCount == 2) {
                    maxScore = 5;
                }
            } else if (playerCount == 0) {
                if (opponentCount == 4) {
                    maxScore = -1000;
                } else if (opponentCount == 3) {
                    maxScore = -20;
                } else if (opponentCount == 2) {
                    maxScore = -5;
                }
            }
        }

        return maxScore;
    }

    public int evaluateColumn(Board board, int row, int col, int playerIndex) {
        int opponentIndex = 3 - playerIndex;

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
                if (playerCount == 4) {
                    maxScore = 1000;
                } else if (playerCount == 3) {
                    maxScore = 20;
                } else if (playerCount == 2) {
                    maxScore = 5;
                }
            } else if (playerCount == 0) {
                if (opponentCount == 4) {
                    maxScore = -1000;
                } else if (opponentCount == 3) {
                    maxScore = -20;
                } else if (opponentCount == 2) {
                    maxScore = -5;
                }
            }
        }

        return maxScore;
    }

    public int evaluateDiagonalFromTopLeft(Board board, int row, int col, int playerIndex) {
        int opponentIndex = 3 - playerIndex;

        int maxScore = 0;

        for (int startRow = row, startCol = col; startRow >= 0 && startCol >= 0 && startRow >= row - nToConnect + 1 && startCol >= col - nToConnect + 1; startRow--, startCol--) {
            int endRow = Math.min(boardSizeY, startRow + nToConnect);
            int endCol = Math.min(boardSizeX, startCol + nToConnect);

            int playerCount = 0;
            int opponentCount = 0;
            int emptyCount = 0;

            for (int r = startRow, c = startCol; r < endRow && c < endCol; r++, c++) {
                if (board.getState()[r][c] == playerIndex) {
                    playerCount++;
                } else if (board.getState()[r][c] == opponentIndex) {
                    opponentCount++;
                } else {
                    emptyCount++;
                }
            }

            if (opponentCount == 0) {
                if (playerCount == 4) {
                    maxScore = 1000;
                } else if (playerCount == 3 && emptyCount == 1) {
                    maxScore = 20;
                } else if (playerCount == 2 && emptyCount == 2) {
                    maxScore = 5;
                }
            } else if (playerCount == 0) {
                if (opponentCount == 4) {
                    maxScore = -1000;
                } else if (opponentCount == 3 && emptyCount == 1) {
                    maxScore = -20;
                } else if (opponentCount == 2 && emptyCount == 2) {
                    maxScore = -5;
                }
            }
        }

        return maxScore;
    }

    public int evaluateDiagonalFromBottomLeft(Board board, int row, int col, int playerIndex) {
        int opponentIndex = 3 - playerIndex;

        int maxScore = 0;

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
                if (playerCount == 4) {
                    maxScore = 1000;
                } else if (playerCount == 3) {
                    maxScore = 20;
                } else if (playerCount == 2) {
                    maxScore = 5;
                }
            } else if (playerCount == 0) {
                if (opponentCount == 4) {
                    maxScore = -1000;
                } else if (opponentCount == 3) {
                    maxScore = -20;
                } else if (opponentCount == 2) {
                    maxScore = -5;
                }
            }
        }
        return maxScore;
    }
}
