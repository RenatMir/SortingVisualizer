package com.company;

import javax.swing.*;
import java.awt.*;

public class SortVisualizer{
    SortVisualizer(){
        JFrame frame = new JFrame();
        ContentPane pane = new ContentPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        frame.add(pane.leftPanel, BorderLayout.LINE_START);
        frame.add(pane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);


    }
    public static void sort(String sortName, ContentPane pane){
        switch(sortName){
            case "Bubble sort":
                new BubbleSort().sort(pane);
                break;
            case "Insertion sort":
                new InsertionSort().sort(pane);

                break;
            case "Selection sort":
                new SelectionSort().sort(pane);
                break;
            case "Merge sort":
                new MergeSort().sort(pane);
                break;
            case "Quick sort":
                new QuickSort().sort(pane);
                break;
        }


    }
    public static void main(String[] args){
        new SortVisualizer();
    }

}
