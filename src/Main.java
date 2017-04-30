import java.util.BitSet;

class Main {
    public static void main(String[] args) {
       long startTime = System.nanoTime();
       TestCases.caseGenerator();
       TestCases.caseMovement();
       System.out.printf("Elapsed Time: %.8f ns",(System.nanoTime() - startTime)*0.000000001);
    }

}