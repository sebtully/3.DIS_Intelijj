package Opgave_2;

import java.util.ArrayList;
import java.util.List;


public class FletteSortering {

    public void parallelMergeSort(List<Integer> list, int l, int h, int depth) {
        if (depth <= 0 || l >= h) {
            mergesort(list, l, h);
        } else {
            int m = (l + h) / 2;

            MergeSortThreadLeft leftThread = new MergeSortThreadLeft(list, l, m);
            MergeSortThreadRight rightThread = new MergeSortThreadRight(list, m + 1, h);

            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(list, l, m, h);
        }
    }

    public void mergesort(List<Integer> list, int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            mergesort(list, low, middle);
            mergesort(list, middle + 1, high);
            merge(list, low, middle, high);
        }
    }


    private void merge(List<Integer> list, int low, int middle, int high) {
        List<Integer> temp = new ArrayList<Integer>();
        int i = low;
        int j = middle + 1;
        while (i <= middle && j <= high) {
            if (list.get(i).compareTo(list.get(j)) <= 0) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }
        while (i <= middle) {
            temp.add(list.get(i));
            i++;
        }
        while (j <= high) {
            temp.add(list.get(j));
            j++;
        }
        for (int k = 0; k < temp.size(); k++) {
            list.set(low + k, temp.get(k));
        }
    }
}