package org.mizar.application;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.mizar.files.*;
import org.mizar.latex.*;

import java.io.FileWriter;

public class TranslationApplication extends XMLApplication {

    public static Translations translations;
    public static Formats formats;// = new Formats(args[0] + ".frx");
    public static Symbols symbols;// = new Symbols(args[0] + ".dcx");

    public TranslationApplication(String fileName) {
        translations = new Translations(fileName);
    }

    public TranslationApplication() {
        this("inputs/pubESX.xml");
    }

    public void writeNewFile(String fileName, Document document) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer;
        try {
//            writer = new XMLWriter(new FileWriter(fileName,false), format);
            writer = new XMLWriter(new FileWriter(fileName,false));
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
