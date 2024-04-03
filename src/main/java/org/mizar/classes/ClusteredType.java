package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ClusteredType extends Type {

    private AdjectiveCluster adjectiveCluster;
    private Type type;

    public ClusteredType(Element element) {
        super(element);
        adjectiveCluster = new AdjectiveCluster(element.element(ESXElementName.ADJECTIVE_CLUSTER));
        type = Type.buildType(element.elements().get(1));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        adjectiveCluster.run();
        type.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        Representation typeRepr = type.texRepr(representationCase);
        String firstAttr = adjectiveCluster.getAttributes().get(0).texRepr(representationCase).repr;
        String preposition = typeRepr.plural ? "" : LaTeX.preposition(firstAttr);
        return new Representation(preposition + adjectiveCluster.texRepr(representationCase) + " " + typeRepr);
    }
}
