package chess;

abstract public class ChessPiece {
    public String color;
    public Boolean check = true;

    public ChessPiece(String color) {
        if (color.equals("White") || color.equals("Black")) this.color = color;
        else {
            System.out.println("Вы что-то ввели не так, попробуйте ещё раз");
        }
    }

    abstract String getColor();

    abstract Boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    abstract String getSymbol();

    // Compare color of the figure in l:c with the target. If target is empty or different color - ok.
    Boolean isOkToMove(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // if we try to move empty cell
        if ( chessBoard.board[line][column] == null ) return false;
        // if target is empty it's ok to move
        if ( chessBoard.board[toLine][toColumn] == null ) return true;
        // if from and to are different colors than it's ok to move
        return !chessBoard.board[toLine][toColumn].color.equals(chessBoard.board[line][column].color);
    }

    // check the way from current position to target
    // considering the move is correct on empty board
    // returns true if the way is empty
    Boolean checkWay(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        int dl = line == toLine ? 0 : (toLine - line) / Math.abs(toLine - line);
        int dc = column == toColumn ? 0 : (toColumn - column) / Math.abs(toColumn - column);


        int l = line + dl;
        int c = column + dc;

        if (dl != 0) {
            while ((l > toLine && dl < 0) || (l < toLine)) {
                if (chessBoard.board[l][c] != null) return false;
                l += dl;
                c += dc;
            }
        } else {
            while ((c > toColumn && dc < 0) || (c < toColumn)) {
                if (chessBoard.board[l][c] != null) return false;
                l += dl;
                c += dc;
            }
        }
        return true;
    }

    Boolean stdMoveCheck(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if( !( chessBoard.checkPos(toLine) && chessBoard.checkPos(toColumn) ) ) return false; // out of board
        if (line == toLine && column == toColumn) return false; // same point
        if( !isOkToMove(chessBoard, line, column, toLine, toColumn) ) return false; // diff color or empty target

        return true;
    }
}
