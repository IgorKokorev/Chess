package chess;

public class Rook extends ChessPiece {
    public Rook(String color) {
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
        // not the same line or column
        if (line != toLine && column != toColumn) return false;
        // check all the way
        return checkWay(chessBoard, line, column, toLine, toColumn);
    }

    @Override
    String getSymbol() {
        return "R";
    }
}
