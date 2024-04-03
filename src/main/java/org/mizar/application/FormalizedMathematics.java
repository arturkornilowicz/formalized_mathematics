package org.mizar.application;

import org.dom4j.*;
import org.jbibtex.*;
import org.mizar.classes.*;
import org.mizar.files.*;
import org.mizar.latex.*;
import org.mizar.misc.*;
import org.mizar.xml_names.*;

import java.io.*;
import java.util.*;

public class FormalizedMathematics extends TranslationApplication {

 //   public static String fileName;

    public static LaTeX laTeX;
    public static BibTeXEntry bibTeXEntry;
    public static Element fmYear;
    public static Sections sections;
    public static List<Node> theoremItems; // for referencing
    public static List<Node> privatePredicates; // for referencing
    public static List<Node> privateFunctors; // for referencing

    @Override
    public XMLElement buildTree() { return new TextProper(document.getRootElement()); }

    private BibTeXEntry loadBibFile() {
        BibTeXDatabase database = null;
        BibTeXParser bibtexParser;
        try {
            Reader reader = new FileReader(new File(FormalizedMathematics.fileName + ".bib"));
            bibtexParser = new BibTeXParser();
            database = bibtexParser.parse(reader);
            System.out.println(database.getObjects().size() + " bib item(s) loaded from " + FormalizedMathematics.fileName + ".bib" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<Key,BibTeXEntry> entryMap = database.getEntries();
        Collection<BibTeXEntry> entries = entryMap.values();
        return entries.iterator().next();
    }

    private Element loadFMYear() {
        String[] tab = fileName.split("/");
        System.out.println("//article[@id='" + tab[tab.length-1].toUpperCase() + "']");
        return (Element)XMLApplication.loadDocument("inputs/articles.xml").getRootElement().selectSingleNode("//article[@id='" + tab[tab.length-1].toUpperCase() + "']");
    }

    public static void main(String[] args) {
        try {
            FormalizedMathematics app = new FormalizedMathematics();
            app.fileName = args[0];
            Errors errors = new Errors(app.fileName);
            app.laTeX = new LaTeX();

            app.formats = new Formats(app.fileName + ".frx");
            app.symbols = new Symbols(app.fileName + ".dcx");
//            app.sections = new Sections(a.fileName + ".dcx");
            app.bibTeXEntry = app.loadBibFile();
            app.fmYear = app.loadFMYear();
            app.sections = new Sections();

            try {
                app.init(app.fileName + ".esx");
                app.xmlElement = app.buildTree();
                app.theoremItems = document.getRootElement().selectNodes("//" + ESXElementName.THEOREM_ITEM);
                app.privatePredicates = document.getRootElement().selectNodes("//" + ESXElementName.PRIVATE_PREDICATE_DEFINITION);
                app.privateFunctors = document.getRootElement().selectNodes("//" + ESXElementName.PRIVATE_FUNCTOR_DEFINITION);
                app.laTeX.preambule();
                app.xmlElement.run();
                app.laTeX.endFile();
            } catch (Exception exception) {
                Errors.logException(exception, app.fileName);
                exception.printStackTrace();
            } finally {
                errors.printErrors();
                errors.writeErrors();
                Errors.printUnknowns();
                app.laTeX.printFile(args[0] + ".tex");
                System.out.println(app.fileName + " processed");
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Enter file name");
            exception.printStackTrace();
        }
    }
}
