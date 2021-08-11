package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;


import java.util.stream.IntStream;

public class ContentPane extends JPanel implements ActionListener, ChangeListener {
    private static final int LEFT_PANEL_WIDTH = 200;
    private static final int RIGHT_PANEL_WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static int BAR_WIDTH = 2;
    private static int NUM_OF_BARS = RIGHT_PANEL_WIDTH / BAR_WIDTH;
    private long DELAY = 100000;


    JPanel leftPanel;
    JButton start;
    JButton swapSidesButton;
    JButton newArrayButton;
    JComboBox<String> sortComboBox;
    JSlider arraySizeSlider;
    JSlider delaySlider;
    JLabel delayText;
    JLabel arraySizeText;
    JLabel sortAlgorithm;
    JLabel timer;
    JLabel sortTime;
    JLabel currentSort;
    JLabel timeComplexity;
    JLabel spaceComplexity;

    private int height;
    private int xBegin;
    private int yBegin;
    private int swap;
    private int temp;
    private boolean swapSides = false;
    public boolean started = false;
    private long startTimer;
    private long finishTimer;
    private long timeElapsed;
    private long delayStart;
    private long delayElapsed;

    protected int[] array;
    protected int[] barColors;

    ContentPane(){
        createArray();

        this.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH,HEIGHT));
        this.setBackground(Color.white);

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH,HEIGHT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(
                BorderFactory.createTitledBorder("Controls"));

        String[] sorts = {"Bubble sort", "Insertion sort", "Selection sort", "Merge sort", "Quick sort"};
        sortComboBox = new JComboBox<>(sorts);
        sortComboBox.setFocusable(false);
        sortComboBox.addActionListener(this);
        sortComboBox.setBounds(40,HEIGHT - 300,120,25);
        sortAlgorithm = new JLabel("Sorting algorithm:");
        sortAlgorithm.setBounds(40,HEIGHT - 325,120,25);

        arraySizeSlider = new JSlider(2,100, 5);
        arraySizeSlider.setPreferredSize(new Dimension(50, 150));
        arraySizeSlider.addChangeListener(this);
        arraySizeSlider.setBackground(Color.WHITE);
        arraySizeSlider.setBounds(45,HEIGHT - 100,120,35);
        arraySizeText = new JLabel("Size:");
        arraySizeText.setBounds(15,HEIGHT - 100,40,35);

        start = new JButton("Run Sort");
        start.addActionListener(this);
        start.setFocusable(false);
        start.setBounds(40,HEIGHT - 50,120,25);

        timer = new JLabel("Time in milliseconds : ");
        timer.setBounds(10,HEIGHT - 350,150,25);
        sortTime = new JLabel("" + timeElapsed);
        sortTime.setBounds(140,HEIGHT - 350,150,25);

        swapSidesButton = new JButton("Swap sides");
        swapSidesButton.addActionListener(this);
        swapSidesButton.setFocusable(false);
        swapSidesButton.setBounds(40,HEIGHT - 200,120,25);

        newArrayButton = new JButton("Generate Array");
        newArrayButton.addActionListener(this);
        newArrayButton.setFocusable(false);
        newArrayButton.setBounds(40,HEIGHT - 250,120,25);

        delaySlider = new JSlider(0,10000000, 100000);
        delaySlider.setPreferredSize(new Dimension(50, 150));
        delaySlider.addChangeListener(this);
        delaySlider.setBackground(Color.WHITE);
        delaySlider.setBounds(45,HEIGHT - 150,120,35);
        delayText = new JLabel("Delay:");
        delayText.setBounds(10,HEIGHT - 150,40,35);

        currentSort = new JLabel();
        currentSort.setBounds(10,HEIGHT - 475,150,25);
        timeComplexity = new JLabel("Time complexity");
        timeComplexity.setBounds(10,HEIGHT - 455,150,75);
        spaceComplexity = new JLabel("Space complexity");
        spaceComplexity.setBounds(10,HEIGHT - 375,150,25);


        leftPanel.add(sortComboBox);
        leftPanel.add(sortAlgorithm);
        leftPanel.add(start);
        leftPanel.add(arraySizeSlider);
        leftPanel.add(arraySizeText);
        leftPanel.add(delaySlider);
        leftPanel.add(delayText);
        leftPanel.add(timer);
        leftPanel.add(sortTime);
        leftPanel.add(swapSidesButton);
        leftPanel.add(newArrayButton);
        leftPanel.add(currentSort);
        leftPanel.add(timeComplexity);
        leftPanel.add(spaceComplexity);

    }

    private void createArray(){
        array = IntStream.rangeClosed(0, NUM_OF_BARS - 1).toArray();
        barColors = new int[NUM_OF_BARS];
        Arrays.fill(barColors, 0);
        shuffle();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < NUM_OF_BARS; i++) {
            height = array[i] * (int) (HEIGHT * 0.9) / (array.length);
            yBegin = (swapSides) ? 0 : HEIGHT-height;
            xBegin = i + 2 + (BAR_WIDTH - 1) * i;

            int val = barColors[i] * 2;
            if (val > 190)
                g.setColor(new Color(255, 255 - val, 255 - val));
            else if(val == -2)
                g.setColor(new Color(0, 100, 0));
            else
                g.setColor(new Color(51,51,51));

            g.fillRect(xBegin, yBegin, BAR_WIDTH, height);

            if (barColors[i] > 0) {
                barColors[i] -= 5;
            }
        }
    }
    public void waitRepaint(){
        setDelayStart(System.nanoTime());
        do{
            setDelayElapsed(System.nanoTime() - getDelayStart());
        }while(getDelayElapsed() < getDELAY());
        repaint();
    }

    private void shuffle(){
        Random random = new Random();
        for (int i = 0; i < NUM_OF_BARS; i++){
            swap = random.nextInt(NUM_OF_BARS - 1);
            temp = array[i];
            array[i] = array[swap];
            array[swap] = temp;
            barColors[i] = 0;
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start){
            if(!started) {
                started = true;
                SortVisualizer.sort(sortComboBox.getSelectedItem().toString(), this);
            }
        }
        if(e.getSource() == swapSidesButton){
            if(!started) {
                swapSides = !swapSides;
                repaint();
            }
        }
        if(e.getSource() == newArrayButton){
            if(!started) {
                shuffle();
                repaint();
            }
        }
    }
    public void updateSingle(int index, int value) {
        array[index] = value;
        barColors[index] = 100;
        waitRepaint();
    }
    @Override
    public void stateChanged(ChangeEvent e) {
            if(!started) {
                if(e.getSource() == arraySizeSlider) {
                    BAR_WIDTH = arraySizeSlider.getValue();
                    NUM_OF_BARS = RIGHT_PANEL_WIDTH / BAR_WIDTH;
                    createArray();
                    repaint();
                }
                if(e.getSource() == delaySlider){
                    setDELAY(delaySlider.getValue());
                }
            }
    }

    public int arrayLength(){
        return array.length;
    }

    public long getStartTimer() {
        return startTimer;
    }

    public void setStartTimer(long startTimer) {
        this.startTimer = startTimer;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public long getTimeElapsed() {
       return timeElapsed;
    }

    public long getFinishTimer() {
        return finishTimer;
    }

    public void setFinishTimer(long finishTimer) {
        this.finishTimer = finishTimer;
    }

    public long getDELAY() {
        return DELAY;
    }

    public void setDELAY(long DELAY) {
        this.DELAY = DELAY;
    }

    public long getDelayElapsed() {
        return delayElapsed;
    }

    public void setDelayElapsed(long delayElapsed) {
        this.delayElapsed = delayElapsed;
    }

    public long getDelayStart() {
        return delayStart;
    }

    public void setDelayStart(long delayStart) {
        this.delayStart = delayStart;
    }
}


