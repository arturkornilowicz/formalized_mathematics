package org.mizar.latex;

import java.io.*;
import java.util.*;
import org.jbibtex.*;
import org.dom4j.Element;
import org.mizar.application.FormalizedMathematics;
import org.mizar.classes.*;
import org.mizar.misc.Errors;
import org.mizar.variables.Identifier;
import org.mizar.xml_names.*;

public class LaTeX {

    private static List<String> texFileContent = new LinkedList<>();

    public static Integer sectionCounter = 0;
    public static Integer thmCounter = 0;
    public static Integer defCounter = 0;
    public static Integer schCounter = 0;

    public static void addText(String string) {
        texFileContent.add(string);
    }

    public static void addTextLn(String string) {
        addText(string + "\n");
    }

    public static String ensureMath(String string) {
        return "\\ensuremath{" + string + "}";
    }

    public static String italic(String string) { return "{\\it " + string + "}"; }

    public static String bf(String string) { return "{\\bf " + string + "}"; }

    public static String text(String string) { return "\\textrm{" + string + "}"; }

    public static String mathrm(String string) { return "\\mathrm{" + string + "}"; }

    public static String mathcal(String string) {
        List<String> upperLetters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; ++c) {
            upperLetters.add("" + c);
        }
        return upperLetters.contains(string) ? ensureMath("\\mathcal{" + string + "}") : string;
    }

    public static String colorbox(String string) {
        return "\\colorbox{green}{" + string + "}";
    }

    public static String allowbreak() {
        return "\\allowbreak";
    }
    public static String qed() { return "\\Box"; }

    private final String firstPageFile = "firstPage";
    private final String lastPageFile = "lastPage";

    private String authors() {
        String[] tab = FormalizedMathematics.bibTeXEntry.getField(BibTeXEntry.KEY_AUTHOR).toUserString().split(" and ");
        String[] author;
        Value value;
        for (int i = 0; i < tab.length; i++) {
            author = tab[i].split(",");
            value = FormalizedMathematics.bibTeXEntry.getField(new Key("ADDRESS" + (i+1)));
            tab[i] = author[1].trim() + " " + author[0].trim() + " \\\\ " + value.toUserString();
        }
        return String.join(" \\and ", tab);
    }

    private void prepareAuthorsAndTitle() {
        addTextLn("\\title{" + FormalizedMathematics.bibTeXEntry.getField(BibTeXEntry.KEY_TITLE).toUserString() + "}\n");
        addTextLn("\\author{" + authors() + "}");
        addTextLn("\\makearticletitle\n");
    }

    public void preambule() {
        addTextLn("\\documentclass[draft]{formath1}\n");
        addTextLn("\\usepackage[utf8]{inputenc}");
        addTextLn("\\usepackage[T1]{fontenc}");
        addTextLn("\\usepackage{xcolor}");
        addTextLn("");
        addTextLn("\\newcounter{abc}");
        addTextLn("");

        addTextLn("\\newwrite\\" + lastPageFile);
        addTextLn("\\immediate\\openout\\" + lastPageFile + "=" + FormalizedMathematics.fileName + ".lastpage");

        addTextLn("\n\\newread\\fileFirst");
        addTextLn("\\openin\\fileFirst=" + FormalizedMathematics.dirName + "/firstPage.txt");
        addTextLn("\\read\\fileFirst to\\fileline");
        addTextLn("\\closein\\fileFirst");

        addTextLn("\\setcounter{page}{\\fileline}");

        addTextLn("\n\\newread\\fileLast");
        addTextLn("\\openin\\fileLast=" + FormalizedMathematics.dirName + "/lastPage.txt");
        addTextLn("\\read\\fileLast to\\fileline");
        addTextLn("\\closein\\fileLast");

        addTextLn("\\newcounter{lastpage}");
        addTextLn("\\setcounter{lastpage}{\\fileline}");

        /*
        addText("\n\n");
        addText("\\article[Tyt.]{Tytuł}\n");
        addText("\\title{\\Pełny tytuł pracy}");
        addText("\\author{Arur Korniłowicz\\\\University of Bialystok\\\\Bialystok, Poland \\and Jan Kowalski\\\\Shinshu University\\\\Nagano, Japan}");
        addText("\\makearticletitle\n");
        */

        addTextLn("\n\\begin{document}");

        String fmnr = FormalizedMathematics.fmYear.element("fmnr") != null ? FormalizedMathematics.fmYear.element("fmnr").getText() : "";

        addTextLn("\\def\\fmyear{" + FormalizedMathematics.fmYear.element("fmyear").getText() + "}"
                + "\\def\\fmvol{" + FormalizedMathematics.fmYear.element("fmvol").getText() + "}"
                + "\\def\\fmnr{" + fmnr + "}"
// TODO
//                + "\\def\\doifm{DOI: \\spaceskip 0.08mm  \\spaceskip 0.08mm" + FormalizedMathematics.fmYear.element("doi").getText()
                + "\\def\\doifm{DOI: \\spaceskip 0.08mm  \\spaceskip 0.08mm" + "FAKE-DOI"
                + "}\n");

        addTextLn("\\article{}{}{}"); //IMPORTANT

        prepareAuthorsAndTitle();
    }

    public void endFile() {
        addTextLn("\n");
        addTextLn("\\immediate\\write\\" + lastPageFile + "{\\thepage}");
        addTextLn("\\immediate\\closeout\\" + lastPageFile);
        addTextLn("\\vspace{1cm}Last generated: " + new Date().toString());
        addTextLn("\n\\end{document}");
    }

    public void printFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (String string: this.texFileContent) {
                fileWriter.write(string + "");
            }
            fileWriter.close();
        } catch (Exception exception) {

        }
    }

    public static String unfinished(Class<?> class1, String comment) {
        return "\\phantom{}\\newline{\\small\\color{brown}UNFINISHED " + class1.getName() + " " + comment + "}";
    }

    public static String unfinished(Class<?> class1) {
        return unfinished(class1,"");
    }

    public static String unknown(Class<?> class1) {
        if (class1.getAnnotation(_Nonpublicable.class) == null) {
            Errors.logUnknown(class1);
            Errors.unknown(class1);
            return "\\phantom{}\\newline{\\small\\color{red}UNKNOWN " + class1.getName() + "}";
        } else {
            return "";
        }
    }

    public static String spelling(XMLElement xmlElement) {
        return xmlElement.getElement().attributeValue(ESXAttributeName.SPELLING);
    }

    public static String texReprMath(List<? extends XMLElement> xmlElements) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < xmlElements.size()-1; i++) {
            result.append(xmlElements.get(i).texRepr(RepresentationCase.GENERAL));
            result.append(", ");
        }
        result.append(xmlElements.get(i).texRepr(RepresentationCase.GENERAL));
        return LaTeX.ensureMath(result.toString());
    }

    public static String texReprMathCal(List<? extends Variable> xmlElements) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < xmlElements.size()-1; i++) {
            result.append(LaTeX.mathcal(xmlElements.get(i).getElement().attributeValue(ESXAttributeName.SPELLING)));
            result.append(", ");
        }
        result.append(LaTeX.mathcal(xmlElements.get(i).getElement().attributeValue(ESXAttributeName.SPELLING)));
        return LaTeX.ensureMath(result.toString());
    }

    public static String texReprText(List<? extends XMLElement> xmlElements) {
        StringBuilder result = new StringBuilder();
        if (xmlElements.size() > 0) {
            int i;
            for (i = 0; i < xmlElements.size() - 1; i++) {
                result.append(xmlElements.get(i).texRepr(RepresentationCase.GENERAL));
                result.append(", ");
            }
            result.append(xmlElements.get(i).texRepr(RepresentationCase.GENERAL));
        }
        return result.toString();
    }

    public static String texReprTextAnd(List<? extends XMLElement> xmlElements, Integer representationCase) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < xmlElements.size()-1; i++) {
            result.append(xmlElements.get(i).texRepr(representationCase));
            result.append((xmlElements.size() > 2 ? ", " : ""));
        }
        result.append(i > 0 ? " and " : "" );
        result.append(xmlElements.get(i).texRepr(representationCase));
        return result.toString();
    }

    public static void texTheorems(List<Item> theorems) {
        if (theorems.size() == 1) {
            LaTeX.addText(Texts.T1);
        } else {
            LaTeX.addText(Texts.T1a);
        }

        LaTeX.addText("\n\n\\begin{description}\n\n");

        for (Item item: theorems) {
            LaTeX.addText(item.getLatexOutput());
        }
        LaTeX.addText("\\end{description}\n");
    }

    //TODO other defs
    public static void texDefinienses(List<Definiens> definienses) {
        LaTeX.addText("\n\n\\begin{description}\n");
        for (Definiens definiens: definienses) {
            definiens.texDefiniens();
        }
        LaTeX.addText("\n\\end{description}\n");
    }

    public static String texDefiniensesString(List<Definiens> definienses) {
        StringBuilder result = new StringBuilder();
        result.append("\n\n\\begin{description}\n");
        for (Definiens definiens: definienses) {
            result.append(definiens.texDefiniensString());
        }
        result.append("\n\\end{description}\n");
        return result.toString();
    }

    public static void defDescriptionItem() {
        LaTeX.addText("\\item[{\\makebox[30pt][r]{(Def.~" + (++LaTeX.defCounter) + ")\\hspace{5pt}}}]  \\setcounter{abc}{0}\n");
    }

    public static String defDescriptionItemString() {
        return "\\item[{\\makebox[30pt][r]{(Def.~" + (++LaTeX.defCounter) + ")\\hspace{5pt}}}]  \\setcounter{abc}{0}\n";
    }

    public static String thmDescriptionItem() {
        return "\\item[{\\makebox[30pt][r]{(" + (++LaTeX.thmCounter) + ")\\hspace{5pt}}}]  \\setcounter{abc}{0}\n";
    }

    public static String varRepr(String string) {
        //TODO read from file
        Identifier identifier = new Identifier(string);
        if (identifier.isWellComposed()) {
            return LaTeX.ensureMath(identifier.getLetters() + "_{" + identifier.getDigits() + "}");
        } else {
            return LaTeX.ensureMath(string);
        }
    }

    public static String propertyName(Element element) {
        switch (element.attributeValue(ESXAttributeName.PROPERTY)) {
            case "asymmetry":
                return "asymmetric";
            case "commutativity":
                return "commutative";
            case "connectedness":
                return "connected";
            case "idempotence":
                return "idempotence";
            case "involutiveness":
                return "involutive";
            case "irreflexivity":
                return "irreflexive";
            case "projectivity":
                return "projective";
            case "reflexivity":
                return "reflexive";
            case "sethood":
                return "sethood";
            case "symmetry":
                return "symmetric";
            default:
                Errors.error(element, "Missing Element in propertyName[" + element.attributeValue(ESXAttributeName.PROPERTY) + "]");
                return null;
        }
    }

    public static String has_sat(Translation translation) {
        return switch (translation.getElement().attributeValue(TranslationNames.HEADER).substring(0,1)) {
            case AdjectiveKind.HAS -> " has ";
            case AdjectiveKind.SAT -> " satisfies ";
            default -> " is ";
        };
    }

    public static String preposition(String word) {
        List<Character> firstChar = List.of('a','e','i','o','u','y');
        //TODO ~
        return firstChar.contains(word.charAt(0)) ? "an " : "a ";
//        return firstChar.contains(word.charAt(0)) ? "an~" : "a~";
    }
}
