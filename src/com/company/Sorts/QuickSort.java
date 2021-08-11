package com.company;

import javax.swing.*;

public class QuickSort implements SortImplementation{

    ContentPane pane;
    private SwingWorker<Void, Void> animation;
    @Override
    public void sort(ContentPane pane) {
        this.pane = pane;
        animation = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.sortTime.setText("" + 0);
                pane.currentSort.setText("Quick sort");
                pane.timeComplexity.setText("<html>Time complexity <BR>Best O(N logN) " +
                                            "<BR> Average O(N logN)" +
                                            "<BR> Worst O(N^2)</html>");
                pane.spaceComplexity.setText("Space complexity O(logN)");
                pane.setStartTimer(System.currentTimeMillis());

                quickSort(pane.array, 0, pane.array.length - 1);

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
    private void quickSort(int[] array, int start, int end) {
        if(start >= end)
            return;

        int boundary = partition(array, start, end);

        quickSort(array, start, boundary - 1);
        quickSort(array, boundary + 1, end);
    }
    private int partition(int[] array, int start, int end){
        int boundary = start - 1;
        int pivot = array[end];

        for(int i = start; i <= end; i++){
            if(array[i] <= pivot)
                swap(array, i, ++boundary);
        }
        pane.barColors[end] = -1;
        pane.waitRepaint();
        pane.repaint();
        return boundary;
    }
    private void swap(int[] array, int firstIndex, int secondIndex){
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;

        pane.barColors[firstIndex] = 100;
        pane.barColors[secondIndex] = 100;
        pane.repaint();

    }
}
