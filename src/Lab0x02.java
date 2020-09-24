import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab0x02 {
    private static Random rng = new Random();
    private static long timeout = 1000000000;
    private static int minV = 65, maxV = 90;

    public static void main(String[] args) {
        if(true) {
            String[] arr;
            long startTime = 0, endTime = 0;
            long[] previousTime = new long[4];
            Arrays.fill(previousTime,0);
            System.out.println("Insertion Sort Results:");
            System.out.println("N\tk=6 Time\tDoubling Ratio\tk=12\tDoubling Ratio\tk=24\tDoubling Ratio\tk=48\tDoublingRatio");
            for(int N = 1; endTime-startTime < timeout; N*=2) {
                System.out.print(N + "\t");
                for(int k = 6; k <= 48; k*=2) {
                    arr = GenerateTestList(N,k,minV,maxV);
                    startTime = getCpuTime();
                    Sorting.InsertionSort(arr);
                    endTime = getCpuTime();
                    System.out.print((endTime-startTime) + "\t" + (previousTime[(int)Math.log(k/6)] == 0 ? "NA" : ((double)(endTime-startTime)/previousTime[(int)Math.log(k/6)])) + "\t");
                    previousTime[(int)Math.log(k/6)] = endTime - startTime;
                }
                System.out.println();
            }
            System.out.println();

            startTime = endTime = 0;
            Arrays.fill(previousTime,0);
            System.out.println("Merge Sort Results:");
            System.out.println("N\tk=6 Time\tDoubling Ratio\tk=12\tDoubling Ratio\tk=24\tDoubling Ratio\tk=48\tDoublingRatio");
            for(int N = 1; endTime-startTime < timeout; N*=2) {
                System.out.print(N + "\t");
                for(int k = 6; k <= 48; k*=2) {
                    arr = GenerateTestList(N, k, minV, maxV);
                    startTime = getCpuTime();
                    Sorting.MergeSort(arr);
                    endTime = getCpuTime();
                    System.out.print((endTime - startTime) + "\t" + (previousTime[(int) Math.log(k / 6)] == 0 ? "NA" : ((double)(endTime - startTime) / previousTime[(int) Math.log(k / 6)])) + "\t");
                    previousTime[(int) Math.log(k / 6)] = endTime - startTime;
                }
                System.out.println();
            }
            System.out.println();

            startTime = endTime = 0;
            Arrays.fill(previousTime,0);
            System.out.println("Quick Sort Results:");
            System.out.println("N\tk=6 Time\tDoubling Ratio\tk=12\tDoubling Ratio\tk=24\tDoubling Ratio\tk=48\tDoublingRatio");
            for(int N = 1; endTime-startTime < timeout; N*=2) {
                System.out.print(N + "\t");
                for(int k = 6; k <= 48; k*=2) {
                    arr = GenerateTestList(N, k, minV, maxV);
                    startTime = getCpuTime();
                    Sorting.QuickSort(arr);
                    endTime = getCpuTime();
                    System.out.print((endTime - startTime) + "\t" + (previousTime[(int) Math.log(k / 6)] == 0 ? "NA" : ((double)(endTime - startTime) / previousTime[(int) Math.log(k / 6)])) + "\t");
                    previousTime[(int) Math.log(k / 6)] = endTime - startTime;
                }
                System.out.println();
            }

            for(int d : new int[] {1,2,3,4,8}) { // 4 and 8 immediately cause java.lang.OutOfMemoryError
                System.out.println();
                startTime = endTime = 0;
                Arrays.fill(previousTime,0);
                System.out.println("Radix Sort " + d + " Results:");
                System.out.print("N\t");
                for(int k = 6; k <= 48; k*= 2) if(k % d == 0) System.out.print("k=" + k + "\tDoubling Ratio\t");
                System.out.println();
                try {
                    for(int N = 1; endTime-startTime < timeout; N*=2) {
                        System.out.print(N + "\t");
                        for(int k = 6; k <= 48; k*=2) {
                            arr = GenerateTestList(N,k,minV,maxV);
                            startTime = getCpuTime();
                            Sorting.RadixSort(arr,d);
                            endTime = getCpuTime();
                            System.out.print((endTime - startTime) + "\t" + (previousTime[(int) Math.log(k / 6)] == 0 ? "NA" : ((double)(endTime - startTime) / previousTime[(int) Math.log(k / 6)])) + "\t");
                            previousTime[(int) Math.log(k / 6)] = endTime - startTime;
                        }
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("\nRan out of memory\nContinuing...");
                }
            }
            System.out.println("\nAll Testing Done!");
        }
    }

    private static Boolean VerificationTesting() {
        String[] arr;
        System.out.println("Visual Verification starting...");

        arr = GenerateTestList(10, 3, minV, maxV);
        System.out.println("Insertion Sort before: " + Arrays.toString(arr));
        Sorting.InsertionSort(arr);
        System.out.println("Insertion Sort after: " + Arrays.toString(arr));

        arr = GenerateTestList(10, 3, minV, maxV);
        System.out.println("Merge Sort before: " + Arrays.toString(arr));
        Sorting.MergeSort(arr);
        System.out.println("Merge Sort after: " + Arrays.toString(arr));

        arr = GenerateTestList(10, 3, minV, maxV);
        System.out.println("Quick Sort before: " + Arrays.toString(arr));
        Sorting.QuickSort(arr);
        System.out.println("Quick Sort after: " + Arrays.toString(arr));

        arr = GenerateTestList(10, 4, minV, maxV);
        System.out.println("Radix Sort before: " + Arrays.toString(arr));
        Sorting.RadixSort(arr,2);
        System.out.println("Radix Sort after: " + Arrays.toString(arr));

        System.out.println("\nSort Verification Function starting...");

        arr = GenerateTestList(100000,16,minV,maxV);
        Sorting.InsertionSort(arr);
        if(Sorting.IsSorted(arr))
            System.out.println("Insertion Sort Succeeded!");
        else {
            System.out.println("Insertion Sort Failed!");
            return false;
        }

        arr = GenerateTestList(100000,16,minV,maxV);
        Sorting.MergeSort(arr);
        if(Sorting.IsSorted(arr))
            System.out.println("Merge Sort Succeeded!");
        else {
            System.out.println("Merge Sort Failed!");
            return false;
        }

        arr = GenerateTestList(100000,16,minV,maxV);
        Sorting.QuickSort(arr);
        if(Sorting.IsSorted(arr))
            System.out.println("Quick Sort Succeeded!");
        else {
            System.out.println("Quick Sort Failed!");
            return false;
        }

        arr = GenerateTestList(100000,16,minV,maxV);
        Sorting.RadixSort(arr);
        if(Sorting.IsSorted(arr))
            System.out.println("Radix Sort Succeeded!");
        else {
            System.out.println("Radix Sort Failed!");
            return false;
        }

        System.out.println("\nAll Sorts Successful!\n");
        return true;
    }

    private static String[] GenerateTestList(int N, int k, int minV, int maxV) {
        String[] arr = new String[N];

        for(int i = 0; i < N; i++)
            arr[i] = GenerateRandomString(k,minV,maxV);

        return arr;
    }

    private static String GenerateRandomString(int k, int minV, int maxV) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < k; i++)
            sb.append((char)(rng.nextInt(maxV-minV+1)+minV));
        return sb.toString();
    }

    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
