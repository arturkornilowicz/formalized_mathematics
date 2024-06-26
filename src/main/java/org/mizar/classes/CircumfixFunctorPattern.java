package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class CircumfixFunctorPattern extends FunctorPattern {

    private RightCircumflexSymbol rightCircumflexSymbol;
    private Loci loci;

    public CircumfixFunctorPattern(Element element) {
        super(element);
        rightCircumflexSymbol = new RightCircumflexSymbol(element.element(ESXElementName.RIGHT_CIRCUMFLEX_SYMBOL));
        loci = new Loci(element.element(ESXElementName.LOCI));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        rightCircumflexSymbol.run();
        loci.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public String kind() {
//        System.out.println(TranslationGenerator.generateTranslation(this).asXML());
        return "K";
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return Translation.texRepr(this,this.loci);
    }
}
