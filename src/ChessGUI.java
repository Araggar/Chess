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
    Thread gameLoop;
    int fromSq, toSq;


    ChessGUI(JFrame root){
        this.chessRoot = root;
        in = new Scanner(System.in);
        fromSq = -1;
        toSq = -1;
        ini();

    }

    private void ini() {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        JButton[] pieceLocation = new JButton[64];
        JPanel[] panelLocation = new JPanel[64];
        for (int i = 0; i < 64; ++i) {
            panelLocation[i] = new JPanel();
            panelLocation[i].setOpaque(true);
            panelLocation[i].setPreferredSize(new Dimension(60,60));
            if ((i % 2 != 0 && (i / 8) % 2 == 0) || (i % 2 == 0 && (i / 8) % 2 != 0)) {
                panelLocation[i].setBackground(new Color(0, 0, 0));
            } else {
                panelLocation[i].setBackground(new Color(255, 255, 255));
            }

            pieceLocation[i] = new JButton();
            //pieceLocation[i].setText(Integer.toString(i));
            pieceLocation[i].setPreferredSize(new Dimension(60, 60));
            pieceLocation[i].setBorderPainted(false);
            pieceLocation[i].setFocusPainted(false);
            pieceLocation[i].setContentAreaFilled(false);
            int index = i;
            pieceLocation[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    whereAreYou(index);
                }
            });
            board.add(panelLocation[i]);
            panelLocation[i].add(pieceLocation[i]);
        }
        chess = new ChessGame(pieceLocation);
        chess.updateBoard();
        this.chessRoot.add(board);
        this.chessRoot.pack();
        this.chessRoot.setVisible(true);
    }

    private void whereAreYou(int here){
        if(fromSq == -1){
            fromSq = here;
            chess.highlightMovements(fromSq);
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
        if (chess.legalMove(fromSq, toSq)) {
            chess.moveAndUpdate(fromSq, toSq);
            chess.simulate2ply();
            System.out.println("Your Turn");
            fromSq = -1;
            toSq = -1;
        } else {
            System.out.println("Wrong Move");
            chess.updateBoard();
            fromSq = -1;
            toSq = -1;
        }
    }


}
