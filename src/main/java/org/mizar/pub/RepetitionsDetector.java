package org.mizar.pub;

import org.mizar.application.TranslationApplication;

import java.io.File;
import java.io.FileWriter;

public class RepetitionsDetector extends TranslationApplication {

    RepetitionsDetector() {
        super("/Users/artur/pub.xml");
    }

    FileWriter fw;

    void test1() throws Exception {
        for (int i = 0; i < translations.size(); i++) {
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = i+1; j < translations.size(); j++) {
                if (translations.get(i).getVocName().equals(translations.get(j).getVocName())
                        && translations.get(i).getSpelling().equals(translations.get(j).getSpelling())
                        && translations.get(i).getArgsNr().equals(translations.get(j).getArgsNr())
                        && translations.get(i).getLeftArgsNr().equals(translations.get(j).getLeftArgsNr())
                ) {
                    System.out.println(translations.get(i));
                    System.out.println(translations.get(j));
                    fw.write(translations.get(i).getElement().toString() + "\n\n");
                    fw.write(translations.get(j).getElement().toString() + "\n\n");
                    fw.write("-------\n\n\n\n");
                }
            }
        }
    }

    void test2() throws Exception {
        for (int i = 0; i < translations.size(); i++) {
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = i+1; j < translations.size(); j++) {
                if (translations.get(i).getVocName().equals(translations.get(j).getVocName())
                        && translations.get(i).getKind().equals(translations.get(j).getKind())
                        && translations.get(i).getSymbolNr().equals(translations.get(j).getSymbolNr())
                        && !translations.get(i).getSpelling().equals(translations.get(j).getSpelling())
                ) {
                    System.out.println(translations.get(i));
                    System.out.println(translations.get(j));
                    fw.write(translations.get(i).getElement().toString() + "\n\n");
                    fw.write(translations.get(j).getElement().toString() + "\n\n");
                    fw.write("-------\n\n\n\n");
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        
        RepetitionsDetector app = new RepetitionsDetector();
        app.fw = new FileWriter("symbol_repetitions.txt",true);

        //app.test1();
        app.test2();

        app.fw.close();
    }
}
