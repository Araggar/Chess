import javax.swing.*;

class Main {
    public static void main(String[] args) {
       long startTime = System.nanoTime();

       JFrame root = new JFrame();
       root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       ChessGUI chessRoot = new ChessGUI(root);
       System.out.printf("\nGame Time1: %.8f ns\n",(System.nanoTime() - startTime)*0.000000001);
    }
}
