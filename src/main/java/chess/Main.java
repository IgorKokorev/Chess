package chess;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        ChessBoard board = new ChessBoard();
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
               Чтобы проверить игру надо вводить команды:
               'exit' - для выхода
               'replay' - для перезапуска игры
               'castling0' или 'castling7' - для рокировки по соответствующей линии
               'move 1 1 2 3' - для передвижения фигуры с позиции 1 1 на 2 3(поле это двумерный массив от 0 до 7)
               Проверьте могут ли фигуры ходить друг сквозь друга, корректно ли съедают друг друга, можно ли поставить шах и сделать рокировку?""");
        System.out.println();
        board.printBoard();
        while (true) {
            String s = scanner.nextLine();
            if (s.equals("exit")) break;
            else if (s.equals("replay")) {
                System.out.println("Заново");
                board = new ChessBoard();
                board.printBoard();
            } else {
                if (s.equals("castling0")) {
                    if (board.castling0()) {
                        System.out.println("Рокировка удалась");
                        board.printBoard();
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                } else if (s.equals("castling7")) {
                    if (board.castling7()) {
                        System.out.println("Рокировка удалась");
                        board.printBoard();
                    } else {
                        System.out.println("Рокировка не удалась");
                    }
                } else if (s.contains("move")) {
                    String[] a = s.split(" ");
                    try {
                        int line = Integer.parseInt(a[1]);
                        int column = Integer.parseInt(a[2]);
                        int toLine = Integer.parseInt(a[3]);
                        int toColumn = Integer.parseInt(a[4]);
                        switch (board.moveToPosition(line, column, toLine, toColumn)) {
                            case 0 -> {
                                System.out.println("Успешно передвинулись");
                                board.printBoard();
                            }
                            case -1 -> System.out.println("Передвижение не удалось");
                            case 1 -> {
                                System.out.println("\n == ШАХ! ==\n");
                                board.printBoard();
                            }
                            case 2 -> {
                                System.out.println("\n\n == КОРОЛЬ ПАЛ! ==\n\n");
                                System.out.println("Хотите сыграть заново (y/n)?");
                                if (scanner.nextLine().equals("y")) {
                                    System.out.println("Заново");
                                    board = new ChessBoard();
                                    board.printBoard();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Вы что-то ввели не так, попробуйте ещё раз");
                    }

                } else board.printBoard();
            }
        }
    }
}