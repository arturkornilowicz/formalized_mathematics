package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.misc.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class Block extends XMLElement {

    private List<Item> items = new LinkedList<>();

    private String latexOutput = "";
    /* One sequence of a construction */
    private List<Item> constructions = new LinkedList<>();

    private Pattern lastDefinedPattern;

    public Block(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.ITEM)) {
            items.add(Item.buildItem(element1));
        }
    }

    public static Block buildBlock(Element element) {
        switch (element.attributeValue(ESXAttributeName.KIND)) {
            case "Case":
                return new Block(element);
            case "Definitional-Block":
                return new Block(element);
            case "Hereby-Reasoning":
                return new Block(element);
            case "Notation-Block":
                return new Block(element);
            case "Now-Reasoning":
                return new Block(element);
            case "Proof":
                return new Block(element);
            case "Registration-Block":
                return new RegistrationBlock(element);
            case "Scheme-Block":
                return new Block(element);
            case "Suppose":
                return new Block(element);
            default:
                Errors.error(element, "Missing Element in buildBlock [" + element.attributeValue(ESXAttributeName.KIND) + "]");
                return null;
        }
     }

    @Override
    public void preProcess() {
        super.preProcess();
    }

    @Override
    public void process() {
        for (Item item: items) {
            item.run();
        }
    }

    @Override
    public void postProcess() {
        latexOutput();
        super.postProcess();
    }

    public void addLatexOutput(String string) {
        setLatexOutput(getLatexOutput() + string);
    }

    private void latexOutputConstruction(String constructionName, int i, String intro) {
        if (i <= 1 || !items.get(i - 2).getElement().getName().equals(constructionName)) {
            constructions.clear();
            addLatexOutput(intro);
        }
        constructions.add(items.get(i));
        if (i == items.size() - 2 || i < items.size() - 2 && !items.get(i + 2).getElement().getName().equals(constructionName)) {
            addLatexOutput(LaTeX.texReprTextAnd(constructions,RepresentationCase.GENERAL) + ".");
            constructions.clear();
        }
    }

    public void latexOutput() {
        for (int i = 0; i < items.size(); i++) {
            switch (items.get(i).getElement().getName()) {
                case ESXElementName.DEFINITION_ITEM:
                    LaTeX.addText(items.get(i).getLatexOutput());
                    break;
                case ESXElementName.FUNCTOR_DEFINITION:
                    lastDefinedPattern = ((FunctorDefinition) items.get(i)).getFunctorPattern();
                    setLatexOutput(getLatexOutput() + items.get(i).texRepr(RepresentationCase.GENERAL));
                    break;
                case ESXElementName.PROPERTY:
                    if (i > 0 && !items.get(i - 1).getElement().getName().equals(ESXElementName.PROPERTY)) {
                        constructions.clear();
                    }
                    constructions.add(items.get(i));
                    if (i == items.size() - 1 || i < items.size() - 1 && !items.get(i + 1).getElement().getName().equals(ESXElementName.PROPERTY)) {
                        setLatexOutput(getLatexOutput() + Texts.T2d + lastDefinedPattern.texRepr(RepresentationCase.GENERAL).repr + " is ");
                        setLatexOutput(getLatexOutput() + LaTeX.texReprTextAnd(constructions,RepresentationCase.GENERAL));
                        setLatexOutput(getLatexOutput() + ".");
                        constructions.clear();
                    }
                    break;
                case ESXElementName.MODE_DEFINITION:
                    lastDefinedPattern = ((ModeDefinition) items.get(i)).getModePattern();
                    setLatexOutput(getLatexOutput() + items.get(i).texRepr(RepresentationCase.GENERAL));
                    break;
                case ESXElementName.PREDICATE_DEFINITION:
                    lastDefinedPattern = ((PredicateDefinition) items.get(i)).getPredicatePattern();
                    setLatexOutput(getLatexOutput() + items.get(i).texRepr(RepresentationCase.GENERAL));
                    break;
                case ESXElementName.RESERVATION:
                    LaTeX.addText(items.get(i).getLatexOutput());
                    break;
                case ESXElementName.SECTION_PRAGMA:
                    LaTeX.addText(items.get(i).texRepr(RepresentationCase.GENERAL).repr);
                    break;
                case ESXElementName.THEOREM_ITEM:
                    if (i > 0 && !items.get(i - 1).getElement().getName().equals(ESXElementName.THEOREM_ITEM)) {
                        constructions.clear();
                    }
                    constructions.add(items.get(i));
                    if (i == items.size() - 1 || i < items.size() - 1 && !items.get(i + 1).getElement().getName().equals(ESXElementName.THEOREM_ITEM)) {
                        LaTeX.texTheorems(constructions);
                        constructions.clear();
                    }
                    break;
                case ESXElementName.IDENTIFY:
                    latexOutputConstruction(ESXElementName.IDENTIFY,i,Texts.T2);
                    break;
                case ESXElementName.REDUCTION:
                    latexOutputConstruction(ESXElementName.REDUCTION,i,Texts.T2);
                    break;
                case ESXElementName.SCHEME_BLOCK_ITEM:
                    LaTeX.addText(items.get(i).texRepr(RepresentationCase.GENERAL).repr);
                    break;
                default:
                    setLatexOutput(getLatexOutput() + items.get(i).texRepr(RepresentationCase.GENERAL));
//                    Errors.error(items.get(i).getElement(), "BAD TEST IN BLOCK");
            }
        }
    }
}
