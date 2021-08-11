package com.company;

import javax.swing.*;

public class InsertionSort implements SortImplementation {
    private SwingWorker<Void, Void> animation;

    @Override
    public void sort(ContentPane pane) {
        int length = pane.arrayLength();

        animation = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.sortTime.setText("" + 0);
                pane.currentSort.setText("Insertion sort");
                pane.timeComplexity.setText("<html>Time complexity <BR>Best O(N) " +
                                            "<BR> Average O(N^2)" +
                                            "<BR> Worst O(N^2)</html>");
                pane.spaceComplexity.setText("Space complexity O(1)");
                pane.setStartTimer(System.currentTimeMillis());

                for (int i = 1; i <= length; i++) {

                    if(i == length) {
                        pane.setFinishTimer(System.currentTimeMillis());
                        pane.setTimeElapsed(pane.getFinishTimer() - pane.getStartTimer());
                        pane.sortTime.setText("" + pane.getTimeElapsed());

                        for(int j = length - 1; j >= 0; j--){
                            Thread.sleep(1);
                            pane.barColors[j] = -1;
                            pane.repaint();
                        }
                        pane.started = false;
                    }

                    int key = pane.array[i];
                    int j = i - 1;

                    while (j >= 0 && pane.array[j] > key) {
                        pane.updateSingle(j + 1, pane.array[j]);
                        j -= 1;
                        pane.barColors[i] = 100;
                        pane.waitRepaint();
                    }
                    pane.array[j + 1] = key;
                }
                return null;
            }
        };animation.execute();

    }
}
