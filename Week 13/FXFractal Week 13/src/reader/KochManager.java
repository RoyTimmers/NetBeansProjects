/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 * @author Sander Geraedts - Code Panda
 */
public class KochManager {

    private JSF31KochFractalFX application;
    private int level;
    private int methode;
    List<Edge> edges;

    public KochManager(JSF31KochFractalFX application, int methode) throws IOException {
        this.application = application;
        this.methode = methode;
        edges = new ArrayList<Edge>();

        switch (methode) {
            case 1:
                getBinary();
                break;
            case 2:
                getBufferedBinary();
                break;
            case 3:
                getText();
                break;
            case 4:
                getBufferedText();
                break;
            case 5:
                getMMF();
                break;
        }
    }

    public void drawEdges() throws IOException {
        application.clearKochPanel();
        for (Edge e : edges) {
            application.drawEdge(e);
        }

    }

    public void getBinary() throws IOException {

        FileInputStream fos = new FileInputStream("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 13\\Edges\\edge.bin");
        DataInputStream dos = new DataInputStream(fos);
        level = dos.readInt();
        while (dos.available() != 0) {
            double x1 = dos.readDouble();
            double y1 = dos.readDouble();
            double x2 = dos.readDouble();
            double y2 = dos.readDouble();
            float red = dos.readFloat();
            float green = dos.readFloat();
            float blue = dos.readFloat();
            float opa = dos.readFloat();
            Edge e = new Edge(x1, y1, x2, y2, new Color(red, green, blue, opa));
            edges.add(e);
        }
        dos.close();
    }

    public void getBufferedBinary() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 13\\Edges\\edge.bin");
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        level = dis.readInt();
        while (dis.available() != 0) {
            double x1 = dis.readDouble();
            double y1 = dis.readDouble();
            double x2 = dis.readDouble();
            double y2 = dis.readDouble();
            float red = dis.readFloat();
            float green = dis.readFloat();
            float blue = dis.readFloat();
            float opa = dis.readFloat();
            Edge e = new Edge(x1, y1, x2, y2, new Color(red, green, blue, opa));
            edges.add(e);
        }
        dis.close();

    }

    public void getText() throws FileNotFoundException {
        FileReader fr = new FileReader("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 13\\Edges\\edge.txt");
        Scanner scanner = new Scanner(fr);
        boolean firstline = true;

        while (scanner.hasNextLine()) {
            if (firstline) {
                level = Integer.parseInt(scanner.nextLine());
                firstline = false;
            }
            String[] edge = scanner.nextLine().split(",");
            double x1 = Double.parseDouble(edge[0]);
            double y1 = Double.parseDouble(edge[1]);
            double x2 = Double.parseDouble(edge[2]);
            double y2 = Double.parseDouble(edge[3]);
            float red = Float.parseFloat(edge[4]);
            float green = Float.parseFloat(edge[5]);
            float blue = Float.parseFloat(edge[6]);
            float opa = Float.parseFloat(edge[7]);
            edges.add(new Edge(x1, y1, x2, y2, new Color(red, green, blue, opa)));
        }
        scanner.close();
    }

    public void getBufferedText() throws FileNotFoundException {
        FileReader fr = new FileReader("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 13\\Edges\\edge.txt");
        BufferedReader br = new BufferedReader(fr);
        Scanner scanner = new Scanner(br);
        boolean firstline = true;

        while (scanner.hasNextLine()) {
            if (firstline) {
                level = Integer.parseInt(scanner.nextLine());
                firstline = false;
            }
            String[] edge = scanner.nextLine().split(",");
            double x1 = Double.parseDouble(edge[0]);
            double y1 = Double.parseDouble(edge[1]);
            double x2 = Double.parseDouble(edge[2]);
            double y2 = Double.parseDouble(edge[3]);
            float red = Float.parseFloat(edge[4]);
            float green = Float.parseFloat(edge[5]);
            float blue = Float.parseFloat(edge[6]);
            float opa = Float.parseFloat(edge[7]);
            edges.add(new Edge(x1, y1, x2, y2, new Color(red, green, blue, opa)));
        }
        scanner.close();

    }

    private void getMMF() throws IOException {
        RandomAccessFile ras = new RandomAccessFile("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 13\\Edges\\edge.ras", "r");
        FileChannel fc = ras.getChannel();
        MappedByteBuffer out;
        System.out.println(fc.size());
        out = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        level = out.getInt();
        while (out.hasRemaining()) {
                double x1 = out.getDouble();
                double y1 = out.getDouble();
                double x2 = out.getDouble();
                double y2 = out.getDouble();
                float red = out.getFloat();
                float green = out.getFloat();
                float blue = out.getFloat();
                float opa = out.getFloat();
                Edge e = new Edge(x1, y1, x2, y2, new Color(red, green, blue, opa));
                edges.add(e);
        }
    }

    public int getLevel() {
        return level;
    }

}
