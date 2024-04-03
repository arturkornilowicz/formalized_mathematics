package org.mizar.classes;

import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;

@Setter
@Getter
@ToString

public class ConditionalRegistration extends Cluster {

    private AdjectiveCluster pred;
    private AdjectiveCluster succ;
    private Type type;

    public ConditionalRegistration(Element element) {
        super(element);
        pred = new AdjectiveCluster(element.elements().get(0));
        succ = new AdjectiveCluster(element.elements().get(1));
        type = Type.buildType(element.elements().get(2));
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        pred.run();
        succ.run();
        type.run();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";

        switch (representationCase) {
            case RepresentationCase.MERGED_CLUSTERS:
                result += type.texRepr(representationCase);
                if (pred.getAttributes().size() > 0) {
                    result += Texts.S6 + pred.texRepr(representationCase) + Texts.S7 + succ.texRepr(representationCase);
                } else {
                    result += Texts.IS + succ.texRepr(representationCase);
                }
                return new Representation(result);
            default:
                result += Texts.T2b + type.texRepr(representationCase);
                if (pred.getAttributes().size() > 0) {
                    result += Texts.S6 + pred.texRepr(representationCase) + Texts.S7 + succ.texRepr(representationCase) + ".";
                } else {
                    result += Texts.IS + succ.texRepr(representationCase) + ".";
                }
                return new Representation(result);
        }
    }
}
