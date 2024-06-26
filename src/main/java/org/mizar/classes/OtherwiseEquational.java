package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class OtherwiseEquational extends Otherwise {

    private Term term;

    public OtherwiseEquational(Element element) {
        super(element);
        if (element.elements().size() > 0) {
            term = Term.buildTerm(element.elements().get(0));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        if (term != null) {
            term.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(term != null ? "\n\\item" + term.texRepr(representationCase) + ", " + Texts.OTHERWISE + "." : "");
    }
}
