package com.company;

import javax.swing.*;

public class BubbleSort implements SortImplementation {
    private SwingWorker<Void, Void> animation;
    int temp;

    @Override
    public void sort(ContentPane pane) {
        int length = pane.arrayLength();

        animation = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                pane.sortTime.setText("" + 0);
                pane.currentSort.setText("Bubble sort");
                pane.timeComplexity.setText("<html>Time complexity <BR>Best O(N) " +
                                            "<BR> Average O(N^2)" +
                                            "<BR> Worst O(N^2)</html>");
                pane.spaceComplexity.setText("Space complexity O(1)");
                pane.setStartTimer(System.currentTimeMillis());

                for(int i = 0; i < length - 1; i++) {


                    for (int j = 0; j < length - 1 - i; j++) {
                        if (pane.array[j] > pane.array[j + 1]) {
                            temp = pane.array[j];
                            pane.updateSingle(j, pane.array[j + 1]);
                            pane.updateSingle(j + 1, temp);
                        }
                    }
                    pane.barColors[length - 1 - i] = - 1;
                }
                pane.repaint();

                pane.setFinishTimer(System.currentTimeMillis());
                pane.setTimeElapsed(pane.getFinishTimer() - pane.getStartTimer());
                pane.sortTime.setText("" + pane.getTimeElapsed());
                pane.started = false;
                return null;
            }
        };animation.execute();
    }

}
