package org.mizar.latex;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.classes.*;
import org.mizar.misc.Errors;
import org.mizar.xml_names.*;

@AllArgsConstructor
@Getter
@Setter
@ToString

public class Translation implements Comparable<Translation> {

    private Element element;

    public List<Node> computePattern() {
        return element.selectNodes("./pattern/X | ./pattern/text() | ./pattern/core/text()");
    }

    public List<Node> computeCompl() {
        return element.selectNodes("./compl/X | ./compl/text() | ./compl/core/text()");
    }

    @Override
    public int compareTo(Translation that) {
        return Comparator.comparing(Translation::getVocName)
                .thenComparing(Translation::getKind)
                .thenComparing(Translation::getSymbolNr)
                .thenComparing(Translation::getArgsNr)
                .thenComparing(Translation::getLeftArgsNr)
                .thenComparing(Translation::getAbsolutePatternMMLID)
                .thenComparing(Translation::getAbsoluteConstrMMLID)
                .compare(this,that);
    }

    public String getVocName() {
        return element.attributeValue(TranslationNames.VOC);
    }

    public String getKind() {
        return element.attributeValue(TranslationNames.KIND);
    }

    public String getSymbolNr() {
        return element.attributeValue(TranslationNames.SYMBOLNR);
    }

    public String getArgsNr() {
        return element.attributeValue(TranslationNames.ARGSNR);
    }

    public String getLeftArgsNr() {
        return element.attributeValue(TranslationNames.LEFTARGSNR);
    }

    public String getAbsoluteConstrMMLID() {
        if (element.attribute(TranslationNames.ABSOLUTECONSTRMMLID) != null) {
            return element.attributeValue(TranslationNames.ABSOLUTECONSTRMMLID);
        } else {
            return "ZZZZZZZZZ";
        }
    }

    public String getAbsoluteOrigConstrMMLID() {
        return element.attributeValue(TranslationNames.ABSOLUTEORIGCONSTRMMLID);
    }

    public String getAbsolutePatternMMLID() {
        if (element.attribute(TranslationNames.ABSOLUTEPATTERNMMLID) != null) {
            return element.attributeValue(TranslationNames.ABSOLUTEPATTERNMMLID);
        } else {
            return "ZZZZZZZZZ";
        }
    }

    public String getAbsoluteOrigPatternMMLID() {
        return element.attributeValue(TranslationNames.ABSOLUTEORIGPATTERNMMLID);
    }

    private String getOperationKind() {
        String c = getElement().attributeValue(TranslationNames.HEADER).substring(2, 3);
        List<String> possibleOperationKinds = List.of("l","k","m","r","q","w","t","b","i","x","y","c");
        if (possibleOperationKinds.contains(c) && getKind().equals("O")) {
            return c;
        }
        return null;
    }

    public String getSpelling() {
        return element.attributeValue(TranslationNames.SYMBOL).substring(1);
    }

    public static Representation texRepr(XMLElement xmlElement, Term term) {
        try {
            String result = "";
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 1"), xmlElement.getElement().toString());
            }
            for (Node node : translation.computePattern()) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    result += term.texRepr(RepresentationCase.GENERAL);
                } else {
                    result += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                result = LaTeX.ensureMath(result);
            }
            return new Representation(result, translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_1 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement, Arguments arguments, boolean withoutFirst) {
        try {
            String result = "";
            Translation translation = xmlElement.translation();
//            System.out.println(xmlElement.kind() + " " + translation);
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 2"), xmlElement.getElement().toString());
            }
            int locusNr;
//        System.out.println(xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
//        System.out.println(arguments.getArguments().size());
//        System.out.println(translation.getAbsolutePatternMMLID());
            for (Node node : translation.computePattern()) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    locusNr = Integer.parseInt(((Element) node).attributeValue("locus"));
                    if (withoutFirst) {
                        if (locusNr > 1) {
                            result += arguments.getArguments().get(locusNr - 1).texRepr(RepresentationCase.GENERAL);
                        } else {
                            result += "";
                        }
                    } else {
                        result += arguments.getArguments().get(locusNr - 1).texRepr(RepresentationCase.GENERAL);
                    }
                } else {
                    result += node.getText();
                }
            }
            switch (translation.getElement().attributeValue(TranslationNames.TEX_MODE)) {
                case "i":
                    result = arguments.getArguments().get(0).texRepr(RepresentationCase.GENERAL) + " is " + result;
                    break;
                case "m":
                    result = LaTeX.ensureMath(result);
                    break;
                case "h":
                    result = result;
//                result = LaTeX.text(result);
                    break;
            }
            return new Representation(result, translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_2 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement, Arguments arguments) {
        try {
            return texRepr(xmlElement, arguments, false);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_3 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texReprInfix(XMLElement xmlElement, Arguments arguments, boolean withoutFirst) {
        try {
            String result = "";
            Translation translation = xmlElement.translation();
            Representation argRepresentation = null;
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 3"), xmlElement.getElement().toString());
            }
            Term arg;
            Integer argPriority;
            Integer thisPriority = Integer.parseInt(translation.getElement().attributeValue(TranslationNames.PRIORITY));
            int locusNr;
            for (Node node : translation.computePattern()) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    locusNr = Integer.parseInt(((Element) node).attributeValue("locus"));
                    argPriority = Integer.MAX_VALUE;
                    if (withoutFirst) {
                        if (locusNr > 1) {
                            arg = arguments.getArguments().get(locusNr - 1);
                            //TODO other cases
                            if (arg instanceof InfixTerm) {
                                argPriority = Integer.parseInt(arg.translation().getElement().attributeValue(TranslationNames.PRIORITY));
                            }
                            if (argPriority <= thisPriority) {
                                result += LaTeX.ensureMath(Texts.LB2);
                            }
                            result += arguments.getArguments().get(locusNr - 1).texRepr(RepresentationCase.GENERAL);
                            if (argPriority <= thisPriority) {
                                result += LaTeX.ensureMath(Texts.RB2);
                            }
                        } else {
                            result += "";
                        }
                    } else {
                        arg = arguments.getArguments().get(locusNr - 1);
                        argRepresentation = arg.texRepr(RepresentationCase.GENERAL);

                        if (arg instanceof InfixTerm) {
                            argPriority = Integer.parseInt(arg.translation().getElement().attributeValue(TranslationNames.PRIORITY));
                        }

                        boolean addBrackets =
                                argRepresentation.openTerm
                                        && argPriority <= thisPriority

                                        && locusNr == 1
                                        && translation.getOperationKind().equals(OperationKind.UPPER_POSTFIX);

                        //TODO other cases

                        if (addBrackets) {
                            result += LaTeX.ensureMath("\\left" + Texts.LB3);
                        }

                        result += argRepresentation;

                        if (addBrackets) {
                            result += LaTeX.ensureMath("\\right" + Texts.RB3);
                        }

//OK                    result += arguments.getArguments().get(locusNr - 1).texRepr();
                    }
                } else {
                    result += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                result = LaTeX.ensureMath(result);
            } else {
                if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("h")) {
                    result = LaTeX.text(result);
                }
            }
            return new Representation(result, translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_4 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texReprInfix(XMLElement xmlElement, Arguments arguments) {
        try {
            return texReprInfix(xmlElement,arguments,false);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_5 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement, Loci loci, boolean pattern) {
        try {
            String result = "";
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 4"), xmlElement.getElement().toString());
            }
            List<Node> nodes = pattern ? translation.computePattern() : translation.computeCompl();
            for (Node node : nodes) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    result += loci.getLoci().get(Integer.parseInt(((Element) node).attributeValue("locus")) - 1).texRepr(RepresentationCase.GENERAL);
                } else {
                    result += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                result = LaTeX.ensureMath(result);
            }
            return new Representation(LaTeX.colorbox(result), translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_6 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement, Loci loci) {
        try {
            return texRepr(xmlElement, loci, true);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_7 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texReprMode(XMLElement xmlElement, Loci loci) {
        try {
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 5"), xmlElement.getElement().toString());
            }
            int locusNr;
            String repr = "";
            List<Node> nodes = translation.computePattern();
            for (Node node : nodes) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    locusNr = Integer.parseInt(((Element) node).attributeValue("locus"));
                    if (locusNr > 0) {
                        repr += loci.getLoci().get(Integer.parseInt(((Element) node).attributeValue("locus")) - 1).texRepr(RepresentationCase.GENERAL);
                    } else {
                        repr += "";
                    }
                } else {
                    repr += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                repr = LaTeX.ensureMath(repr);
            }
            return new Representation(LaTeX.colorbox(repr), translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_8 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texReprType(XMLElement xmlElement, Arguments arguments) {
        try {
            String result = "";
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 6"), xmlElement.getElement().toString());
            }
            int locusNr;
            boolean plural = plural(xmlElement);
            List<Node> nodes = plural ? translation.computeCompl() : translation.computePattern();
            for (Node node : nodes) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    locusNr = Integer.parseInt(((Element) node).attributeValue("locus"));
                    if (locusNr > 0) {
                        result += arguments.getArguments().get(Integer.parseInt(((Element) node).attributeValue("locus")) - 1).texRepr(RepresentationCase.GENERAL);
                    } else {
                        result += "";
                    }
                } else {
                    result += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                result = LaTeX.ensureMath(result);
            }
            return new Representation(result, translation, plural);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_9 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement, Loci leftLoci, Loci rightLoci) {
        try {
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 7"), xmlElement.getElement().toString());
            }
            int locusNr;
            String repr = "";
            for (Node node : translation.computePattern()) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    locusNr = Integer.parseInt(((Element) node).attributeValue("locus")) - 1;
                    if (locusNr < leftLoci.getLoci().size()) {
                        repr += leftLoci.getLoci().get(locusNr).texRepr(RepresentationCase.GENERAL);
                    } else {
                        repr += rightLoci.getLoci().get(locusNr - leftLoci.getLoci().size()).texRepr(RepresentationCase.GENERAL);
                    }
                } else {
                    repr += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("i")) {
                repr = leftLoci.getLoci().get(0).texRepr(RepresentationCase.GENERAL) + " is " + repr;
            } else {
                if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                    repr = LaTeX.ensureMath(repr);
                }
            }
            return new Representation(LaTeX.colorbox(repr), translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_10 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    public static Representation texRepr(XMLElement xmlElement) {
        try {
            Translation translation = xmlElement.translation();
            if (translation == null) {
                Errors.logMissing(xmlElement.getElement());
                Errors.logException(new Exception("Unknown translation 8"), xmlElement.getElement().toString());
            }
            String repr = "";
            for (Node node : translation.computePattern()) {
                if (node.getClass().getName().equals("org.dom4j.tree.DefaultElement")) {
                    repr += "it";
                } else {
                    repr += node.getText();
                }
            }
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE).equals("m")) {
                repr = LaTeX.ensureMath(repr);
            }
            return new Representation(LaTeX.colorbox(repr), translation);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Exception in texRepr_11 caused by " + xmlElement.getElement().attributeValue(ESXAttributeName.XMLID));
        }
    }

    private static boolean plural(XMLElement xmlElement) {
        if (xmlElement.getElement().getParent().getParent().getName().equals(ESXElementName.FUNCTOR_SEGMENT)) {
            if (xmlElement.getElement().getParent().getParent().element(ESXElementName.VARIABLES).elements(ESXElementName.VARIABLE).size() > 1) {
                return true;
            }
        }
        if (xmlElement.getElement().getParent().getName().equals(ESXElementName.RESERVATION_SEGMENT)) {
            if (xmlElement.getElement().getParent().element(ESXElementName.VARIABLES).elements(ESXElementName.VARIABLE).size() > 1) {
                return true;
            }
        }
        //TODO glue cases EXPLICITLY_QUALIFIED_SEGMENT
        if (xmlElement.getElement().getParent().getName().equals(ESXElementName.EXPLICITLY_QUALIFIED_SEGMENT)) {
            if (xmlElement.getElement().getParent().element(ESXElementName.VARIABLES).elements(ESXElementName.VARIABLE).size() > 1) {
                return true;
            }
        }
        if (xmlElement.getElement().getParent().getParent().getName().equals(ESXElementName.EXPLICITLY_QUALIFIED_SEGMENT)) {
            if (xmlElement.getElement().getParent().getParent().element(ESXElementName.VARIABLES).elements(ESXElementName.VARIABLE).size() > 1) {
                return true;
            }
        }
        if (xmlElement.getElement().getParent().getName().equals(ESXElementName.FIELD_SEGMENT)) {
            if (xmlElement.getElement().getParent().element(ESXElementName.SELECTORS).elements(ESXElementName.SELECTOR).size() > 1) {
                return true;
            }
        }
        return false;
    }
}
