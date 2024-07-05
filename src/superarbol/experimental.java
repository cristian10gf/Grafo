package superarbol;

import java.util.ArrayList;

public class experimental {
    public void pascal_print(int n) {
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                for (int j = 0; j <= i; j++) {
                    for (int k = 0; k < 2 * n / (i + 1); k++)
                        System.out.print(" ");
                    System.out.print(pascal(i, j) + "   ");
                }
            } else {
                for (int j = 0; j <= i; j++) {
                    for (int k = 0; k <= 2 * n / (i + 1); k++)
                        System.out.print(" ");
                    System.out.print(" " + pascal(i, j) + " ");
                }
            }
            System.out.println();
        }
    }

    public int pascal(int i, int j) {
        if (j == 0 || j == i) {
            return 1;
        } else {
            return pascal(i - 1, j - 1) + pascal(i - 1, j);
        }
    }

    public void pascal_print_centrado(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                for (int k = 0; k < (n - i) + 1; k++) {
                    System.out.print("  ");
                }
                System.out.print(pascal(i, j) + "  ");
            }
            System.out.println();
        }
    }

    public void matriz_vaciadetexto(int n) {
        ArrayList<String> matriz = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            matriz.add("");
            for (int j = 0; j < n; j++) {
                matriz.set(i, matriz.get(i) + " ");
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(matriz.get(i));
        }
    }
    
    

}
 class PascalTriangle {
    public static void main(String[] args) {
        int n = 9; // Change the value of n as needed
        printPascalTriangle(n);
    }

    static void printPascalTriangle(int n) {
        // Calculate the maximum width of the numbers in the triangle
        int maxWidth = String.valueOf(binomialCoefficient(n - 1, (n - 1) / 2)).length() + 1;

        for (int line = 0; line < n; line++) {
            // Print leading spaces for center alignment
            for (int i = 0; i < (n - line - 1) * (maxWidth / 2); i++) {
                System.out.print(" ");
            }

            for (int i = 0; i <= line; i++) {
                // Calculate the number to print
                int number = binomialCoefficient(line, i);
                // Calculate the number of spaces needed for padding
                int padding = maxWidth - String.valueOf(number).length();
                // Print the number with appropriate padding
                System.out.printf("%" + maxWidth + "d", number);
            }
            System.out.println();
        }
    }

    static int binomialCoefficient(int n, int k) {
        int res = 1;
        if (k > n - k) {
            k = n - k;
        }
        for (int i = 0; i < k; ++i) {
            res *= (n - i);
            res /= (i + 1);
        }
        return res;
    }
}