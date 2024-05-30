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
    
    private Color assignedPartitionColor = Color.ORANGE;
    private Color unassignedPartitionColor = Color.BLUE;
    
    public void setAssignedPartitionColor(Color color) {
        this.assignedPartitionColor = color;
        repaint();
    }

    public void setUnassignedPartitionColor(Color color) {
        this.unassignedPartitionColor = color;
        repaint();
    }

    public void addPartition(int size, boolean assigned) {
        int[] newPartitionSizes = new int[partitionSizes.length + 1];
        boolean[] newIsAssigned = new boolean[isAssigned.length + 1];
        
        System.arraycopy(partitionSizes, 0, newPartitionSizes, 0, partitionSizes.length);
        System.arraycopy(isAssigned, 0, newIsAssigned, 0, isAssigned.length);
        
        newPartitionSizes[newPartitionSizes.length - 1] = size;
        newIsAssigned[newIsAssigned.length - 1] = assigned;
        
        partitionSizes = newPartitionSizes;
        isAssigned = newIsAssigned;
        
        repaint(); 
    }
    
    private int memorySize;
    private int partitions;
    private String unit;
    private int[] partitionSizes;
    private boolean[] isAssigned;

    public MemoryPanel(int memorySize, int partitions, String unit) {
        if (partitions <= 0) {
            throw new IllegalArgumentException("El número de particiones debe ser mayor que cero.");
        }
        this.memorySize = memorySize;
        this.partitions = partitions;
        this.unit = unit;
        this.partitionSizes = new int[partitions];
        this.isAssigned = new boolean[partitions];
        generateRandomPartitionSizes();
        assignRandomPartitions();
        setPreferredSize(new Dimension(200, memorySize * 10 + 20));
    }
    
    public MemoryPanel(int memorySize, int partitions, String unit, int[] partitionSizes, boolean[] isAssigned) {
        if (partitions <= 0) {
            throw new IllegalArgumentException("El número de particiones debe ser mayor que cero.");
        }
        this.memorySize = memorySize;
        this.partitions = partitions;
        this.unit = unit;
        this.partitionSizes = partitionSizes;
        this.isAssigned = isAssigned;
        setPreferredSize(new Dimension(200, memorySize * 10 + 20));
    }

    private void generateRandomPartitionSizes() {
        Random random = new Random();
        int remainingSize = memorySize;
        int bigSections = Math.min(3, partitions);
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
    
    private int indexOfNewPartition = -1;
    
    public void setIndexOfNewPartition(int index) {
    this.indexOfNewPartition = index;
}
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int panelHeight = getHeight() - 20;
    int y = 10;

    for (int i = 0; i < partitions; i++) {
        if (i == indexOfNewPartition) {
            g.setColor(Color.YELLOW);
        } else if (isAssigned[i]) {
            g.setColor(Color.BLUE); 
        } else {
            g.setColor(new Color(0, 0, 0, 0));
        }
        g.fillRect(50, y, 100, partitionSizes[i] * 10);
        g.setColor(Color.BLACK);
        g.drawRect(50, y, 100, partitionSizes[i] * 10);
        g.drawString(partitionSizes[i] + " " + unit, 10, y + (partitionSizes[i] * 10) / 2);
        y += partitionSizes[i] * 10;
    }
    g.drawString("Capacidad: " + memorySize + " " + unit, getWidth() - 250, panelHeight);
}

    public int getMemorySize() {
        return memorySize;
    }

    public int getPartitions() {
        return partitions;
    }

    public String getUnit() {
        return unit;
    }

    public int[] getPartitionSizes() {
        return partitionSizes;
    }

    public boolean[] getAssignedPartitions() {
        return isAssigned;
    }

    public void assignPartition(int index, int newSize) {
        if (index >= 0 && index < partitions) {
            if (partitionSizes[index] >= newSize) {
                partitionSizes[index] -= newSize; 
                isAssigned[index] = true;
            } else {
                throw new IllegalArgumentException("El tamaño de la nueva partición excede el tamaño de la partición existente.");
            }
        } else {
            throw new IllegalArgumentException("Índice de partición fuera de rango.");
        }
    }

    public boolean isAssigned(int index) {
        if (index >= 0 && index < partitions) {
            return isAssigned[index];
        } else {
            throw new IllegalArgumentException("Índice de partición fuera de rango.");
        }
    }
}