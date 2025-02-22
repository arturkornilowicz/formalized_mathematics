package org.mizar.proofs;

import org.dom4j.*;
import org.mizar.application.FormalizedMathematics;
import org.mizar.application.XMLApplication;
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
        Element node;
        for (Node e: FormalizedMathematics.privatePredicates) {
            node = (Element)e;
            if (node.element(ESXElementName.VARIABLE).attributeValue(ESXAttributeName.SERIALNR).equals(element.attributeValue(ESXAttributeName.SERIALNR))) {
                result = new PrivatePredicateDefinition(node);
            }
        }
        return result;
    }

    private static String schemeAlias(String fileName, String schemeNr) {
        Document document = XMLApplication.loadDocument("test/" + fileName);
        List<Node> items2 = document.getRootElement().selectNodes(".//" + ESXElementName.ITEM);
        Element e, p;
        for (int i = 0; i < items2.size(); i++) {
            e = (Element)items2.get(i);
            if (e.attributeValue(ESXAttributeName.KIND).equals(ESXElementName.SCHEME_BLOCK_ITEM)) {
                if (e.elements().get(0).attributeValue(ESXAttributeName.MMLID).equals(schemeNr)) {
                    // TODO find correct PRAGMA
                    p = (Element) items2.get(i - 1);
                    if (p.attributeValue(ESXAttributeName.KIND).equals(ESXElementName.PRAGMA)) {
                        if (p.elements().get(0).elements().get(0).getName().equals("Alias")) {
                            return p.elements().get(0).elements().get(0).attributeValue(ESXAttributeName.INSCRIPTION);
                        }
                    }
                    p = (Element) items2.get(i - 2);
                    if (p.attributeValue(ESXAttributeName.KIND).equals(ESXElementName.PRAGMA)) {
                        if (p.elements().get(0).elements().get(0).getName().equals("Alias")) {
                            return p.elements().get(0).elements().get(0).attributeValue(ESXAttributeName.INSCRIPTION);
                        }
                    }
                }
            }
        }
        return "";
    }

    public static String isProvedByScheme(TheoremItem theoremItem) {
        String result = "";
        if (theoremItem.getJustification().getElement().getName().equals(ESXElementName.BLOCK)) {
            Element block = theoremItem.getJustification().getElement();
            List<Node> items = theoremItem.getElement().selectNodes(".//" + ESXElementName.ITEM);
            if (items.size() > 2) {
                if (((Element) items.get(items.size() - 2)).elements().get(0).elements().size() < 2 ||
                    (!((Element) items.get(items.size() - 2)).elements().get(0).elements().get(1).getName().equals(ESXElementName.SCHEME_JUSTIFICATION))) {
                        return "";
                }
                Element justification = ((Element) items.get(items.size() - 2)).elements().get(0).elements().get(1);
                if (justification.getName().equals(ESXElementName.SCHEME_JUSTIFICATION)) {
                    String schemeNr = justification.attributeValue(ESXAttributeName.MMLID);
                    String fileWithSchemeName = justification.attributeValue(ESXAttributeName.MMLID).toLowerCase().split(":")[0] + ".esx";
                    String alias = schemeAlias(fileWithSchemeName,schemeNr);

                    if (alias.length() > 0) {
                        return result.equals("") && alias.length() == 0 ? "" : privatePredicate(theoremItem, justification).texRepr(RepresentationCase.PREDICATE_IN_SCHEME)
                                + " The proof goes by " + LaTeX.italic(alias)
                                //+ justification.attributeValue(ESXAttributeName.MMLID).replace("_","\\_")
                                + ". ";
                    } else {
                        return result;
                    }
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
        String beginning = "\nPROOF: ";
        String ending = " " + LaTeX.ensureMath(LaTeX.qed());
        String isProvedByScheme = isProvedByScheme(theoremItem);
        if (!isProvedByScheme.equals("")) {
            return beginning + isProvedByScheme + ending;
        } else {
            String isProvedByByLocalTheorems = isProvedByByLocalTheorems(theoremItem);
            if (!isProvedByByLocalTheorems.equals("")) {
                return isProvedByByLocalTheorems;
            } else {
            }
        }
        return "";
    }
}
