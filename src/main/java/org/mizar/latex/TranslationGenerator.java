package org.mizar.latex;

import org.dom4j.*;
import org.mizar.application.TranslationApplication;
import org.mizar.classes.*;
import org.mizar.files.*;
import org.mizar.misc.Errors;
import org.mizar.patterns.Patterns;
import org.mizar.xml_names.*;

import java.util.List;

public class TranslationGenerator extends TranslationApplication {

    Element root = DocumentHelper.createElement("Translations");
    Document newDocument = DocumentHelper.createDocument(root);

    TranslationGenerator(String fileName) {
        init(fileName);
    }

    private Element locus(int nr) {
        return DocumentHelper.createElement("X").addAttribute("locus","" + nr);
    }

    private Element generatePattern(Format format, Symbol symbol, String kind, boolean compl) {
        Element result = DocumentHelper.createElement(compl ? "compl" : "pattern");
        if (compl && !kind.equals("U") && !kind.equals("L")) {
            result.addText("not ");
        }
        int i;
        String string;
        switch (kind) {
            case "G":
                result.addText("\\langle ");
                for (i = 1; i < format.argsNbr(); i++) {
                    result.add(locus(i));
                    result.addText(", ");
                }
                result.add(locus(i));
                result.addText(" \\rangle");
                break;
            case "O", "R":
                for (i = 0; i < format.leftArgsNbr() - 1; i++) {
                    result.add(locus(i+1));
                    result.addText(", ");
                }
                result.add(locus(i+1));
                result.addText(" " + symbol.getElement().attributeValue("name") + " ");
                for (i = 0; i < format.rightArgsNbr() - 1; i++) {
                    result.add(locus(format.leftArgsNbr() + i + 1));
                    result.addText(", ");
                }
                result.add(locus(format.leftArgsNbr() + i + 1));
                break;
            case "J":
                result.addText(symbol.getElement().attributeValue("name") + " of ");
                result.add(locus(1));
                break;
            case "K":
                //TODO add args
                result.addText(symbol.getElement().attributeValue("name") + "TODO???");
                break;
            case "L":
                string = symbol.getElement().attributeValue("name").replace("_", "\\_");
                if (compl) {
                    result.addText(string + "'s ");
                } else {
                    result.addText(string + " ");
                }
                result.add(locus(0));
                break;
            case "U":
                string = symbol.getElement().attributeValue("name").replace("_", "\\_");
                if (compl) {
                    result.addText(string);
                } else {
                    result.addText(string + " of ");
                }
                break;
            case "V":
                if (format.argsNbr() > 1) {
                    for (i = 1; i < format.argsNbr() - 1; i++) {
                        result.add(locus(i));
                        result.addText(", ");
                    }
                    result.add(locus(i));
                }
                result.addText(" " + symbol.getElement().attributeValue("name").replace("_","\\_"));
                break;
            default:
                result.addText(" " + symbol.getElement().attributeValue("name").replace("_","\\_"));
        }
        return result;
    }

    public Element generateTranslation(Pattern pattern) {

        Element result = DocumentHelper.createElement("Translation");

        Format format = pattern.format();

        Symbol symbol = format.symbol();

        String[] tab = symbol.getElement().attributeValue("absolutenr").split(":");
        String vocName = tab[0];
        String symbolNr = tab[1];
        String kind = format.getElement().attributeValue("kind");

        Symbol rightSymbol = null;
        String[] tab2 = null;
        String vocName2 = null;
        String rightSymbolNr = null;

        result.addAttribute(TranslationNames.VOC,vocName);
        result.addAttribute(TranslationNames.KIND,kind);
        result.addAttribute(TranslationNames.SYMBOLNR,symbolNr);
        result.addAttribute(TranslationNames.SYMBOL,kind+symbol.getElement().attributeValue("name"));
        if (kind.equals("K")) {
            rightSymbol = format.rightSymbol();
            tab2 = rightSymbol.getElement().attributeValue("absolutenr").split(":");
            vocName2 = tab2[0];
            rightSymbolNr = tab2[1];
            result.addAttribute(TranslationNames.VOC2,vocName2);
            result.addAttribute(TranslationNames.RIGHTSYMBOLNR,rightSymbolNr);
            result.addAttribute(TranslationNames.RIGHTSYMBOL,"L"+rightSymbol.getElement().attributeValue("name"));
        }
        result.addAttribute(TranslationNames.ARGSNR, format.argsNbr().toString());
        result.addAttribute(TranslationNames.LEFTARGSNR, format.leftArgsNbr().toString());
        result.addAttribute(TranslationNames.RIGHTARGSNR, format.rightArgsNbr().toString());
        switch (kind) {
            case "O", "R":
                result.addAttribute(TranslationNames.FORMAT, kind + symbolNr + " " + format.leftArgsNbr() + " " + format.rightArgsNbr());
                break;
            case "K":
                result.addAttribute(TranslationNames.FORMAT, kind + symbolNr + " " + format.argsNbr().toString() + " L" + rightSymbolNr + " v" + vocName2);
                break;
            default:
                result.addAttribute(TranslationNames.FORMAT, kind + symbolNr + " " + format.argsNbr().toString());
        }
        if (kind.equals("O")) {
            result.addAttribute("priority","5");
        }
        result.addAttribute(TranslationNames.HEADER,"i");
        result.addAttribute(TranslationNames.FORCING,"");
        result.addAttribute(TranslationNames.CONTEXT1,"");
        result.addAttribute(TranslationNames.TEX_MODE,"h");

        result.add(generatePattern(format,symbol,kind,false));

        if (List.of("L","R","U").contains(kind)) {
            result.add(generatePattern(format,symbol,kind,true));
        }

        result.addAttribute(ESXAttributeName.ABSOLUTEPATTERNMMLID,pattern.getElement().attributeValue(ESXAttributeName.ABSOLUTEPATTERNMMLID));
        result.addAttribute(ESXAttributeName.ABSOLUTECONSTRMMLID,pattern.getElement().attributeValue(ESXAttributeName.ABSOLUTECONSTRMMLID));

        return result;
    }

    public static void main(String[] args) {
        TranslationGenerator app = new TranslationGenerator(args[0] + ".tix");
        formats = new Formats(args[0] + ".frx");
        symbols = new Symbols(args[0] + ".dcx");
        Errors errors = new Errors(app.fileName);

        Pattern p;
        for (Patterns pattern: Patterns.values()) {
            for (Node n: document.getRootElement().selectNodes(".//" + pattern.getRepr() + "/" + pattern.getESXname())) {
                p = Pattern.buildPattern((Element) n);
//                System.out.println(app.generateTranslation(p).asXML());
                app.root.add(app.generateTranslation(p));
            }
        }
        errors.printErrors();
        errors.writeErrors();
        Errors.printUnknowns();
        app.writeNewFile(args[0] + ".fmn",app.newDocument);
    }
}
