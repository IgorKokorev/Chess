package chess;

public class Horse extends ChessPiece {
    public Horse(String color) {
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
        // check L-move
        if( (Math.abs(line - toLine) == 1) && (Math.abs(column - toColumn) == 2) ) return true;
        return (Math.abs(line - toLine) == 2) && (Math.abs(column - toColumn) == 1);
    }

    @Override
    String getSymbol() {
        return "H";
    }
}
