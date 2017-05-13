import javafx.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

class Main {
    public static void main(String[] args) {
       long startTime = System.nanoTime();
       //TestCases.caseBitBoard();
       //TestCases.caseGenerator();
       //TestCases.caseMovement();
       //TestCases.caseGame();
       System.out.printf("\nElapsed Time: %.8f ns",(System.nanoTime() - startTime)*0.000000001);

       JFrame root = new JFrame();
       root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menu = new JPanel();

       GridLayout menuLayout = new GridLayout(2,0);

       root.setContentPane(menu);
       menu.setLayout(menuLayout);

       JButton chessGame = new JButton("Chess");
       chessGame.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               ChessGUI chessRoot = new ChessGUI(new JFrame());
           }
       });

        JButton checkersGame = new JButton("Checkers");
        checkersGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                TestCases.caseGame();
            }
        });

        menu.add(chessGame, BorderLayout.NORTH);
        menu.add(checkersGame, BorderLayout.SOUTH);

        root.pack();
        root.setVisible(true);

    }

}