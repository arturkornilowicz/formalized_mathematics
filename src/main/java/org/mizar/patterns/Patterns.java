package org.mizar.patterns;

import lombok.*;
import org.mizar.xml_names.*;

/*
    This class represents all possible patterns
*/

@Getter
@AllArgsConstructor
public enum Patterns {

    ATTRIBUTE_DEFINITION(ESXElementName.ATTRIBUTE_DEFINITION,ESXElementName.ATTRIBUTE_PATTERN,Patterns.AttributeCnt),
    ATTR_ANTONYM(ESXElementName.ATTR_ANTONYM,ESXElementName.ATTRIBUTE_PATTERN,Patterns.AttributeCnt),
    ATTR_SYNONYM(ESXElementName.ATTR_SYNONYM,ESXElementName.ATTRIBUTE_PATTERN,Patterns.AttributeCnt),


    CIRCUMFIX_FUNCTOR_DEFINITION(ESXElementName.FUNCTOR_DEFINITION,ESXElementName.CIRCUMFIXFUNCTOR_PATTERN,Patterns.FunctorCnt),
    CIRCUMFIXFUNC_SYNONYM(ESXElementName.FUNC_SYNONYM,ESXElementName.CIRCUMFIXFUNCTOR_PATTERN,Patterns.FunctorCnt),
    INFIX_FUNCTOR_DEFINITION(ESXElementName.FUNCTOR_DEFINITION,ESXElementName.INFIXFUNCTOR_PATTERN,Patterns.FunctorCnt),
    INFIXFUNC_SYNONYM(ESXElementName.FUNC_SYNONYM,ESXElementName.INFIXFUNCTOR_PATTERN,Patterns.FunctorCnt),


    MODE_DEFINITION(ESXElementName.MODE_DEFINITION,ESXElementName.MODE_PATTERN,Patterns.ModeCnt),
    MODE_SYNONYM(ESXElementName.MODE_SYNONYM,ESXElementName.MODE_PATTERN,Patterns.ModeCnt),


    PREDICATE_DEFINITION(ESXElementName.PREDICATE_DEFINITION,ESXElementName.PREDICATE_PATTERN,Patterns.PredicateCnt),
    PRED_ANTONYM(ESXElementName.PRED_ANTONYM,ESXElementName.PREDICATE_PATTERN,Patterns.PredicateCnt),
    PRED_SYNONYM(ESXElementName.PRED_SYNONYM,ESXElementName.PREDICATE_PATTERN,Patterns.PredicateCnt),


    STRUCTURE_DEFINITION(ESXElementName.STRUCTURE_DEFINITION,ESXElementName.STRUCTURE_PATTERN,Patterns.StructureCnt),
    AGGREGATE_PATTERN(ESXElementName.STRUCTURE_DEFINITION,
            ESXElementName.STRUCTURE_PATTERNS_RENDERING+"/"+ESXElementName.AGGREGATEFUNCTOR_PATTERN,
            Patterns.AggregateCnt),
    SELECTOR_PATTERN(ESXElementName.STRUCTURE_DEFINITION,
            ESXElementName.STRUCTURE_PATTERNS_RENDERING+"/"+ESXElementName.SELECTORS_LIST+"/"+ESXElementName.SELECTORFUNCTOR_PATTERN,
            Patterns.SelectorCnt),
    FORGETFUL_FUNCTOR_PATTERN(ESXElementName.STRUCTURE_DEFINITION,
            ESXElementName.STRUCTURE_PATTERNS_RENDERING+"/"+ESXElementName.FORGETFULFUNCTOR_PATTERN,
            Patterns.ForgetfulCnt);

    private static final String AttributeCnt = "Attribute";
    private static final String FunctorCnt = "Functor";
    private static final String ModeCnt = "Mode";
    private static final String PredicateCnt = "Predicate";
    private static final String StructureCnt = "Structure";
    private static final String SelectorCnt = "Selector";
    private static final String ForgetfulCnt = "Forgetful";
    private static final String AggregateCnt = "Aggregate";

    private String repr;
    private String ESXname;
    private String sortCounter;
}
