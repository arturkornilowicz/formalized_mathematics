package org.mizar.notions;

import org.dom4j.*;
import org.mizar.xml_names.ESXAttributeName;

import java.util.*;

public class Notion {

    Element element;
    String kind;
    List<Notion> redefinitions = new ArrayList<>(); // and synonyms and antonyms

    public Notion(Element element, String kind) {
        this.element = element;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return kind + " " + element.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID);
    }

    private String spaces(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    public void print(int level) throws Exception {
        MapOfNotions.writer.write(element.attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID)
                + " " + element.attributeValue(ESXAttributeName.SPELLING)
                + " " + kind + "\n");
//        switch (element.getName()) {
//            case ESXElementName.MODE_DEFINITION:
//                System.out.println(element.elements().get(1).attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID)
//                        + " " + element.elements().get(1).attributeValue(ESXAttributeName.SPELLING)
//                        + " redefinition");
//                break;
//            case ESXElementName.MODE_SYNONYM:
//                System.out.println(element.elements().get(1).attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID)
//                        + " " + element.elements().get(1).attributeValue(ESXAttributeName.SPELLING)
//                        + " synonym");
//                break;
//        }
        for (Notion redefinition: redefinitions) {
            MapOfNotions.writer.write(spaces(level));
            redefinition.print(level+1);
        }
    }
}
