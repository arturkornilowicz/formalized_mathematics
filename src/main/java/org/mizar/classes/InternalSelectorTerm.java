package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class InternalSelectorTerm extends Term {

    public InternalSelectorTerm(Element element) {
        super(element);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {}

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public String kind() { return "U"; }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO their
        return new Representation(LaTeX.text("the " + Translation.texRepr(this)));
    }
}
