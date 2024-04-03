package org.mizar.pub;

import org.dom4j.*;
import org.dom4j.io.*;
import org.mizar.application.TranslationApplication;
import org.mizar.files.*;
import org.mizar.latex.*;
import org.mizar.patterns.Patterns;
import org.mizar.xml_names.*;

import java.io.*;
import java.util.*;

// Creates new PUB with absolute values
public class Pub2Pub extends TranslationApplication {

    public Pub2Pub() {
        super("inputs/pub.xml");
    }

    private Args formatDes2args(String string) {
        String[] tab = string.split("[ \\(, \\), \\[, \\] ]");
        return new Args(tab[0].substring(0,1),Integer.parseInt(tab[0].substring(1)),Integer.parseInt(tab[2]),Integer.parseInt(tab[1]),Integer.parseInt(tab[3]));
    }

    private final String newPubName = "inputs/pub_new.xml";
    private final String missingTranslations = "missingTranslations.txt";
    private Document newDocument;

    private Element newTranslation;

    private void createNewFile() {
        File f = new File(newPubName);
        if (!f.exists()) {
            newDocument = DocumentHelper.createDocument(DocumentHelper.createElement(TranslationNames.TRANSLATIONS));
        } else {
            newDocument = loadDocument(newPubName);
        }
    }

    private void sortTranslations() {
        String currentVocName = "";
        Set<Translation> elementList = new TreeSet<>();
        for (Element element: newDocument.getRootElement().elements()) {
            elementList.add(new Translation(element));
        }
//        Collections.sort(elementList);
        Document document1 = DocumentHelper.createDocument(DocumentHelper.createElement(TranslationNames.TRANSLATIONS));
        for (Translation translation: elementList) {
            if (!currentVocName.equals(translation.getVocName())) {
                currentVocName = translation.getVocName();
                document1.getRootElement().addComment(currentVocName);
            }
            document1.getRootElement().add(translation.getElement().createCopy());
        }
        writeNewFile(newPubName + ".sorted",document1);
    }

    private void addAttribute(Element pattern, String attrName) {
        if (pattern.attribute(attrName) != null) {
            newTranslation.addAttribute(attrName, pattern.attributeValue(attrName));
        }
    }

    public static void main(String[] args) {
        Pub2Pub app = new Pub2Pub();
        app.init(args[0] + ".esx");
        app.createNewFile();

        app.formats = new Formats(args[0] + ".frx");
        app.symbols = new Symbols(args[0] + ".dcx");

        List<Node> nodes;
        Element pattern;
        Args arguments;
        Format format;
        Translation translation = null;
        for (Patterns patterns: Patterns.values()) {
//            System.out.println(patterns);
            nodes = Pub2Pub.document.selectNodes("//" + patterns.getRepr() + "/" + patterns.getESXname());
            for (Node node: nodes) {
                pattern = (Element) node;
                arguments = app.formatDes2args(pattern.attributeValue(ESXAttributeName.FORMATDES));

                format = TranslationApplication.formats.format(pattern.attributeValue(ESXAttributeName.FORMATNR));

//                System.out.println(format.getElement().attributeValue(ESXAttributeName.KIND));
                translation = translations.translationWSX(format, format.getElement().attributeValue(ESXAttributeName.KIND));

                if (translation == null) {
                    try {
                        System.out.println(format.getElement().attributeValue(ESXAttributeName.KIND) + " " + format);
                        FileWriter f = new FileWriter(app.missingTranslations, true);
                        f.write(pattern + "\n\n");
                        f.write(format + "\n\n");
                        f.write(format.symbol() + "\n\n\n\n");
                        f.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                app.newTranslation = DocumentHelper.createElement(TranslationNames.TRANSLATION);

                for (int i = 0; i < translation.getElement().attributeCount(); i++) {
                    app.newTranslation.addAttribute(translation.getElement().attribute(i).getName(),translation.getElement().attribute(i).getValue());
                }

                for (int i = 0; i < translation.getElement().elements().size(); i++) {
                    app.newTranslation.add(translation.getElement().elements().get(i).createCopy());
                }

//                app.newTranslation.addAttribute(ESXAttributeName.ABSOLUTEPATTERNMMLID,pattern.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID))
//                        .addAttribute(ESXAttributeName.ABSOLUTECONSTRMMLID,pattern.attributeValue(ESXAttributeName.ABSOLUTECONSTRMMLID));

                app.addAttribute(pattern,ESXAttributeName.ABSOLUTEPATTERNMMLID);
                app.addAttribute(pattern,ESXAttributeName.ABSOLUTECONSTRMMLID);
                app.addAttribute(pattern,ESXAttributeName.ABSOLUTEORIGPATTERNMMLID);
                app.addAttribute(pattern,ESXAttributeName.ABSOLUTEORIGCONSTRMMLID);

                app.newDocument.getRootElement().add(app.newTranslation);

            }
        }
//        System.out.println(app.newDocument.asXML());

        app.sortTranslations();
        app.writeNewFile(app.newPubName,app.newDocument);
    }
}
