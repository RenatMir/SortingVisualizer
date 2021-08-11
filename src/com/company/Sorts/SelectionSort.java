package com.company;

import javax.swing.*;

public class SelectionSort implements SortImplementation{
    private SwingWorker<Void, Void> animation;

    @Override
    public void sort(ContentPane pane) {
        int length = pane.arrayLength();

        animation = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int minIndex;
                int temp;
                pane.sortTime.setText("" + 0);
                pane.currentSort.setText("Selection sort");
                pane.timeComplexity.setText("<html>Time complexity <BR>Best O(N^2) " +
                                            "<BR> Average O(N^2)" +
                                            "<BR> Worst O(N^2)</html>");
                pane.spaceComplexity.setText("Space complexity O(1)");
                pane.setStartTimer(System.currentTimeMillis());

                for (int i = 0; i <= length - 1; i++) {
                    if (i == length - 1) {
                        pane.setFinishTimer(System.currentTimeMillis());
                        pane.setTimeElapsed(pane.getFinishTimer() - pane.getStartTimer());
                        pane.sortTime.setText("" + pane.getTimeElapsed());
                        pane.started = false;
                    }
                    minIndex = i;
                    for(int j = i + 1 ; j < length; j++){
                        if(pane.array[j] < pane.array[minIndex])
                            minIndex = j;

                        pane.barColors[i] = 100;
                        pane.barColors[minIndex] = 100;

                        pane.waitRepaint();
                    }
                    temp = pane.array[i];
                    pane.array[i] = pane.array[minIndex];
                    pane.array[minIndex] = temp;


                    pane.barColors[i] = - 1;
                    pane.repaint();

                }
                return null;

            }
        };animation.execute();

    }
}
