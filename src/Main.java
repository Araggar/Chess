

class Main {
    public static void main(String[] args) {
       long startTime = System.nanoTime();
       TestCases.caseBitBoard();
       TestCases.caseGenerator();
       TestCases.caseMovement();
       TestCases.caseGame();
       System.out.printf("\nElapsed Time: %.8f ns",(System.nanoTime() - startTime)*0.000000001);
    }

}