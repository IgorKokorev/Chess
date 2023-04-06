package chess;

public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    String getColor() {
        return color;
    }

    @Override
    Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // standard check
        if( !chessBoard.stdMoveCheck(line, column, toLine, toColumn) ) return false;
        // check diagonal move
        if ( !( Math.abs(line - toLine) == Math.abs(column - toColumn) ) ) return false;
        // check way
        return chessBoard.checkWay(line, column, toLine, toColumn);
    }

    @Override
    String getSymbol() {
        return "B";
    }
}
