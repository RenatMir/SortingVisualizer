package com.company;

import javax.swing.*;
import java.util.Arrays;


public class MergeSort implements SortImplementation{
    private SwingWorker<Void, Void> animation;
    ContentPane pane;

    @Override
    public void sort(ContentPane pane) {
        this.pane = pane;
        animation = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                pane.sortTime.setText("" + 0);
                pane.currentSort.setText("Merge sort");
                pane.timeComplexity.setText("<html>Time complexity <BR>Best O(N logN) " +
                                            "<BR> Average O(N logN)" +
                                            "<BR> Worst O(N logN)</html>");
                pane.spaceComplexity.setText("Space complexity O(N)");
                pane.setStartTimer(System.currentTimeMillis());

                mergeSort(pane.array, 0, pane.arrayLength() - 1);

                pane.setFinishTimer(System.currentTimeMillis());
                pane.setTimeElapsed(pane.getFinishTimer() - pane.getStartTimer());
                pane.sortTime.setText("" + pane.getTimeElapsed());

                for(int j = pane.arrayLength() - 1; j >= 0; j--){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pane.barColors[j] = -1;
                    pane.repaint();
                }

                pane.started = false;
                return null;
            }
        };animation.execute();
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middleIndex = (left + right) / 2;

            mergeSort(array, left, middleIndex);
            mergeSort(array, middleIndex + 1, right);

            merge(array, left, middleIndex, right);
        }
    }

    private int[] getSubArray(int[] array, int begin, int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = array[begin + i];
        }
        return arr;
    }

    private void merge(int[] array, int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] leftArray = getSubArray(array, left, leftSize);
        int[] rightArray = getSubArray(array, middle + 1, rightSize);

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                pane.updateSingle(k++, leftArray[i++]);
            } else {
                pane.updateSingle(k++, rightArray[j++]);
            }
        }

        while (i < leftSize) {
            pane.updateSingle(k++, leftArray[i++]);
        }

        while (j < rightSize) {
            pane.updateSingle(k++, rightArray[j++]);
        }
    }
}
