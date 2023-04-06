package chess;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer; // whose turn
    // Where the kings are
    private int lineWKing;
    private int columnWKing;
    private int lineBKing;
    private int columnBKing;

    // initializing a board indicating whose turn first
    public ChessBoard(String nowPlayer) {
        this();
        if (nowPlayer.equals("Black")) this.nowPlayer = nowPlayer;
        else this.nowPlayer = "White";
    }

    // initializing a board with first move by default
    public ChessBoard() {
        this.nowPlayer = "White";

        this.board[0][0] = new Rook("White");
        this.board[0][1] = new Horse("White");
        this.board[0][2] = new Bishop("White");
        this.board[0][3] = new Queen("White");
        this.board[0][4] = new King("White");
        lineWKing = 0;
        columnWKing = 4;
        this.board[0][5] = new Bishop("White");
        this.board[0][6] = new Horse("White");
        this.board[0][7] = new Rook("White");
        this.board[1][0] = new Pawn("White");
        this.board[1][1] = new Pawn("White");
        this.board[1][2] = new Pawn("White");
        this.board[1][3] = new Pawn("White");
        this.board[1][4] = new Pawn("White");
        this.board[1][5] = new Pawn("White");
        this.board[1][6] = new Pawn("White");
        this.board[1][7] = new Pawn("White");

        this.board[7][0] = new Rook("Black");
        this.board[7][1] = new Horse("Black");
        this.board[7][2] = new Bishop("Black");
        this.board[7][3] = new Queen("Black");
        this.board[7][4] = new King("Black");
        lineBKing = 7;
        columnBKing = 4;
        this.board[7][5] = new Bishop("Black");
        this.board[7][6] = new Horse("Black");
        this.board[7][7] = new Rook("Black");
        this.board[6][0] = new Pawn("Black");
        this.board[6][1] = new Pawn("Black");
        this.board[6][2] = new Pawn("Black");
        this.board[6][3] = new Pawn("Black");
        this.board[6][4] = new Pawn("Black");
        this.board[6][5] = new Pawn("Black");
        this.board[6][6] = new Pawn("Black");
        this.board[6][7] = new Pawn("Black");
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    // move a piece from start to end
    // if the move was made correctly returns 0
    // if the move is impossible returns -1
    // if it was a check returns 1
    // if the king was eaten returns 2
    public int moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (!checkPos(startLine) || !checkPos(startColumn) || !checkPos(endLine) || !checkPos(endColumn)) return -1;

        if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return -1;

        if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {

            if (board[startLine][startColumn].getSymbol().equals("K")) {
                board[startLine][startColumn].check = false; // check position for castling
                if (nowPlayer.equals("White")) { // remember the kings positions
                    lineWKing = endLine;
                    columnWKing = endColumn;
                } else {
                    lineBKing = endLine;
                    columnBKing = endColumn;
                }
            }
            if (board[startLine][startColumn].getSymbol().equals("R")) { // check position for castling
                board[startLine][startColumn].check = false;
            }

            // checkmate
            if (board[endLine][endColumn] != null && board[endLine][endColumn].getSymbol().equals("K")) return 2;

            board[endLine][endColumn] = board[startLine][startColumn]; // if piece can move, we moved a piece
            board[startLine][startColumn] = null; // set null to previous cell
            this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";


            //check if the enemy king is under attack
            if (this.nowPlayer.equals("White")) {
                if( ((King)board[lineWKing][columnWKing]).isUnderAttack(this, lineWKing, columnWKing) ) return 1;
            } else {
                if( ((King)board[lineBKing][columnBKing]).isUnderAttack(this, lineBKing, columnBKing) ) return 1;
            }
            return 0;
        } else return -1;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    // Compare color of the figure in l:c with the target. If target is empty or different color - ok.
    Boolean isOkToMove(int line, int column, int toLine, int toColumn) {
        // if we try to move empty cell
        if ( this.board[line][column] == null ) return false;
        // if target is empty it's ok to move
        if ( this.board[toLine][toColumn] == null ) return true;
        // if from and to are different colors than it's ok to move
        return !this.board[toLine][toColumn].color.equals(this.board[line][column].color);
    }

    // check the way from current position to target
    // considering the move is correct on empty board
    // returns true if the way is empty
    Boolean checkWay(int line, int column, int toLine, int toColumn) {

        int dl = line == toLine ? 0 : (toLine - line) / Math.abs(toLine - line);
        int dc = column == toColumn ? 0 : (toColumn - column) / Math.abs(toColumn - column);


        int l = line + dl;
        int c = column + dc;

        if (dl != 0) {
            while ((l > toLine && dl < 0) || (l < toLine)) {
                if (this.board[l][c] != null) return false;
                l += dl;
                c += dc;
            }
        } else {
            while ((c > toColumn && dc < 0) || (c < toColumn)) {
                if (this.board[l][c] != null) return false;
                l += dl;
                c += dc;
            }
        }
        return true;
    }

    Boolean stdMoveCheck(int line, int column, int toLine, int toColumn) {
        if( !( this.checkPos(toLine) && this.checkPos(toColumn) ) ) return false; // out of board
        if (line == toLine && column == toColumn) return false; // same point
        return this.isOkToMove(line, column, toLine, toColumn); // diff color or empty target
    }

    public boolean castling0() {
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {              // never moved
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][0].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 2)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][2] = new King("White");   // move King
                    lineWKing = 0;
                    columnWKing = 2;
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");   // move Rook
                    board[0][3].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {              // never moved
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 2)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][2] = new King("Black");   // move King
                    lineBKing = 7;
                    columnBKing = 2;
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");   // move Rook
                    board[7][3].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }

    public boolean castling7() {
        if (nowPlayer.equals("White")) {
            if (board[0][7] == null || board[0][4] == null) return false;
            if (board[0][7].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][6] == null && board[0][5] == null) {              // never moved
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][7].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 6)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][6] = new King("White");   // move King
                    lineWKing = 0;
                    columnWKing = 6;
                    board[0][6].check = false;
                    board[0][7] = null;
                    board[0][5] = new Rook("White");   // move Rook
                    board[0][5].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][7] == null || board[7][4] == null) return false;
            if (board[7][7].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][6] == null && board[7][5] == null) {              // never moved
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][7].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 6)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][6] = new King("Black");   // move King
                    lineBKing = 7;
                    columnBKing = 6;
                    board[7][6].check = false;
                    board[7][7] = null;
                    board[7][5] = new Rook("Black");   // move Rook
                    board[7][5].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }
}
