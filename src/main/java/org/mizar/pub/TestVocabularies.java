package org.mizar.pub;

import org.mizar.application.TranslationApplication;
import org.mizar.vocabularies.*;
import org.mizar.latex.*;

public class TestVocabularies extends TranslationApplication {
    TestVocabularies(String fileName) {
        super(fileName);
    }

    Vocabularies vocabularies = new Vocabularies("/usr/local/share/mizar/mml.vct");

    void test_missing_vocabularies() {
        boolean found;
        for (Vocabulary vocabulary: vocabularies) {
            found = false;
            for (Translation translation: translations) {
                if (translation.getVocName().equalsIgnoreCase(vocabulary.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Missing vocabulary " + vocabulary.getName());
            }
        }
    }

    void test_missing_symbols() {
        boolean found;
        int counter = 0;
        for (Vocabulary vocabulary: vocabularies) {
            for (Symbol symbol: vocabulary) {
                found = false;
                for (Translation translation : translations) {
                    if (translation.getElement().attributeValue(TranslationNames.SYMBOL).substring(1).equals(symbol.getRepresentation())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    counter++;
                    System.out.println("Missing symbol " + symbol.getRepresentation() + " from vocabulary " + vocabulary.getName());
                }
            }
        }
        System.out.println(counter + " symbols not found");
    }

    void test_symbol_numbers() {
        int nr;
        for (Vocabulary vocabulary: vocabularies) {
            nr = 0;
            for (Symbol symbol: vocabulary) {
                if (symbol.getKind().name().equals("V")) {
                    nr++;
                    for (Translation translation : translations) {
                        if (translation.getElement().attributeValue(TranslationNames.SYMBOL).substring(1).equals(symbol.getRepresentation())) {
                            if (!translation.getSymbolNr().equals(""+nr)) {
                                System.out.println(symbol.getRepresentation() + " in " + vocabulary.getName() + " " + nr + " " + translation.getSymbolNr());
                                System.out.println(translation);
//                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestVocabularies app = new TestVocabularies("inputs/pub.xml");
        app.vocabularies.loadMmlVct();
//        app.test_missing_vocabularies();
        app.test_missing_symbols();
//        app.test_symbol_numbers();
    }
}
