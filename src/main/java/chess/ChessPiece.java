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

}
