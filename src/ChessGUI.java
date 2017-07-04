import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by Tyrael on 5/13/2017.
 */
public class ChessGUI extends ChessGame {
    private JFrame chessRoot;
    private Scanner in;
    private Thread gameLoop;
    private int fromSq, toSq;
    private JButton[] pieceLocation;
    private JPanel[] panelLocation;
    private BufferedImage w_pawn, w_rook, w_bis, w_kni, w_kin, w_queen;
    private BufferedImage b_pawn, b_rook, b_bis, b_kni, b_kin, b_queen;


    ChessGUI(JFrame root){
        super();
        this.chessRoot = root;
        in = new Scanner(System.in);
        fromSq = -1;
        toSq = -1;
        try {
            w_pawn = ImageIO.read(new FileInputStream(new File(".\\resources\\white_pawn.png")));
            w_rook = ImageIO.read(new FileInputStream(new File(".\\resources\\white_rook.png")));
            w_kni = ImageIO.read(new FileInputStream(new File(".\\resources\\white_knight.png")));
            w_bis = ImageIO.read(new FileInputStream(new File(".\\resources\\white_bishop.png")));
            w_kin = ImageIO.read(new FileInputStream(new File(".\\resources\\white_king.png")));
            w_queen = ImageIO.read(new FileInputStream(new File(".\\resources\\white_queen.png")));
            b_pawn = ImageIO.read(new FileInputStream(new File(".\\resources\\black_pawn.png")));
            b_rook = ImageIO.read(new FileInputStream(new File(".\\resources\\black_rook.png")));
            b_kni = ImageIO.read(new FileInputStream(new File(".\\resources\\black_knight.png")));
            b_bis = ImageIO.read(new FileInputStream(new File(".\\resources\\black_bishop.png")));
            b_kin = ImageIO.read(new FileInputStream(new File(".\\resources\\black_king.png")));
            b_queen = ImageIO.read(new FileInputStream(new File(".\\resources\\black_queen.png")));
			b_queen = ImageIO.read(new FileInputStream(new File(".\\resources\\black_queen.png")));
			b_queen = ImageIO.read(new FileInputStream(new File(".\\resources\\black_queen.png")));
        } catch (IOException e) {
			try {
				w_pawn = ImageIO.read(new FileInputStream(new File("./resources/white_pawn.png")));
			    w_rook = ImageIO.read(new FileInputStream(new File("./resources/white_rook.png")));
			    w_kni = ImageIO.read(new FileInputStream(new File("./resources/white_knight.png")));
			    w_bis = ImageIO.read(new FileInputStream(new File("./resources/white_bishop.png")));
			    w_kin = ImageIO.read(new FileInputStream(new File("./resources/white_king.png")));
			    w_queen = ImageIO.read(new FileInputStream(new File("./resources/white_queen.png")));
			    b_pawn = ImageIO.read(new FileInputStream(new File("./resources/black_pawn.png")));
			    b_rook = ImageIO.read(new FileInputStream(new File("./resources/black_rook.png")));
			    b_kni = ImageIO.read(new FileInputStream(new File("./resources/black_knight.png")));
			    b_bis = ImageIO.read(new FileInputStream(new File("./resources/black_bishop.png")));
			    b_kin = ImageIO.read(new FileInputStream(new File("./resources/black_king.png")));
			    b_queen = ImageIO.read(new FileInputStream(new File("./resources/black_queen.png")));
				b_queen = ImageIO.read(new FileInputStream(new File("./resources/black_queen.png")));
				b_queen = ImageIO.read(new FileInputStream(new File("./resources/black_queen.png")));
			} catch (IOException e2) {
		        e2.printStackTrace();
		        System.exit(1);
			}        
		}
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
            panelLocation[i].setBackground(new Color(95,175,150,255));
            panelLocation[i].setPreferredSize(new Dimension(60,60));
            panelLocation[i].setOpaque(false);
            buttonBackground[i] = new JPanel();
            buttonBackground[i].setLayout(new BorderLayout());
            buttonBackground[i].setPreferredSize(new Dimension(60,60));
            if ((i % 2 != 0 && (i / 8) % 2 == 0) || (i % 2 == 0 && (i / 8) % 2 != 0)) {
                buttonBackground[i].setBackground(new Color(0, 70, 0));
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

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Menu",KeyEvent.VK_T);
        JMenuItem saveItem = new JMenuItem("Save",KeyEvent.VK_T);
        JMenuItem loadItem = new JMenuItem("Load",KeyEvent.VK_T);
        JMenuItem newGameItem = new JMenuItem("New Game",KeyEvent.VK_T);
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveState();
            }
        });
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadState();
                updateBoard();
            }
        });
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame root = new JFrame();
                root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ChessGUI newRoot = new ChessGUI(root);
                chessRoot.dispose();

            }
        });
        menuBar.add(menu);
        menu.add(newGameItem);
        menu.add(saveItem);
        menu.add(loadItem);

        this.chessRoot.setJMenuBar(menuBar);
        this.chessRoot.add(board);
        this.chessRoot.pack();
        this.chessRoot.revalidate();
        this.chessRoot.setVisible(true);
        this.updateBoard();
    }

    public void move(int indexFrom, int indexTo,BitBoard boardPiece){
        super.move( indexFrom, indexTo, boardPiece);
        updateBoard();
    }

    private void whereAreYou(int here){
        if(fromSq == -1){
            fromSq = here;
            this.highlightMovements(fromSq);
        }else{
            toSq = here;
            switch(gameLoop(fromSq, toSq)){
                case 1:
                    this.updateBoard();
                    System.out.println("CHECKMATE - BLACK WINS");
                    end("CHECKMATE - BLACK WINS");
                    break;
                case 2:
                    this.updateBoard();
                    System.out.println("CHECKMATE - WHITE WINS");
                    end("CHECKMATE - WHITE WINS");
                    break;
                default:
                    fromSq = -1;
                    toSq = -1;
                    this.updateBoard();
            }

        }
    }

    private void updateBoard(){
        clearBoard();
        for(int local : blackPawnIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_pawn));
        }

        for(int local : blackRookIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_rook));
        }

        for(int local : blackBishopIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_bis));
        }

        for(int local : blackQueenIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_queen));
        }

        for(int local : blackKingIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_kin));
        }

        for(int local : blackKnightIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(b_kni));
        }

        for(int local : whitePawnIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_pawn));
        }

        for(int local : whiteRookIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_rook));
        }

        for(int local : whiteBishopIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_bis));
        }

        for(int local : whiteKnightIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_kni));
        }

        for(int local : whiteQueenIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_queen));
        }

        for(int local : whiteKingIndexes()){
            this.pieceLocation[local].setIcon(new ImageIcon(w_kin));
        }
        chessRoot.repaint();
    }

    private void clearBoard(){
        for(JButton b : this.pieceLocation){
            b.setIcon(null);
        }
        for(JPanel p : this.panelLocation){
            p.setOpaque(false);
        }
    }

    private void highlightMovements(int iFrom){
        BitSet movements = movementIndexes(iFrom);
        for(int i = movements.nextSetBit(0); i >= 0; i = movements.nextSetBit(i+1)) {
            this.panelLocation[i].setOpaque(true);
        }
    chessRoot.repaint();
    }

    private void end(String s){
        JFrame end = new JFrame();
        end.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel endPanel = new JPanel();
        endPanel.setLayout(new GridLayout(2,1));
        JLabel endLabel = new JLabel(s);
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessRoot.dispose();
                JFrame root = new JFrame();
                root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ChessGUI chessRoot = new ChessGUI(root);
                end.dispose();
            }
        });

        endPanel.add(endLabel);
        endPanel.add(newGame);
        end.add(endPanel);
        end.pack();
        end.setVisible(true);


    }
}
