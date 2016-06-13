/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolefractal;

import builder.EdgeBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author jsf3
 */
public class ConsoleFractal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {

            File file = new File("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.bin");

            if (file.delete()) {
                System.out.println("Old " + file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
            
            file = new File("C:\\Users\\royti\\OneDrive\\Documenten\\NetBeansProjects\\Week 12\\Edges\\edge.txt");

            if (file.delete()) {
                System.out.println("Old " + file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
        EdgeBuilder edgeBuilder = new EdgeBuilder(3, 3);
        System.out.println("Edges saved");
    }

}
