package org.mizar.misc;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.mizar.xml_names.*;

public class Errors {

    private static final Set<String> errors = new TreeSet<>();
    private static final Set<String> unknowns = new TreeSet<>();
    private static int errorNbr;
    private static int unknownNbr;

    private final String fileName;

    private static final String fileWithComments = "esx_errors.txt";

    public Errors(String fileName) {
        this.fileName = fileName;
    }

    public static void error(Element e, String kind) {
        errorNbr++;
        errors.add(kind + ": " + e.getName());
    }

    public static void unknown(Class<?> class1) {
        unknownNbr++;
        unknowns.add(class1.getName());
    }

    public static void printErrors() {
        System.out.println(errorNbr + " errors found:\n" + errors);
    }

    public void writeErrors() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(fileWithComments, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println("#" + fileName);
            for (String error : errors)
                out.println("  ERROR: " + error);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printUnknowns() {
        System.out.println(unknownNbr + " unknowns found:\n" + unknowns);
    }

    public static void logException(Exception exception, String comment) {
        try {
            FileWriter fileWriter = new FileWriter(fileWithComments,true);
            fileWriter.write("RTE " + comment + ": " + exception + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logUnknown(Class<?> class1) {
        try {
            FileWriter fileWriter = new FileWriter(fileWithComments,true);
            fileWriter.write("UNKNOWN " + class1.getName() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logMissing(Element element) {
        try {
            FileWriter fileWriter = new FileWriter(fileWithComments,true);
            fileWriter.write("MISSING "
                    + element.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID) + " "
                    + element.attributeValue(ESXAttributeName.SPELLING)
                    + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
