package org.mizar.pub;

import org.dom4j.*;
import org.mizar.application.TranslationApplication;
import org.mizar.latex.*;
import java.io.*;
import java.util.*;

public class Test_TeXMode extends TranslationApplication {

    Test_TeXMode(String fileName) {
        super(fileName);
    }

    List<String> chars = List.of("G", "J", "L", "V", "M", "U");

    Translation translation1;

    private void test_1(Translation translation) {
        if (translation.getKind().equals("O")) {
            translation1 = translation;
            String TeXmode = translation.getElement().attributeValue(TranslationNames.TEX_MODE);
            String headerMode = translation.getElement().attributeValue(TranslationNames.HEADER).substring(0, 1);

            // TeXmode
            if (!TeXmode.equals(headerMode)) {
                System.out.println(translation.getKind() + " " + TeXmode + " " + headerMode + " " + TeXmode.equals(headerMode));
            }

            //Bracket-ability
            headerMode = translation.getElement().attributeValue(TranslationNames.HEADER).substring(1, 2);
            if (!(headerMode.equals("o") || headerMode.equals("c"))) {
                System.out.println(translation.getKind() + " " + TeXmode + " " + headerMode + " " + TeXmode.equals(headerMode));
                System.out.println(translation);
            }
        }
    }

    private void test_2(Translation translation) {
        String[] format;
        String symbolnr;
        if (translation.getKind().equals("G")) {
            format = translation.getElement().attributeValue(TranslationNames.FORMAT).split(" ");
            symbolnr = format[0].substring(1);
            if (! (symbolnr.equals(translation.getSymbolNr()) && format[1].equals(translation.getArgsNr())) ) {
                System.out.println(translation);
            }
        }
    }

    private void test_3_lokusy(Translation translation) {
        Element e;
        int l;
        for (Node node: translation.getElement().selectNodes(".//X")) {
            e = (Element)node;
            l = Integer.parseInt(e.attributeValue("locus"));
            if (l > Integer.parseInt(translation.getArgsNr())) {
                System.out.println(translation);
            }
        }
    }

    private void test_4(Translation translation) {
        if (translation.getKind().equals("V")) {
            if (translation.getArgsNr().equals("0")) {
                System.out.println(translation);
            }
        }
    }

    private void test_5(Translation translation) {
        String[] format;
        int left, right;
        if (translation.getKind().equals("O") || translation.getKind().equals("R")) {
            format = translation.getElement().attributeValue(TranslationNames.FORMAT).split(" ");
            left = Integer.parseInt(format[1]);
            right = Integer.parseInt(format[2]);
            if (
                    left + right != Integer.parseInt(translation.getArgsNr())
                            || left != Integer.parseInt(translation.getLeftArgsNr())
                            || right != Integer.parseInt(translation.getElement().attributeValue(TranslationNames.RIGHTARGSNR))
            ) {
                System.out.println(translation);
            }
        }
    }

    private void test_6(Translation translation) {
        try {
            String[] format;
            int args;
            if (chars.contains(translation.getKind())) {
                format = translation.getElement().attributeValue(TranslationNames.FORMAT).split(" ");
                args = Integer.parseInt(format[1]);
                if (
                        args != Integer.parseInt(translation.getArgsNr())
                ) {
                    System.out.println(translation);
                }
            }
        } catch (Exception exception) {
            System.out.println(translation);
            exception.printStackTrace();
        }
    }

    private void test_7(Translation translation) {
        try {
            String[] format;
            int symbolnr;
            if (chars.contains(translation.getKind())) {
                format = translation.getElement().attributeValue(TranslationNames.FORMAT).split(" ");
                symbolnr = Integer.parseInt(format[0].substring(1));
                if (
                        symbolnr != Integer.parseInt(translation.getSymbolNr())
                ) {
                    System.out.println(translation);
                }
            }
        } catch (Exception exception) {
            System.out.println(translation);
            exception.printStackTrace();
        }
    }

    void test_symbol_repetitions() throws Exception {
        for (int i = 0; i < translations.size(); i++) {
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = i; j < translations.size(); j++) {
                if (translations.get(i).getSpelling().equals(translations.get(j).getSpelling())) {
                    if (!translations.get(i).getVocName().equals(translations.get(j).getVocName())) {
                        FileWriter fw = new FileWriter("repeated_spellings_in_pub.txt",true);
                        fw.write(translations.get(i).toString() + "\n\n");
                        fw.write(translations.get(j).toString() + "\n\n\n\n");
                        fw.close();
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Test_TeXMode app = new Test_TeXMode("inputs/pub.xml");
//        Test_TeXMode app = new Test_TeXMode("/Users/artur/pub.xml");
        Translation translation1 = null;
        try {
            for (Translation translation : translations) {
                translation1 = translation;
//                app.test_1(translation);
//                app.test_2(translation);
//                app.test_3_lokusy(translation);
//                app.test_4(translation);
//                app.test_5(translation);
//                app.test_symbol_repetitions();
//                app.test_6(translation);
                app.test_7(translation);
            }
        } catch (Exception e) {
            System.out.println(translation1);
            e.printStackTrace();
        }
    }
}
