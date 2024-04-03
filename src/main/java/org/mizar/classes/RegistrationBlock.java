package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class RegistrationBlock extends Block {

    private List<String> clusterNames = List.of(ESXElementName.CONDITIONAL_REGISTRATION, ESXElementName.EXISTENTIAL_REGISTRATION, ESXElementName.FUNCTORIAL_REGISTRATION);

    public RegistrationBlock(Element element) {
        super(element);
    }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        super.process();
    }

    @Override
    public void postProcess() {
        super.postProcess();
    }

    @Override
    public void latexOutput() {
        if (isWithNoSpreadLoci()) {
            int i = 0;
            while (i < getItems().size() && !clusterNames.contains(getItems().get(i).getElement().getName())) {
                addLatexOutput(getItems().get(i++).texRepr(RepresentationCase.GENERAL).repr);
            }

            if (i == getItems().size() - 2) {
                addLatexOutput(" Now we can register the following property:");
            } else {
                addLatexOutput(" Now we can register the following properties:");
            }

            setLatexOutput(getLatexOutput() + "\\begin{itemize}");
            // -2 because of correctness condition
            for ( ; i < getItems().size() - 2; i++) {
                switch (getItems().get(i).getElement().getName()) {
                    case ESXElementName.CONDITIONAL_REGISTRATION:
                        ConditionalRegistration cr = (ConditionalRegistration) getItems().get(i);
                        addLatexOutput("\\item " + cr.texRepr(RepresentationCase.MERGED_CLUSTERS) + ",");
                        break;
                    case ESXElementName.EXISTENTIAL_REGISTRATION:
                        ExistentialRegistration er = (ExistentialRegistration) getItems().get(i);
                        addLatexOutput("\\item " + er.texRepr(RepresentationCase.MERGED_CLUSTERS) + ",");
                        break;
                    case ESXElementName.FUNCTORIAL_REGISTRATION:
                        FunctorialRegistration fr = (FunctorialRegistration) getItems().get(i);
                        addLatexOutput("\\item " + fr.texRepr(RepresentationCase.MERGED_CLUSTERS) + ",");
                        break;
                }
            }
            switch (getItems().get(i).getElement().getName()) {
                case ESXElementName.CONDITIONAL_REGISTRATION:
                    ConditionalRegistration cr = (ConditionalRegistration) getItems().get(i);
                    addLatexOutput("\\item " + cr.texRepr(RepresentationCase.MERGED_CLUSTERS) + ".");
                    break;
                case ESXElementName.EXISTENTIAL_REGISTRATION:
                    ExistentialRegistration er = (ExistentialRegistration) getItems().get(i);
                    addLatexOutput("\\item " + er.texRepr(RepresentationCase.MERGED_CLUSTERS) + ".");
                    break;
                case ESXElementName.FUNCTORIAL_REGISTRATION:
                    FunctorialRegistration fr = (FunctorialRegistration) getItems().get(i);
                    addLatexOutput("\\item " + fr.texRepr(RepresentationCase.MERGED_CLUSTERS) + ".");
                    break;
            }
            addLatexOutput("\\end{itemize}");
        } else {
            super.latexOutput();
        }
    }

    private boolean isWithNoSpreadLoci() {
        boolean result = true;
        boolean clusters = false;
        for (Item item: getItems()) {
            if (!clusterNames.contains(item.getElement().getName()) && !item.getElement().getName().equals(ESXElementName.CORRECTNESS_CONDITION)) {
                return false;
            }
            if (item.getElement().getParent().getName().equals(ESXElementName.CLUSTER)) {
                clusters = true;
            }
            if (clusters) {
                switch (item.getElement().getName()) {
                    case ESXElementName.CONDITIONAL_REGISTRATION: break;
                    case ESXElementName.EXISTENTIAL_REGISTRATION: break;
                    case ESXElementName.FUNCTORIAL_REGISTRATION: break;
                    case ESXElementName.CORRECTNESS_CONDITION: break;
                    default: return false;
                }
            }
        }
        return result;
    }
}
