package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.application.FormalizedMathematics;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class SectionPragma extends Item {

    public SectionPragma(Element element) {
        super(element);
    }

    @Override
    public void preProcess() { super.preProcess(); }

    @Override
    public void process() {}

    @Override
    public void postProcess() { super.postProcess(); }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation("{\\vskip2ex\\penalty-1000\\begin{center}\\sc "
                + FormalizedMathematics.sections.get(LaTeX.sectionCounter++)
                + " \\end{center}\\vskip1ex\\nopagebreak}");
    }
}
