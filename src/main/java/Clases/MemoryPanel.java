/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Esaú_Pu
 */
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryPanel extends JPanel {
    private int size;
    private int partitions;
    private String unit;
    private int[] partitionSizes;
    private boolean[] isAssigned;

    public MemoryPanel(int size, int partitions, String unit) {
        this.size = size;
        this.partitions = partitions;
        this.unit = unit;
        partitionSizes = new int[partitions];
        isAssigned = new boolean[partitions];
        generateRandomPartitionSizes();
        assignRandomPartitions();
        setPreferredSize(new Dimension(200, size * 10 + 20));
    }

   private void generateRandomPartitionSizes() {
    Random random = new Random();
    int remainingSize = size;
    
    int bigSections = 3;
    
    int bigSize = remainingSize / (bigSections * 2);
    
    for (int i = 0; i < bigSections; i++) {
        int randomSize = bigSize + random.nextInt(bigSize > 1 ? bigSize - 1 : 1);
        partitionSizes[i] = randomSize;
        remainingSize -= randomSize;
    }
    
    for (int i = bigSections; i < partitions - 1; i++) {
        if (remainingSize <= 1) {
            partitionSizes[i] = 1;
            remainingSize--;
        } else {
            int randomSize = random.nextInt(remainingSize - (partitions - i - 1)) + 1;
            partitionSizes[i] = randomSize;
            remainingSize -= randomSize;
        }
    }
    
    partitionSizes[partitions - 1] = remainingSize;
}


    private void assignRandomPartitions() {
    Random random = new Random();
    List<Integer> partitionIndices = new ArrayList<>();
    for (int i = 0; i < partitions; i++) {
        partitionIndices.add(i);
    }
    Collections.shuffle(partitionIndices);

    isAssigned[partitionIndices.get(0)] = true;

    int assignedCount = random.nextInt(partitions / 2) + 1;
    for (int i = 1; i < assignedCount; i++) {
        if (i > 0 && partitionIndices.get(i) == partitionIndices.get(i - 1) + 1) {
            continue;
        }
        isAssigned[partitionIndices.get(i)] = true;
    }
}

    @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelHeight = getHeight() - 20; 
        int y = 10;
        boolean blackPartitionAssigned = false; 
        for (int i = 0; i < partitions; i++) {
            if (isAssigned[i]) {
                if (!blackPartitionAssigned) {
                    g.setColor(Color.ORANGE); 
                    g.fillRect(50, y, 100, partitionSizes[i] * 10);
                    g.setColor(Color.BLACK);
                    blackPartitionAssigned = true; 
                } else {
                    g.setColor(Color.BLUE);
                    g.fillRect(50, y, 100, partitionSizes[i] * 10);
                    g.setColor(Color.BLACK);
                }
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawRect(50, y, 100, partitionSizes[i] * 10);
            g.drawString(partitionSizes[i] + " " + unit, 10, y + (partitionSizes[i] * 10) / 2);
            y += partitionSizes[i] * 10;
        }
        
        g.drawString("Capacidad: " + size + " " + unit, getWidth() - 250, panelHeight);
    }
} 















