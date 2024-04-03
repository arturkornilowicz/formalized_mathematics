package org.mizar.application;

import org.dom4j.*;
import org.dom4j.io.*;
import org.mizar.classes.XMLElement;

import java.io.*;

public class XMLApplication {

    public static String fileName;
    public static String dirName;
    public static Document document;
    public XMLElement xmlElement;

    @Deprecated
    public void init1(String fileName) {
        try {
            File inputFile = new File(fileName);
            SAXReader saxBuilder = new SAXReader();
            this.fileName = fileName;
            document = saxBuilder.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void init(String fileName) {
        String[] tab = fileName.split("/");
        this.fileName = fileName;
        //TODO consider all "/"
        this.dirName = tab[0];
        this.document = loadDocument(fileName);
    }

    public static Document loadDocument(String fileName) {
        try {
            File inputFile = new File(fileName);
            SAXReader saxBuilder = new SAXReader();
            return saxBuilder.read(inputFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public XMLElement buildTree() { return null; }
}
