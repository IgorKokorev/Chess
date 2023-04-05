package chess;

public class Pawn extends ChessPiece {
    public Pawn(String color) {
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

        // check move direction
        int direction = color.equals("White") ? 1 : -1;
        // 1-step move
        if (toLine == line + direction) return true;
        // or 2-steps but only in the very beginning
        if ( ((7 - toLine * 2) == direction) && (toLine == line + 2 * direction) )
            return checkWay(chessBoard, line, column, toLine, toColumn);
        else return false;
    }

    @Override
    String getSymbol() {
        return "P";
    }
}
