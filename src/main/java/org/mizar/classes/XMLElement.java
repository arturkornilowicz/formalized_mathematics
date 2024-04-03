package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.application.FormalizedMathematics;
import org.mizar.files.*;
import org.mizar.latex.*;

@AllArgsConstructor
@ToString
@Getter
@Setter

public class XMLElement {

    private Element element;

    public void preProcess() {}

    public void process() {}

    public void postProcess() {}

    public void run() {
        preProcess();
        process();
        postProcess();
    }

    public String kind() {
        return null;
//        return element.attributeValue("kind");
    }

    public final Format format() {
        return FormalizedMathematics.formats.format(element.attributeValue("formatnr"));
    }

    public final Translation translation() {
//        return FormalizedMathematics.translations.translationWSX(format(),kind());
        return FormalizedMathematics.translations.translationESX(element,kind());
    }

    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.unknown(getClass()));
    }
}
