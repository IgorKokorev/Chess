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
        if( !chessBoard.stdMoveCheck(line, column, toLine, toColumn) ) return false;
        // check "nearby" move
        if( !((Math.abs(line - toLine) < 2) && (Math.abs(column - toColumn) < 2)) ) return false;
        // no need to check the way
        return true;
    }

    Boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {
        for (int l = 0; l <= 7; l++) {
            for (int c = 0; c <= 7; c++) {
                if (chessBoard.board[l][c] == null) continue;;
                if (chessBoard.board[l][c].color.equals(this.color)) continue;;
                if (chessBoard.board[l][c].canMoveToPosition(chessBoard, l, c, line, column)) return true;
            }
        }
        return false;
    }
    @Override
    String getSymbol() {
        return "K";
    }
}
