package org.mizar.latex;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import org.mizar.files.*;
import org.mizar.xml_names.*;

public class Translations extends LinkedList<Translation> {

    private String fileName;

    public Translations(String fileName) {
        this.fileName = fileName;
        loadTranslations();
    }

    private void loadTranslations() {
        try {
            System.out.println("Loading translations from " + fileName);
            SAXReader saxBuilder = new SAXReader();
            Document document = saxBuilder.read(new File(fileName));
            for (Element element: document.getRootElement().elements(TranslationNames.TRANSLATION)) {
                this.add(new Translation(element));
            }
            System.out.println(this.size() + " translations loaded");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Translation translationWSX(String vocName, String kind, String symbolNr, String leftargsnr, String rightargsnr) {
//        System.out.println(vocName + " K=" + kind + " S=" + symbolNr + " L=" + leftargsnr + " R=" + rightargsnr);
        for (Translation translation: this) {
            switch (kind) {
                case TranslationKind.G, TranslationKind.J, TranslationKind.K, TranslationKind.L,
                        TranslationKind.M, TranslationKind.U, TranslationKind.V:
                    if (
                        translation.getElement().attributeValue("voc").equals(vocName)
                                &&
                                translation.getElement().attributeValue("kind").equals(kind)
                                &&
                                translation.getElement().attributeValue("symbolnr").equals(symbolNr)
                                &&
                                translation.getElement().attributeValue("argsnr").equals(leftargsnr)
                ) {
                    return translation;
                }
                    break;
                case TranslationKind.O, TranslationKind.R: if (
                        translation.getElement().attributeValue("voc").equals(vocName)
                                &&
                                translation.getElement().attributeValue("kind").equals(kind)
                                &&
                                translation.getElement().attributeValue("symbolnr").equals(symbolNr)
                                &&
                                translation.getElement().attributeValue("leftargsnr").equals(leftargsnr)
                                &&
                                translation.getElement().attributeValue("rightargsnr").equals(rightargsnr)
                ) {
                    return translation;
                }
                    break;
                default:
                    System.out.println("Missing case in Translation [" + kind + "]");
                    return null;
            }
        }
        return null;
    }

    public Translation translationWSX(String vocName, String kind, String symbolNr, Integer leftargsnr, Integer rightargsnr) {
        return translationWSX(vocName,kind,symbolNr,leftargsnr.toString(),rightargsnr.toString());
    }

    @Deprecated
    public Translation translationWSX_aaa(Format format) {
        Symbol symbol = format.symbol();
        String[] tab = symbol.getElement().attributeValue("absolutenr").split(":");
        String vocName = tab[0];
        String symbolNr = tab[1];
        String kind = format.getElement().attributeValue("kind");
        //WARNING
        if (kind.equals("G")) {
            kind = "L";
        }
        if (format.getElement().attributeValue("leftargnr") != null) {
            return translationWSX(vocName, kind, symbolNr, format.leftArgsNbr(), format.rightArgsNbr());
        } else {
            return translationWSX(vocName, kind, symbolNr, format.argsNbr(), 0);
        }
    }

    public Translation translationWSX(Format format, String kind) {
        Symbol symbol = format.symbol();
        String[] tab = symbol.getElement().attributeValue("absolutenr").split(":");
        String vocName = tab[0];
        String symbolNr = tab[1];
        //TODO WARNING
        if (format.getElement().attributeValue("leftargnr") != null) {
            return translationWSX(vocName, kind, symbolNr, format.leftArgsNbr(), format.rightArgsNbr());
        } else {
            return translationWSX(vocName, kind, symbolNr, format.argsNbr(), 0);
        }
    }

    private boolean equalAttributes(Element element, Translation translation, String attr) {
//        if (element.attribute(attr) == null)
        return translation.getElement().attributeValue(attr).equals(element.attributeValue(attr));
    }

    private boolean kindsAgree(Element element, Translation translation) {

        return switch (element.getName()) {

            case ESXElementName.CIRCUMFIX_TERM -> translation.getKind().equals("K");
            case ESXElementName.CIRCUMFIXFUNCTOR_PATTERN -> translation.getKind().equals("K");

            case ESXElementName.STRUCTURE_PATTERN -> translation.getKind().equals("L");
            case ESXElementName.AGGREGATE_TERM -> translation.getKind().equals("G");
            case ESXElementName.FORGETFUL_FUNCTOR_TERM -> translation.getKind().equals("J");
            case ESXElementName.INTERNAL_SELECTOR_TERM -> translation.getKind().equals("U");
            case ESXElementName.SELECTORFUNCTOR_PATTERN -> translation.getKind().equals("U");
            case ESXElementName.STRUCT_TYPE -> translation.getKind().equals("L");

            case ESXElementName.MODE_PATTERN -> translation.getKind().equals("M");
            case ESXElementName.STANDARD_TYPE -> translation.getKind().equals("M");

            case ESXElementName.INFIX_TERM -> translation.getKind().equals("O");
            case ESXElementName.INFIXFUNCTOR_PATTERN -> translation.getKind().equals("O");

            case ESXElementName.PREDICATE_PATTERN -> translation.getKind().equals("R");
            case ESXElementName.RELATION_FORMULA -> translation.getKind().equals("R");
            case ESXElementName.RIGHTSIDEOF_RELATION_FORMULA -> translation.getKind().equals("R");

            case ESXElementName.SELECTOR_TERM -> translation.getKind().equals("U");

            case ESXElementName.ATTRIBUTE -> translation.getKind().equals("V");
            case ESXElementName.ATTRIBUTE_PATTERN -> translation.getKind().equals("V");

            default -> throw new RuntimeException("UNKNOWN ESX Element Kind: " + element.getName() + " " + translation.getKind());
        };
    }

    public Translation translationESX(Element element, String kind) {
        for (Translation translation: this) {
            if (kindsAgree(element, translation)) {
                if (
                        equalAttributes(element, translation, TranslationNames.ABSOLUTEPATTERNMMLID)
//                    &&
//                    equalAttributes(element,translation,TranslationNames.ABSOLUTECONSTRMMLID)
                ) {
                    return translation;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Translations app = new Translations("inputs/pub.xml");
        for (Translation translation: app) {
            if (translation.getElement().attributeValue(TranslationNames.TEX_MODE) == null) {
                System.out.println(translation);
            }
        }
    }
}
