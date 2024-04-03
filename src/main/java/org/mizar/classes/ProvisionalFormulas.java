package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class ProvisionalFormulas extends XMLElement {

    private List<Proposition> propositions = new LinkedList<>();

    public ProvisionalFormulas(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.PROPOSITION)) {
            propositions.add(new Proposition(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Proposition proposition: propositions) {
            proposition.run();
        }
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        String result = "";
        result += "\\begin{itemize}";
        int i;
        for (i = 0; i < propositions.size()-2; i++) {
            result += "\\item " + propositions.get(i).texRepr(representationCase) + ",\n";
        }
        if (propositions.size() > 1) {
            result += "\\item " + propositions.get(i++).texRepr(representationCase) + ", and\n";
        }
        result += "\\item " + propositions.get(i).texRepr(representationCase) + ".\n";
        result += "\\end{itemize}";
        return new Representation(result);
    }
}
