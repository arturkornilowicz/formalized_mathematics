package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class MultiAttributiveFormula extends Formula {

    private Term term;
    private AdjectiveCluster adjectiveCluster;

    public MultiAttributiveFormula(Element element) {
        super(element);
        term = Term.buildTerm(element.elements().get(0));
        adjectiveCluster = new AdjectiveCluster(element.element(ESXElementName.ADJECTIVE_CLUSTER));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        term.run();
        adjectiveCluster.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String adjRepr = adjectiveCluster.texRepr(representationCase).repr;
        String string = term.texRepr(representationCase).repr + " ";
        //TODO
        string += switch (adjectiveCluster.getAdjectiveKind()) {
            case AdjectiveKind.SAT -> " satisfies ";
            case AdjectiveKind.HAS -> " has ";
            default -> " is ";
        };
        string += adjRepr;
        return new Representation(string);
    }
}
