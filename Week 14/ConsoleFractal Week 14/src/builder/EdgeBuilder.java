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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    MappedByteBuffer out;

    public EdgeBuilder(int level, int methode) throws FileNotFoundException, IOException {
        this.methode = methode;
        kf = new KochFractal();
        kf.addObserver(this);
        kf.setLevel(level);
        first = true;
        out = null;
        kf.generateBottomEdge();
        kf.generateLeftEdge();
        kf.generateRightEdge();
        File dir = new File("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges");
        File[] files = dir.listFiles();
        File temp = files[0];

        if (temp.toString().endsWith("(BIP)")) {
            Files.move(temp.toPath(),temp.toPath().resolveSibling("edge.bin") ,StandardCopyOption.REPLACE_EXISTING);
        }
        if (temp.toString().endsWith("(TIP)")) {
            Files.move(temp.toPath(),temp.toPath().resolveSibling("edge.txt") ,StandardCopyOption.REPLACE_EXISTING);
        }
        if (temp.toString().endsWith("(RIP)")) {
            Files.move(temp.toPath(),temp.toPath().resolveSibling("edge.ras") ,StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void binarySave(Object e) throws FileNotFoundException, IOException {
        File f = new File("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges\\edge(BIP)");
        FileOutputStream fos = new FileOutputStream(f, true);
        DataOutputStream dos = new DataOutputStream(fos);
        if (first) {
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
        FileOutputStream fos = new FileOutputStream("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges\\edge(BIP)", true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        if (first) {
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

    private void textSave(Object e) throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges\\edge(TIP)", true);
        Edge edge = (Edge) e;
        if (first) {
            fw.write(Integer.toString(kf.getLevel()) + System.lineSeparator());
            first = false;
        }
        fw.write(Double.toString(edge.X1) + ", ");
        fw.write(Double.toString(edge.Y1) + ", ");
        fw.write(Double.toString(edge.X2) + ", ");
        fw.write(Double.toString(edge.Y2) + ", ");
        fw.write(Double.toString(edge.color.getRed()) + ", ");
        fw.write(Double.toString(edge.color.getGreen()) + ", ");
        fw.write(Double.toString(edge.color.getBlue()) + ", ");
        fw.write(Double.toString(edge.color.getOpacity()));
        fw.write(System.lineSeparator());
        fw.close();

    }

    private void bufferedTextSave(Object e) throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges\\edge(TIP)", true);
        BufferedWriter out = new BufferedWriter(fw);
        Edge edge = (Edge) e;
        if (first) {
            fw.write(Integer.toString(kf.getLevel()) + System.lineSeparator());
            first = false;
        }
        fw.write(Double.toString(edge.X1) + ", ");
        fw.write(Double.toString(edge.Y1) + ", ");
        fw.write(Double.toString(edge.X2) + ", ");
        fw.write(Double.toString(edge.Y2) + ", ");
        fw.write(Double.toString(edge.color.getRed()) + ", ");
        fw.write(Double.toString(edge.color.getGreen()) + ", ");
        fw.write(Double.toString(edge.color.getBlue()) + ", ");
        fw.write(Double.toString(edge.color.getOpacity()));
        fw.write(System.lineSeparator());
        out.close();
    }

    private void MMFSave(Object e) throws IOException {

        RandomAccessFile ras = new RandomAccessFile("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 14\\Edges\\edge(RIP)", "rw");
        FileChannel fc = ras.getChannel();
        Edge edge = (Edge) e;
        if (first) {
            System.out.println((long) (4 + (long) (3 * Math.pow(4, kf.getLevel())) * 42));
            out = fc.map(FileChannel.MapMode.READ_WRITE, 0, 4 + ((long) (3 * Math.pow(4, kf.getLevel())) * 42));
            out.putInt(kf.getLevel());
            first = false;
        }
        out.putDouble(edge.X1);
        out.putDouble(edge.Y1);
        out.putDouble(edge.X2);
        out.putDouble(edge.Y2);
        out.putFloat((float) edge.color.getRed());
        out.putFloat((float) edge.color.getGreen());
        out.putFloat((float) edge.color.getBlue());
        out.putFloat((float) edge.color.getOpacity());
        out.clear();
        fc.close();
        ras.close();
    }

    @Override
    public void update(Observable o, Object edge) {
        switch (methode) {
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
            case 5: {
                try {
                    MMFSave(edge);
                } catch (IOException ex) {
                    Logger.getLogger(EdgeBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
        }

    }
}
