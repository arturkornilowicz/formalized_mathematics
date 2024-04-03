package org.mizar.latex;

public interface Connectives {

    //TODO flex

    Connective ATOMIC = new Connective("",200);
    Connective CONJUNCTION = new Connective("and",120);
    Connective FLEX_CONJUNCTION = new Connective("AND",100);
    Connective DISJUNCTION = new Connective("or",80);
    Connective FLEX_DISJUNCTION = new Connective("OR",60);
    Connective IMPLICATION = new Connective("implies",40);
    Connective BIIMPLICATION = new Connective("iff",20);

}
