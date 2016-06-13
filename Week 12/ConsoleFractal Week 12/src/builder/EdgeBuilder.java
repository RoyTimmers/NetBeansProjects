/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import calculate.*;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 *
 * @author jsf3
 */
public class EdgeBuilder implements Observer {

    KochFractal kf;
    int methode;
    boolean first;

    public EdgeBuilder(int level, int methode) throws FileNotFoundException, IOException {
        this.methode = methode;
        kf = new KochFractal();
        kf.addObserver(this);
        kf.setLevel(level);
        first = true;
        kf.generateBottomEdge();
        kf.generateLeftEdge();
        kf.generateRightEdge();
    }

    private void binarySave(Object e) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.bin", true);
        DataOutputStream dos = new DataOutputStream(fos);
        if(first)
        {
            dos.writeInt(kf.getLevel());
            first = false;
        }
        Edge edge = (Edge) e;
        dos.writeDouble(edge.X1);
        dos.writeDouble(edge.Y1);
        dos.writeDouble(edge.X2);
        dos.writeDouble(edge.Y2);
        dos.writeFloat((float) edge.color.getRed());
        dos.writeFloat((float) edge.color.getGreen());
        dos.writeFloat((float) edge.color.getBlue());
        dos.writeFloat((float) edge.color.getOpacity());
        dos.close();

    }

    private void bufferedBinarySave(Object e) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.bin", true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        if(first)
        {
            dos.writeInt(kf.getLevel());
            first = false;
        }
        Edge edge = (Edge) e;
        dos.writeDouble(edge.X1);
        dos.writeDouble(edge.Y1);
        dos.writeDouble(edge.X2);
        dos.writeDouble(edge.Y2);
        dos.writeFloat((float) edge.color.getRed());
        dos.writeFloat((float) edge.color.getGreen());
        dos.writeFloat((float) edge.color.getBlue());
        dos.writeFloat((float) edge.color.getOpacity());
        dos.close();

    }

    private void textSave(Object e)throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.txt", true);
        Edge edge = (Edge) e;
        if(first)
        {
            fw.write(Integer.toString(kf.getLevel())+ System.lineSeparator());
            first = false;
        }
        fw.write(Double.toString(edge.X1)+", ");
        fw.write(Double.toString(edge.Y1)+", ");
        fw.write(Double.toString(edge.X2)+", ");
        fw.write(Double.toString(edge.Y2)+", ");
        fw.write(Double.toString(edge.color.getRed())+", ");
        fw.write(Double.toString(edge.color.getGreen())+", ");
        fw.write(Double.toString(edge.color.getBlue())+", ");
        fw.write(Double.toString(edge.color.getOpacity()));
        fw.write(System.lineSeparator());
        fw.close();

    }

    private void bufferedTextSave(Object e) throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.txt", true);
        BufferedWriter out = new BufferedWriter(fw);
        Edge edge = (Edge) e;
        if(first)
        {
            fw.write(Integer.toString(kf.getLevel())+ System.lineSeparator());
            first = false;
        }
        fw.write(Double.toString(edge.X1)+", ");
        fw.write(Double.toString(edge.Y1)+", ");
        fw.write(Double.toString(edge.X2)+", ");
        fw.write(Double.toString(edge.Y2)+", ");
        fw.write(Double.toString(edge.color.getRed())+", ");
        fw.write(Double.toString(edge.color.getGreen())+", ");
        fw.write(Double.toString(edge.color.getBlue())+", ");
        fw.write(Double.toString(edge.color.getOpacity()));
        fw.write(System.lineSeparator());
        out.close();
    }

    @Override
    public void update(Observable o, Object edge) {
        switch(methode){
            case 1: {
            try {
                binarySave(edge);
            } catch (IOException ex) {
                Logger.getLogger(EdgeBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;
            case 2: {
            try {
                bufferedBinarySave(edge);
            } catch (IOException ex) {
                Logger.getLogger(EdgeBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;
            case 3: {
            try {
                textSave(edge);
            } catch (IOException ex) {
                Logger.getLogger(EdgeBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;
            case 4: {
            try {
                bufferedTextSave(edge);
            } catch (IOException ex) {
                Logger.getLogger(EdgeBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;
        }
    }

}
