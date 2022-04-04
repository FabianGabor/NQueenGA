package hu.nye.mi.nqueen.domain;

public class Board {
    private final int[][] queens;

    public Board(int size) {
        queens = new int[size][size];
    }

    public void putQueens(int[] queens) {
        for (int i = 0; i < queens.length; i++) {
            this.queens[i][queens[i]] = 1;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Board:\n");
        for (int[] queen : queens) {
            for (int j = 0; j < queens.length; j++) {
                sb.append(queen[j] > 0 ? " Q " : " * ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
