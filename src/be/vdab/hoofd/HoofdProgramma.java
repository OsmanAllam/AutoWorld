/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.vdab.hoofd;

import be.vdab.util.Datum;
import be.vdab.util.DatumException;
import be.vdab.util.Maat;
import be.vdab.util.Volume;
import be.vdab.util.VolumeException;
import be.vdab.util.mens.Mens;
import be.vdab.util.mens.MensException;
import static be.vdab.util.mens.Rijbewijs.*;
import be.vdab.voertuigen.Personenwagen;
import be.vdab.voertuigen.Pickup;
import be.vdab.voertuigen.Voertuig;
import be.vdab.voertuigen.Vrachtwagen;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author osman.allam
 */
public class HoofdProgramma {
    final static Mens BESTUURDER_A = new Mens("Andree", A);
    final static Mens BESTUURDER_AB = new Mens("Amadeus", A, B);
    final static Mens BESTUURDER_B = new Mens("Bernard", B);
    final static Mens BESTUURDER_BC = new Mens("Beatrice-Clothilde", B, C);
    final static Mens BESTUURDER_C = new Mens("Catherina", C);
    final static Mens BESTUURDER_D = new Mens("Didier", D);
    final static Mens BESTUURDER_BE = new Mens("Beatrice-Emanuella", BE);
    final static Mens BESTUURDER_BBE = new Mens("Babette-Emanuella", B, BE);
    final static Mens BESTUURDER_CE = new Mens("Cederic-Eduard", CE);
    final static Mens BESTUURDER_DE = new Mens("Dominique-Emille", CE);
    final static Mens BESTUURDER_BBECCE = new Mens("Ammelie", B, BE, C, CE);
    final static Mens INGEZETENE_A = new Mens("Anita");
    final static Mens INGEZETENE_B = new Mens("Bert");
    final static Mens INGEZETENE_C = new Mens("Christina");
    final static Mens INGEZETENE_D = new Mens("Duts");
    final static Mens INGEZETENE_E = new Mens("Elsa");
    final static Mens INGEZETENE_F = new Mens("Fred");
    final static Mens INGEZETENE_G = new Mens("Gerda");
    final static Mens INGEZETENE_H = new Mens("Hedwig");
    final static Mens INGEZETENE_I = new Mens("Ingrid");
    
    static Datum DATUM_1;
    static Datum DATUM_2;
    static Datum DATUM_3;
    static Datum DATUM_4;
    static Datum DATUM_5;
    static Datum DATUM_6;
    
    static Volume VOLUME_1;
    static Volume VOLUME_2;
    static Volume VOLUME_3;
    static Volume VOLUME_4;
    
    static {
        try {
            DATUM_1 = new Datum(1, 1, 2000);
            DATUM_2 = new Datum(2, 1, 2000);
            DATUM_3 = new Datum(3, 1, 2000);
            DATUM_4 = new Datum(4, 1, 2000);
            DATUM_5 = new Datum(5, 1, 2000);
            DATUM_6 = new Datum(6, 1, 2000);
        }
        catch (DatumException e) {
            System.out.printf("Caught DatumException: %s", e.getMessage());
            System.exit(1);
        }
    }
    
    static {
        try {
            VOLUME_1 = new Volume(100, 100, 100, Maat.centimeter);
            VOLUME_2 = new Volume( 90,  90,  90, Maat.centimeter);
            VOLUME_3 = new Volume( 80,  80,  80, Maat.centimeter);
            VOLUME_4 = new Volume( 70,  70,  70, Maat.centimeter);
        } catch (VolumeException e) {
            System.out.printf("Caught VolumeException: %s", e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) throws DatumException {
        SortedSet<Voertuig> voertuigen;
        voertuigen = new TreeSet<>();
        
        try {
        voertuigen.add(new Personenwagen("opel", DATUM_1, 1000, 5, Color.YELLOW, BESTUURDER_A, INGEZETENE_A, INGEZETENE_B));
        voertuigen.add(new Personenwagen("opel", DATUM_2, 1050, 4, Color.RED, BESTUURDER_B, INGEZETENE_C, INGEZETENE_D));
        voertuigen.add(new Pickup("GMC", DATUM_3, 2000, 3, Color.WHITE, VOLUME_1, BESTUURDER_AB));
        voertuigen.add(new Pickup("FORD", DATUM_3, 2100, 3, Color.RED, VOLUME_2, BESTUURDER_BBE, INGEZETENE_E, INGEZETENE_F));
        voertuigen.add(new Vrachtwagen("MAN", DATUM_5, 4000, 2, VOLUME_3, 10000, 2, BESTUURDER_D, INGEZETENE_G));
        voertuigen.add(new Vrachtwagen("SCANIA", DATUM_6, 4000, 3, VOLUME_4, 15000, 2, BESTUURDER_DE, INGEZETENE_G, INGEZETENE_H));
        } catch (IllegalArgumentException e) {
            System.out.printf("Caught IllegalArgumentException: %s", e.getMessage());
            System.exit(1);
        } catch (MensException e) {
            System.out.printf("Caught MensException: %s", e.getMessage());
            System.exit(1);
        }

        SortedSet<Voertuig> voertuigen_aankoop = new TreeSet<>(Voertuig.getAankoopprijsComparator());
        SortedSet<Voertuig> voertuigen_merk = new TreeSet<>(Voertuig.getMerkComparator());
        
        voertuigen_aankoop.addAll(voertuigen);
        voertuigen_merk.addAll(voertuigen);
        
        printVoertuigen("eerste set", voertuigen.iterator());
        printVoertuigen("tweede set", ((TreeSet<Voertuig>) voertuigen_aankoop).descendingIterator());
        printVoertuigen("derde set", ((TreeSet<Voertuig>) voertuigen_merk).iterator());
        
        // Serialization
        String serFileName = "wagenpark.ser";
        try (ObjectOutputStream  fh = new ObjectOutputStream(new FileOutputStream(serFileName))) {
            fh.writeObject(voertuigen);
        } catch (FileNotFoundException e) {
            System.out.printf("Caught FileNotFoundException: %s\n", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("Caught IOException: %s\n", e.getMessage());
            System.exit(1);
        }
        
        SortedSet<Voertuig> voertuigen_deserialized;
        try (ObjectInputStream  fh = new ObjectInputStream(new FileInputStream(serFileName))) {
            try {
                voertuigen_deserialized = (SortedSet < Voertuig >) fh.readObject();
                printVoertuigen("deserialized set", voertuigen_deserialized.iterator());
            } 
            catch (ClassNotFoundException e) {
                System.out.printf("Caught ClassNotFoundException: %s\n", e.getMessage());
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Caught FileNotFoundException: %s\n", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("Caught IOException: %s\n", e.getMessage());
            System.exit(1);
        }
    }
    
    static private void printVoertuigen(String header, Iterator<Voertuig> it) {
        System.out.println(header);
        
        int i = 0;
        while(it.hasNext()) {
            System.out.print(i++ + ": ");
            System.out.println(it.next().toString());
        }
    }
}
