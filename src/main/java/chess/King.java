package chess;

public class King extends ChessPiece {
    public King(String color) {
        super(color);
    }

    @Override
    String getColor() {
        return color;
    }

    @Override
    Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // standard check
        if( !stdMoveCheck(chessBoard, line, column, toLine, toColumn) ) return false;
        // check "nearby" move
        if( !((Math.abs(line - toLine) < 2) && (Math.abs(column - toColumn) < 2)) ) return false;
        // no need to check the way
        return true;
    }

    Boolean isUnderAttack(ChessBoard board, int line, int column) {
        return false;
    }
    @Override
    String getSymbol() {
        return "K";
    }
}
