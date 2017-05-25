import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by Tyrael on 5/13/2017.
 */
public class ChessGUI extends ChessGame {
    JFrame chessRoot;
    Scanner in;
    Thread gameLoop;
    int fromSq, toSq;
    JButton[] pieceLocation;
    JPanel[] panelLocation;


    ChessGUI(JFrame root){
        super();
        this.chessRoot = root;
        in = new Scanner(System.in);
        fromSq = -1;
        toSq = -1;
        ini();

    }

    private void ini() {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        pieceLocation = new JButton[64];
        panelLocation = new JPanel[64];
        JPanel[] buttonBackground = new JPanel[64];
        for (int i = 0; i < 64; ++i) {
            panelLocation[i] = new JPanel();
            panelLocation[i].setBackground(new Color(150,150,0,255));
            panelLocation[i].setPreferredSize(new Dimension(60,60));
            panelLocation[i].setOpaque(false);
            buttonBackground[i] = new JPanel();
            buttonBackground[i].setLayout(new BorderLayout());
            buttonBackground[i].setPreferredSize(new Dimension(60,60));
            if ((i % 2 != 0 && (i / 8) % 2 == 0) || (i % 2 == 0 && (i / 8) % 2 != 0)) {
                buttonBackground[i].setBackground(new Color(0, 0, 0));
            } else {
                buttonBackground[i].setBackground(new Color(255, 255, 255));
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
            board.add(buttonBackground[i]);
            buttonBackground[i].add(panelLocation[i], BorderLayout.CENTER);
            panelLocation[i].add(pieceLocation[i]);
        }
        this.updateBoard();
        this.chessRoot.add(board);
        this.chessRoot.pack();
        this.chessRoot.setVisible(true);
    }

    void move(int indexFrom, int indexTo,BitSet boardPiece){
        super.move( indexFrom, indexTo, boardPiece);
        updateBoard();
    }

    private void whereAreYou(int here){
        if(fromSq == -1){
            fromSq = here;
            this.highlightMovements(fromSq);
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
        if (legalMove(fromSq, toSq)) {
            move(fromSq, toSq, pieceFinder(fromSq));
            simulate2ply();
            System.out.println("Your Turn");
            fromSq = -1;
            toSq = -1;
        } else {
            System.out.println("Wrong Move");
            updateBoard();
            fromSq = -1;
            toSq = -1;
        }
    }

    public void updateBoard(){
        clearBoard();
        for(int i = blackPawns.nextSetBit(0); i>=0; i = blackPawns.nextSetBit(++i)){
            this.pieceLocation[i].setText("BP");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = blackRooks.nextSetBit(0); i>=0; i = blackRooks.nextSetBit(++i)){
            this.pieceLocation[i].setText("BR");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = blackBishops.nextSetBit(0); i>=0; i = blackBishops.nextSetBit(++i)){
            this.pieceLocation[i].setText("BB");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = blackQueens.nextSetBit(0); i>=0; i = blackQueens.nextSetBit(++i)){
            this.pieceLocation[i].setText("BQ");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = blackKing.nextSetBit(0); i>=0; i = blackKing.nextSetBit(++i)){
            this.pieceLocation[i].setText("BK");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = blackKnights.nextSetBit(0); i>=0; i = blackKnights.nextSetBit(++i)){
            this.pieceLocation[i].setText("BN");
            this.pieceLocation[i].setForeground(new Color(255,150,0));
        }

        for(int i = whitePawns.nextSetBit(0); i>=0; i = whitePawns.nextSetBit(++i)){
            this.pieceLocation[i].setText("WP");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }

        for(int i = whiteRooks.nextSetBit(0); i>=0; i = whiteRooks.nextSetBit(++i)){
            this.pieceLocation[i].setText("WR");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }

        for(int i = whiteBishops.nextSetBit(0); i>=0; i = whiteBishops.nextSetBit(++i)){
            this.pieceLocation[i].setText("WB");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }

        for(int i = whiteKnights.nextSetBit(0); i>=0; i = whiteKnights.nextSetBit(++i)){
            this.pieceLocation[i].setText("WN");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }

        for(int i = whiteQueens.nextSetBit(0); i>=0; i = whiteQueens.nextSetBit(++i)){
            this.pieceLocation[i].setText("WQ");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }

        for(int i = whiteKing.nextSetBit(0); i>=0; i = whiteKing.nextSetBit(++i)){
            this.pieceLocation[i].setText("WK");
            this.pieceLocation[i].setForeground(new Color(0,150,255));
        }
        chessRoot.repaint();
    }

    private void clearBoard(){
        for(JButton b : this.pieceLocation){
            b.setText("");
        }
        for(JPanel p : this.panelLocation){
            p.setOpaque(false);
        }
    }

    public void highlightMovements(int iFrom){
        if(whitePawns.get(iFrom)){
            for(int i = whitePawnMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = whitePawnMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                this.panelLocation[i].setOpaque(true);
            }
        }else{

            if(whiteRooks.get(iFrom)){
                for(int i = rookMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = rookMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                    this.panelLocation[i].setOpaque(true);
                }
            }else{

                if(whiteBishops.get(iFrom)){
                    for(int i = bishopMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = bishopMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                        this.panelLocation[i].setOpaque(true);
                    }
                }else{

                    if(whiteKnights.get(iFrom)){
                        for(int i = knightMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = knightMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                            this.panelLocation[i].setOpaque(true);
                        }
                    }else{

                        if(whiteQueens.get(iFrom)){
                            for(int i = queenMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = queenMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                                this.panelLocation[i].setOpaque(true);
                            }
                        }else{

                            if(whiteKing.get(iFrom)){
                                for(int i = kingMovement(iFrom, whiteBoard, blackBoard).nextSetBit(0); i >= 0; i = kingMovement(iFrom, whiteBoard, blackBoard).nextSetBit(i+1)) {
                                    this.panelLocation[i].setOpaque(true);
                                }
                            }}}}}}
    chessRoot.repaint();
    }




}
