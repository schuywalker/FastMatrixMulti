
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

            A = new int[linesInFile+1];

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



        Container M_S = matrix_chain_order(A);
        print_optimal_parentheses(A, M_S.S, 1, A.length-1-1);
        System.out.println("\n"+M_S.timeCost);
        //One -1 for 0 index, other -1 because n numbers represent n-1 matricies


    }

    public static Container matrix_chain_order(int[] p){

        int n = p.length - 1;

        int[][] M = new int[n][n]; // stores optimal time cost for multiplying subchains A_i...A_j

        int[][] S = new int[n][n]; // stores position of optimal outmost pair of parentheses for subchain A_i...A_j

        int timeCost = 0;

        for (int i = 1; i < n; i++) {
            M[i][i] = 0;
        }
        for (int h = 2; h <= n; h++){           //h is the length of the subchain
            for (int i = 1; i < n-h+1; i++){    //starting position of the sub-chain
                int j = i+h-1;                  //ending position of the sub-chain
                M[i][j] = Integer.MAX_VALUE;

                for (int k = i; k <= j-1; k++){
                    int q = M[i][k] + M[k+1][j] + (p[i-1]*p[k]*p[j]);
                    if (q < M[i][j]) {
                        M[i][j] = q; // optimal time cost for A_i...A_j
                        S[i][j] = k; // position for outermost parentheses for optimal multiplication of A_i...A_j
                    }

                }
                timeCost = M[i][j];
            }

        }

        Container MandS = new Container(M,S, timeCost);

        return MandS;

    }

    public static void print_optimal_parentheses(int[] A, int[][] S, int i, int j) {
        if (i==j){
            System.out.print("A"+i);
        }
        else {
            System.out.print("(");
            print_optimal_parentheses(A, S, i, S[i][j]);
            print_optimal_parentheses(A, S, S[i][j]+1, j);
            System.out.print(")");


        }
    }

}

