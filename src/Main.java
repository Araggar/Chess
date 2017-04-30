

class Main {
    public static void main(String[] args) {
       long startTime = System.nanoTime();
       TestCases.caseBitBoard();
       TestCases.caseGenerator();
       TestCases.caseMovement();
       //BoardEvaluator b = new BoardEvaluator();
       //int[] a = b.blackKingPositionalValues;
       //for(int i = 0; i<a.length;++i){
       //    a[i] = a[i]*(-1);
       //}
       //int c = 0;
       //for(int i = a.length-1; i>=0; --i){
       //    if(c%8 == 0)System.out.println();
       //    System.out.printf("%3d,",a[i]);
       //    c++;
       //}
       System.out.printf("\nElapsed Time: %.8f ns",(System.nanoTime() - startTime)*0.000000001);
    }

}