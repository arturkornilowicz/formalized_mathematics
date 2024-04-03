package org.mizar.application;

import java.util.*;
import org.mizar.latex.*;
import org.mizar.vocabularies.*;

public class TestPubXml {

    Vocabularies vocabularies;
    Translations translations;

    void checkVocabularies_MissingVocabularies() {
        Set<String> missing = new TreeSet<>();
        String repr;
        for (Translation translation: translations) {
            repr = translation.getElement().attributeValue(TranslationNames.SYMBOL).substring(1);
            if (vocabularies.findVocabularyWithSymbol(repr) == null) {
                missing.add(repr);
            }
        }
        System.out.println("Missing vocabularies\n" + missing);
    }

    void checkVocabularies_MissingTranslations() {
        Set<String> missing = new TreeSet<>();
        boolean found;
        for (Vocabulary vocabulary: vocabularies) {
            for (Symbol symbol: vocabulary) {
                found = false;
                for (Translation translation: translations) {
                    if (symbol.getRepresentation().equals(translation.getElement().attributeValue(TranslationNames.SYMBOL).substring(1))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    missing.add(symbol.getRepresentation());
                }
            }
        }
        System.out.println("Missing translations\n" + missing);
    }

    void checkVocabularies_WrongVocs() {
        Set<String> missing = new TreeSet<>();
        String repr;
        Vocabulary vocabulary;
        for (Translation translation: translations) {
            repr = translation.getElement().attributeValue(TranslationNames.SYMBOL).substring(1);
            vocabulary = vocabularies.findVocabularyWithSymbol(repr);
            if (vocabulary != null) {
                if (!vocabulary.getName().equals(translation.getElement().attributeValue(TranslationNames.VOC))) {
                    missing.add(repr);
                }
            }
        }
        System.out.println("Wrong vocabularies\n" + missing);
    }

    public static void main(String[] args) {
        TestPubXml app = new TestPubXml();
        app.vocabularies = new Vocabularies(args[0]);
        app.vocabularies.loadMmlVct();
        app.translations = new Translations(args[1]);
        app.checkVocabularies_MissingVocabularies();
        app.checkVocabularies_MissingTranslations();
        app.checkVocabularies_WrongVocs();
    }
}
