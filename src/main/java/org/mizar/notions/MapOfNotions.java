package org.mizar.notions;

import org.dom4j.*;
import org.mizar.application.XMLApplication;
import org.mizar.patterns.Patterns;
import org.mizar.xml_names.*;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class MapOfNotions {

    String mmllar = "inputs/mml.lar";
    String fileName;
    String path = "test/";
    String xpath;
    String outputFileName;

    List<Notion> modes = new ArrayList<>(); // only patterns
    List<Notion> modesAll = new ArrayList<>(); // only patterns

    static FileWriter writer;

    public MapOfNotions() {
    }

    void print(int level) throws Exception {
        writer = new FileWriter(outputFileName);
        writer.write("PRINT " + xpath + "\n");
        for (Notion notion: modes) {
            notion.print(level+1);
        }
        writer.close();
        System.out.println(outputFileName + " written");
    }

    Notion findNotion(Element element) {
//        System.out.println("find = " + element.getName() + " " + element.attributeValue(ESXAttributeName.ABSOLUTEORIGPATTERNMMLID) + " in " + fileName + " " + element.attributeValue(ESXAttributeName.XMLID));
//        System.out.println(modesAll);
//        System.out.println(element.attributeValue(ESXAttributeName.ABSOLUTEORIGPATTERNMMLID));

            for (Notion notion: modesAll) {
//                System.out.println(notion.element.getName());
//                System.out.println(notion.element.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID));
//                System.out.println(notion.element.attributeValue(ESXAttributeName.XMLID));
                if (notion.element.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID)
                    .equals(element.attributeValue(ESXAttributeName.ABSOLUTEORIGPATTERNMMLID))) {
                return notion;
            }
        }
        throw new RuntimeException("No notion found " + element.getName() + " " + element.attributeValue(ESXAttributeName.XMLID) + " in " + fileName);
    }

    void processFile(String fileName) {
        Document document;
        List<Node> nodes;

        System.out.println("Processing " + fileName);
        this.fileName = fileName;
        document = XMLApplication.loadDocument(path + fileName + ".esx");

//        for (Patterns pattern: Patterns.values()) {
//            System.out.println("\t" + pattern.getESXname());
//        }
//        System.out.println("\t" + Patterns.MODE_DEFINITION.getRepr());
//        System.out.println("\t" + Patterns.MODE_DEFINITION.getESXname());

        nodes = document.getRootElement().selectNodes(xpath);

        Element element;
        Notion newNotion;

        for (Node node : nodes) {
            element = (Element) node;
//            System.out.println("aaa" + element.getName());
            //TODO
            switch (element.getName()) {
                case ESXElementName.MODE_DEFINITION, ESXElementName.PREDICATE_DEFINITION, ESXElementName.ATTRIBUTE_DEFINITION:
                    if (element.element(ESXElementName.REDEFINE).attributeValue(ESXAttributeName.OCCURS).equals("false")) {
                        newNotion = new Notion(element.elements().get(1),NotionKind.DEFINITION);
                        modes.add(newNotion);
                        modesAll.add(newNotion);
                    } else {
                        newNotion = new Notion(element.elements().get(1),NotionKind.REDEFINITION);
                        modesAll.add(newNotion);
                        findNotion(element.elements().get(1)).redefinitions.add(newNotion);
                    }
                    break;
                case ESXElementName.MODE_SYNONYM, ESXElementName.PRED_SYNONYM, ESXElementName.ATTR_SYNONYM:
                    newNotion = new Notion(element.elements().get(0),NotionKind.SYNONYM);
//                    modes.add(newNotion);
                    modesAll.add(newNotion);
//                    System.out.println(modesAll);
//                    System.out.println(element.elements().get(1).elements().get(0).getName());
//                    System.out.println(element.elements().get(1).elements().get(0).attributeValue(ESXAttributeName.XMLID));
//                    System.out.println(findNotion(element.elements().get(0)));
                    findNotion(element.elements().get(0)).redefinitions.add(newNotion);
                    break;
                case ESXElementName.PRED_ANTONYM, ESXElementName.ATTR_ANTONYM:
                    newNotion = new Notion(element.elements().get(0),NotionKind.ANTONYM);
                    modesAll.add(newNotion);
                    findNotion(element.elements().get(0)).redefinitions.add(newNotion);
                    break;
            }
        }
    }

    void processNotionKind(String xpath, String outputFileName) throws Exception {
        modes.clear();
        modesAll.clear();
        this.xpath = xpath;
        this.outputFileName = outputFileName;

        Scanner sc = new Scanner(new File(mmllar));
        while (sc.hasNextLine()) {
            processFile(sc.nextLine());
        }
        sc.close();
        print(1);
    }

    public static void main(String[] args) {

        try {
            MapOfNotions app = new MapOfNotions();

//            app.processNotionKind(".//" + Patterns.MODE_DEFINITION.getRepr()
//                    + " | .//" + Patterns.MODE_SYNONYM.getRepr(),
//                    "outputs/modes.txt");
//
//            app.processNotionKind(".//" + Patterns.PREDICATE_DEFINITION.getRepr()
//                            + " | .//" + Patterns.PRED_SYNONYM.getRepr()
//                            + " | .//" + Patterns.PRED_ANTONYM.getRepr(),
//                    "outputs/preds.txt");

            app.processNotionKind(".//" + Patterns.ATTRIBUTE_DEFINITION.getRepr()
                            + " | .//" + Patterns.ATTR_SYNONYM.getRepr()
                            + " | .//" + Patterns.ATTR_ANTONYM.getRepr(),
                    "outputs/attrs.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
