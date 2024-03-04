import Opgave_2.FletteSortering;


import java.util.ArrayList;
import java.util.Random;
import java.util.List;


class TestFletteSortering {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        Random r = new Random();
        for (int i = 0; i < 1000000; i++) {
            list.add(Math.abs(r.nextInt() % 10000));
        }
        //System.out.println(list);
        FletteSortering sort = new FletteSortering();
        long l1, l2;
        l1 = System.nanoTime();
        sort.mergesort(list, 0, list.size() - 1);
        l2 = System.nanoTime();
        System.out.println();
        System.out.println("Koeretiden var " + (l2 - l1) / 1000000);
        System.out.println();
        //System.out.println(list);

        // Parallel Merge Sort
        List<Integer> parallelList = new ArrayList<>(list);
        l1 = System.nanoTime();
        sort.parallelMergeSort(parallelList, 0, parallelList.size() - 1, 2);
        l2 = System.nanoTime();
        System.out.println("Med Threads - koeretiden var: " + (l2 - l1) / 1000000 + " ms");
    }
}


