
import java.io.File;
import java.util.Scanner;

public class FastMatrixMulti {

    public static void main(String[] args){

        File file = new File(args[0]);

        int[] A = null;

        // initialize A will matricies file
        try {

            Scanner sc = new Scanner(file);

            int linesInFile = 0;

            // get number of lines
            while (sc.hasNextLine()) {
                sc.nextLine();
                linesInFile++;
            }

            A = new int[linesInFile];

            sc.close();
            sc = new Scanner(file);


            for (int i = 0; i < linesInFile; i++) {
                A[i] = sc.nextInt();
            }




//            for (int x = 0; x < A.length; x++){
//                System.out.println(A[x]);
//            }

            sc.close();

        } catch (Exception e) {
            System.out.println("exception e");
        }

        int[][][] M_S = matrix_chain_order(A);
        print_optimal_parentheses(A, M_S[1], 1, A.length-2);

        //next step, comment out line 50, print S[][] and see what the heck is going on inside it.

    }

    public static int[][][] matrix_chain_order(int[] A){

        int n = A.length - 1;

        int[][] M = new int[n][n]; // stores optimal time cost for multiplying subchains A_i...A_j

        int[][] S = new int[n][n]; // stores position of optimal outmost pair of parentheses for subchain A_i...A_j

        for (int i = 1; i < n; i++) {
            M[i][i] = 0;
        }
        for (int h = 2; h <= n; h++){
            for (int i = 1; i < n-h+1; i++){
                int j = i+h-1;
                M[i][j] = Integer.MAX_VALUE;

                for (int k = i; k <= j-1; k++){
                    int q = M[i][k] + M[k+1][j] + (A[i-1]*A[k]*A[j]);
                    if (q < M[i][j]) {
                        M[i][j] = q; // optimal time cost for A_i...A_j
                        S[i][j] = k; // position for outermost parentheses for optimal multiplication of A_i...A_j
                    }


                }
            }



        }

        int[][][] MandS = {M, S};

        return MandS;

    }

    public static void print_optimal_parentheses(int[] A, int[][] S, int i, int j) {
        if (i==j){
            System.out.println(A[i]);
        }
        else {
            print_optimal_parentheses(A, S, i, S[i][j]);
            print_optimal_parentheses(A, S, S[i][j]+1, j);
        }
    }

}

