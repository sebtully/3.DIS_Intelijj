package Opgave_2;

import java.util.List;

public class MergeSortThreadLeft extends Thread {
    private FletteSortering fletteSortering;
    private List<Integer> list;
    private int low, high;

    public MergeSortThreadLeft(List<Integer> list, int low, int high) {
        this.fletteSortering = fletteSortering;
        this.list = list;
        this.low = low;
        this.high = high;
    }

    @Override
    public void run() {
        fletteSortering.parallelMergeSort(list, low, high, 1);
    }
}


