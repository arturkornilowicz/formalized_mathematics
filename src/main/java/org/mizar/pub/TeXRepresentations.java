package org.mizar.pub;

import org.mizar.application.XMLApplication;
import org.mizar.latex.*;
import org.dom4j.*;
import java.util.*;
import java.io.*;

public class TeXRepresentations extends XMLApplication {

    private String pattern2LaTeX(List<Node> pattern) {
        String result = "";
        for (Node node : pattern) {
            switch (node.getClass().getName()) {
                case "org.dom4j.tree.DefaultText":
                    result += node.getText();
                    break;
                case "org.dom4j.tree.DefaultElement":
                    if (((Element)node).getName().equals("X")) {
                        result += ((Element) node).attributeValue("locus");
                    }
                    break;
                default:
            }
        }
        return result;
    }

    Writer writer;

    private void write(String s) throws IOException {
        writer.write(s + "\n");
    }

    public static void main(String[] args) throws Exception {
        TeXRepresentations app  = new TeXRepresentations();
        app.init("inputs/pub.xml");
        Translation translation;
        boolean mathmode;
        int nr = 1;

        app.writer = new FileWriter("inputs/examples.tex");

        app.write("\\documentclass{formath1}");
        app.write("\\usepackage[utf8]{inputenc}");
        app.write("\\usepackage[T1]{fontenc}");
        app.write("\\usepackage{amsmath,amssymb}");
        app.write("\\setlength{\\parindent}{0pt}\n");
        app.write("\\begin{document}");

        app.write("Numer translacji\n");
        app.write("Slownik   rodzaj   symbol   liczba\\_argumentow\n");
        app.write("Przepisane tlumaczenie\n");
        app.write("Tlumaczenie w \\LaTeX'u\n");
        app.write("\\medskip\\hrule\\medskip\n");

        for (Element element: TeXRepresentations.document.getRootElement().elements(TranslationNames.TRANSLATION)) {
            translation = new Translation(element);
//            mathmode = element.attributeValue(TranslationNames.HEADER).substring(0,1).equals("m");
            if (element.attribute(TranslationNames.TEX_MODE) != null) {
                mathmode = element.attributeValue(TranslationNames.TEX_MODE).equals("m");
            } else {
                mathmode = false;
            }
            String representation = app.pattern2LaTeX(translation.computePattern());
//            if (translation.getElement().attributeValue(TranslationNames.KIND).equals("O"))
//            {
                {
                    app.write(
                            "Translation nr " + (nr++)
                            + "\n\\begin{verbatim}\n"
                            + element.attributeValue(TranslationNames.VOC)
                                    + " k=" + element.attributeValue(TranslationNames.KIND)
                                    + " " + element.attributeValue(TranslationNames.SYMBOL).substring(1)
                            + " argsNr=" + element.attributeValue(TranslationNames.ARGSNR)
                            + "\n" + representation
                            + "\n\\end{verbatim}\n"
                            + (mathmode ? "$" + representation + "$" : representation.replace("_","\\_").replace("\\\\_","\\_"))
                            + "\\medskip\\hrule\\medskip\n"
                    );
                }
//            }
        }
        app.write("\\end{document}");
        app.writer.close();
    }
}
