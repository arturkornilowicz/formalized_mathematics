package org.mizar.proofs;

import org.dom4j.*;
import org.mizar.application.FormalizedMathematics;
import org.mizar.classes.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

import java.util.*;

public class Proof {

    private static int theoremNumberReferenced(String serialNr) {
        Element theorem, label;
        for (Node node: FormalizedMathematics.theoremItems) {
            theorem = (Element)node;
            label = theorem.element(ESXElementName.PROPOSITION).element(ESXElementName.LABEL);
            if (label.attributeValue(ESXAttributeName.SERIALNR).equals(serialNr)) {
                //TODO Check canceled theorems
                return Integer.parseInt(theorem.attributeValue(ESXAttributeName.MMLID).split(":")[1]);
            }
        }
        throw new RuntimeException("Theorem " + serialNr + " not found");
    }

    private static PrivatePredicateDefinition privatePredicate(TheoremItem theoremItem, Element justification) {
        PrivatePredicateDefinition result = null;
        //TODO incorrect predicate
        Element element = (Element)theoremItem.getElement().selectSingleNode(".//" + ESXElementName.PRIVATE_PREDICATE_FORMULA);
        System.out.println(element.attributeValue(ESXAttributeName.SERIALNR));
        Element node;
        for (Node e: FormalizedMathematics.privatePredicates) {
            node = (Element)e;
            System.out.println(node.element(ESXElementName.VARIABLE).attributeValue(ESXAttributeName.SERIALNR));
            if (node.element(ESXElementName.VARIABLE).attributeValue(ESXAttributeName.SERIALNR).equals(element.attributeValue(ESXAttributeName.SERIALNR))) {
                result = new PrivatePredicateDefinition(node);
            }
        }
        return result;
    }

    public static String isProvedByScheme(TheoremItem theoremItem) {
        String result = "";
        if (theoremItem.getJustification().getElement().getName().equals(ESXElementName.BLOCK)) {
            Element block = theoremItem.getJustification().getElement();
            List<Node> items = theoremItem.getElement().selectNodes(".//" + ESXElementName.ITEM);
            if (items.size() > 2) {
                Element justification = ((Element) items.get(items.size() - 2)).elements().get(0).elements().get(1);
                System.out.println(theoremItem.getJustification().getElement().getName());
                System.out.println(theoremItem.getElement().attributeValue(ESXAttributeName.MMLID));
                System.out.println(justification.getName());
                if (true) {
                    result += "ALA";
                    return result.equals("") ? "" : privatePredicate(theoremItem, justification).texRepr(RepresentationCase.PREDICATE_IN_SCHEME)
                            + " The proof goes by the scheme of mathematical induction "
                            + justification.attributeValue(ESXAttributeName.MMLID).replace("_","\\_")
                            + ". ";
                }
            }
        }
        return "";
    }
    public static String isProvedByByLocalTheorems(TheoremItem theoremItem) {
        String result = "";
        List<Element> localTheorems;
        if (theoremItem.getJustification().getElement().getName().equals(ESXElementName.STRAIGHTFORWARD_JUSTIFICATION)) {
            localTheorems = theoremItem.getJustification().getElement().elements(ESXElementName.LOCAL_REFERENCE);
            if (localTheorems.size() > 0) {
                int i;
                for (i = 0; i < localTheorems.size() - 1; i++) {
                    result += theoremNumberReferenced(localTheorems.get(i).attributeValue(ESXAttributeName.SERIALNR)) + ", ";
                }
                result += theoremNumberReferenced(localTheorems.get(i).attributeValue(ESXAttributeName.SERIALNR));
            }
        }
        return result.equals("") ? "" : "The theorem is a~consequence of (" + result + ").";
    }

    public static String proof(TheoremItem theoremItem) {
        // TODO
//        String beginning = "\nPROOF: ";
//        String ending = " " + LaTeX.ensureMath(LaTeX.qed());
//        String isProvedByScheme = isProvedByScheme(theoremItem);
//        if (!isProvedByScheme.equals("")) {
//            return beginning + isProvedByScheme + ending;
//        } else {
//            String isProvedByByLocalTheorems = isProvedByByLocalTheorems(theoremItem);
//            if (!isProvedByByLocalTheorems.equals("")) {
//                return isProvedByByLocalTheorems;
//            } else {
//            }
//        }
        return "";
    }
}
