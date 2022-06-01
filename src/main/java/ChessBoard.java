import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
public class ChessBoard extends JFrame {

   static JButton[][] buttons = new JButton[8][8];
   public static ActionListener actionListener = new TestActionListener();
    static ImageIcon dot = new ImageIcon("dot_PNG29.png");
    static int wTime = 1200;
    static int bTime = 1200;
    static JLabel label = new JLabel("...");

    public ChessBoard() {
        JFrame choseFrame = new JFrame(); //панель для получения кода расположения фигур
        JLabel enterTheFen =  new JLabel("Enter the FEN");
        JButton submitFEN = new JButton("Submit");
        JTextField textFEN = new JTextField(50);
        textFEN.setHorizontalAlignment(JTextField.CENTER);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText("Mirror");
        submitFEN.addActionListener(e -> {
            Logic.FEN(textFEN.getText());
            if (textFEN.getText().equals("")) Logic.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
            startGame();
            choseFrame.setVisible(false);
            Logic.mirrors = checkBox.isSelected();
        });


        JPanel p = new JPanel();
        p.add(textFEN);
        p.add(submitFEN);
        p.add(enterTheFen);
        p.add(checkBox);
        ImageIcon icon = new ImageIcon("1728594.png");
        choseFrame.setTitle("Chose");
        choseFrame.setIconImage(icon.getImage());
        choseFrame.setLayout(new BorderLayout(20,0));
        choseFrame.add(p,BorderLayout.CENTER );
        choseFrame.setSize(700, 300);
        choseFrame.setVisible(true);

    }


    static void startGame() { //создание окна игры, расстановка фигур
        JFrame chessBoard = new JFrame();
        chessBoard.setSize(1000, 1000);
        chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard.setTitle("Chess");
        ImageIcon icon = new ImageIcon("1728594.png");
        chessBoard.setIconImage(icon.getImage());
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setSize(525, 525);

        JPanel timer = new JPanel();
        timer.setSize(525, 100);
        Timer wTimer = new Timer(1000, e -> {
            if (Logic.colour) {
                if (wTime > 0) {
                    wTime--;
                    label.setText(wTime / 60 + ":" + wTime % 60);
                } else System.out.println("Black wins");
            } else {
                if (bTime > 0) {
                    bTime--;
                    label.setText(bTime / 60 + ":" + bTime % 60);
                } else System.out.println("White wins");
            }
        });

        boolean t = false;

        for (int y = 0; y < 8; y++) {
            t = !t;
            for (int x = 0; x < 8; x++) {
                buttons[y][x] = new JButton();
                if (t) {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.pink);
                    else buttons[y][x].setBackground(Color.white);
                } else {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.white);
                    else buttons[y][x].setBackground(Color.pink);
                }

                if (Logic.pieces[y][x] != null) buttons[y][x].setIcon(Logic.pieces[y][x].pieceIcon);
                buttons[y][x].addActionListener(actionListener);
                board.add(buttons[y][x]);
            }
        }
        timer.add(label);
        wTimer.start();
        chessBoard.add(timer, BorderLayout.SOUTH);
        chessBoard.add(board);
        chessBoard.pack();
        chessBoard.setVisible(true);

    }

    static class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //обработка кнопок
            JButton o = (JButton) e.getSource();
            for (int y = 0; y < 8; y++) { //поиск нажатой кнопки
                for (int x = 0; x < 8; x++) {
                    if (buttons[y][x] == o) {
                        Logic.actionMove(y, x);
                        break;
                    }
                }
            }

        }
    }

    static void mirror() { //разворот доски
        for (int y = 4; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ImageIcon temp = (ImageIcon) buttons[y][x].getIcon();
                buttons[y][x].setIcon(ChessBoard.buttons[7 - y][x].getIcon());
                buttons[7 - y][x].setIcon(temp);
                Piece tempP = Logic.pieces[y][x];
                Logic.pieces[y][x] = Logic.pieces[7 - y][x];
                Logic.pieces[7 - y][x] = tempP;

            }
        }
    }


    static void clearDots() { //удаление кругов хода
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (buttons[y][x].getIcon() == dot) buttons[y][x].setIcon(null);
            }
        }
    }

    static void defColours() { //исправление цветов
        boolean t = false;
        for (int y = 0; y < 8; y++) {
            t = !t;
            for (int x = 0; x < 8; x++) {
                if (t) {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.pink);
                    else buttons[y][x].setBackground(Color.white);
                } else {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.white);
                    else buttons[y][x].setBackground(Color.pink);
                }
            }
        }
    }

    static void addDot(int y, int x) {
        buttons[y][x].setIcon(dot);
    }

    static void paintRed(int y, int x){
        buttons[y][x].setBackground(Color.red);
    }

}