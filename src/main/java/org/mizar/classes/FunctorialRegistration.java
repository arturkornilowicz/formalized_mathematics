package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class FunctorialRegistration extends Cluster {

    private Term term;
    private AdjectiveCluster adjectiveCluster;
    private Type type;

    public FunctorialRegistration(Element element) {
        super(element);
        term = Term.buildTerm(element.elements().get(0));
        adjectiveCluster = new AdjectiveCluster(element.element(ESXElementName.ADJECTIVE_CLUSTER));
        if (element.elements().size() > 2) {
            type = Type.buildType(element.elements().get(2));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        term.run();
        adjectiveCluster.run();
        if (type != null) {
            type.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        //TODO add type
        return switch (representationCase) {
            case RepresentationCase.MERGED_CLUSTERS -> new Representation(LaTeX.ensureMath(term.texRepr(representationCase).repr) + Texts.IS + adjectiveCluster.texRepr(representationCase));
            default -> new Representation(Texts.T2 + LaTeX.ensureMath(term.texRepr(representationCase).repr) + Texts.IS + adjectiveCluster.texRepr(representationCase) + ".");
        };
    }
}
