package net.joelpena.tictactoc;

/**
 * Created by Joel on 2/10/2017.
 */

public enum Piece {
    RED (1),
    YELLOW (2);

    private int value;

    private Piece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
