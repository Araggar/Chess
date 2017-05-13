import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 * Created by Tyrael on 5/13/2017.
 */
public class ChessGUI {
    JFrame chessRoot;
    ChessGame chess;
    Scanner in;
    Boolean checkMate = false;
    Thread gameLoop;
    int fromSq, toSq;


    ChessGUI(JFrame root){
        this.chessRoot = root;
        chess = new ChessGame();
        in = new Scanner(System.in);
        fromSq = -1;
        toSq = -1;
        ini();

    }

    private void ini() {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        JButton[] pieceLocation = new JButton[64];
        for (int i = 0; i < 64; ++i) {

            pieceLocation[i] = new JButton();
            pieceLocation[i].setText(Integer.toString(i + 1));
            pieceLocation[i].setPreferredSize(new Dimension(60, 60));
            //pieceLocation[i].setBorderPainted(false);
            //pieceLocation[i].setFocusPainted(false);
            //pieceLocation[i].setContentAreaFilled(false);
            int index = i;
            pieceLocation[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    whereAreYou(index);
                }
            });
            board.add(pieceLocation[i]);
        }

        chess.updateBoard(pieceLocation);
        this.chessRoot.add(board);
        this.chessRoot.pack();
        this.chessRoot.setVisible(true);
    }

    private void whereAreYou(int here){
        if(fromSq == -1){
            fromSq = here;
        }else{
            toSq = here;
            gameLoop = new Thread() {
                public void run() {
                    gameLoop();
                }
            };
            gameLoop.setDaemon(true);
            gameLoop.start();
        }
    }

    private void gameLoop() {
        if(wrongMove()){}else {
            if (wrongMove()) {
                fromSq = -1;
                toSq = -1;
            } else {
                System.out.println(fromSq);
                System.out.print("  " + toSq);
                chess.simulate2ply();
                System.out.println("Your Turn Riumã");
                fromSq = -1;
                toSq = -1;
            }
        }
    }

    Boolean wrongMove(){
        return (fromSq == -1 || toSq == -1);
    }
}
