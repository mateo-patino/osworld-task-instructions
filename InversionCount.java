import java.io.*;
import java.util.*;

public class InversionCount {

    public static void main(String[] args) throws IOException {
        // Read input array from array.txt
        BufferedReader br = new BufferedReader(new FileReader("array.txt"));
        String line = br.readLine();
        br.close();

        String[] parts = line.trim().split("\\s+");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }

        // Count inversions using the provided fast method
        long inversions = countInversionsFast(array);

        // Write result to output.txt
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
        bw.write(Long.toString(inversions));
        bw.newLine();
        bw.close();
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original array.
        int[] arrayCopy = new int[array.length];
        int[] scratch   = new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);

        return countInvFastHelper(arrayCopy, scratch, 0, array.length - 1);
    }

    /**
     * Returns the number of inversions in an array using mergesort
     */
    public static long countInvFastHelper(int[] array, int[] scratch, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            long leftCount  = countInvFastHelper(array, scratch, low, mid);
            long rightCount = countInvFastHelper(array, scratch, mid + 1, high);

            long invCount = 0;
            int i = low, j = mid + 1;

            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                    invCount += mid - i + 1;
                }
            }

            // Reflect changes made to scratch back into array
            for (int k = low; k <= high; k++) {
                array[k] = scratch[k];
            }

            return leftCount + rightCount + invCount;
        }
        return 0;
    }
}