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
        if( !chessBoard.stdMoveCheck(line, column, toLine, toColumn) ) return false;

        // check move direction
        int direction = color.equals("White") ? 1 : -1;
        int sideMove = toColumn - column;
        if (Math.abs(sideMove) > 1) return false;

        // check if Pawn can take enemy piece
        if (chessBoard.board[toLine][toColumn] != null) {
            if (sideMove == 0) return false;
            return toLine == line + direction;
        }
        //check move to empty cell
        if (sideMove != 0) return false;
        // 1-step move
        if (toLine == line + direction) return true;
        // or 2-steps but only in the very beginning
        if ( ((7 - toLine * 2) == direction) && (toLine == line + 2 * direction) )
            return chessBoard.checkWay(line, column, toLine, toColumn);
        else return false;
    }

    @Override
    String getSymbol() {
        return "P";
    }
}
