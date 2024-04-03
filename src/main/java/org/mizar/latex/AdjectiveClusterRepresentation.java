package org.mizar.latex;

import java.util.*;

public class AdjectiveClusterRepresentation extends LinkedHashMap<String,AdjectiveDescr> {

    public static String pos = "_pos";
    public static String neg = "_neg";
    public static String regular = "regular";

    public AdjectiveClusterRepresentation() {
        init();
    }

    public String caseName(String name, String sign) {
        return name + sign;
    }

    public void init() {

        //TODO ""
        put(caseName(regular,pos), new AdjectiveDescr("","",new ArrayList<>()));
        put(caseName(regular,neg), new AdjectiveDescr("non","non",new ArrayList<>()));

        put(caseName(AdjectiveKind.NOUN,pos), new AdjectiveDescr("","",new ArrayList<>()));
        put(caseName(AdjectiveKind.NOUN,neg), new AdjectiveDescr("not","not",new ArrayList<>()));

        put(caseName(AdjectiveKind.SAT,pos), new AdjectiveDescr("satisfies","satisfying",new ArrayList<>()));
        put(caseName(AdjectiveKind.SAT,neg), new AdjectiveDescr("does not satisfy","not satisfying",new ArrayList<>()));

        put(caseName(AdjectiveKind.HAS,pos), new AdjectiveDescr("has","having",new ArrayList<>()));
        put(caseName(AdjectiveKind.HAS,neg), new AdjectiveDescr("does not have","not having",new ArrayList<>()));

        put(caseName(AdjectiveKind.INHERITS,pos), new AdjectiveDescr("inherits","inheriting",new ArrayList<>()));
        put(caseName(AdjectiveKind.INHERITS,neg), new AdjectiveDescr("does not inherite","not inheriting",new ArrayList<>()));

        put(caseName(AdjectiveKind.PRESERVES,pos), new AdjectiveDescr("preserves","preserving",new ArrayList<>()));
        put(caseName(AdjectiveKind.PRESERVES,neg), new AdjectiveDescr("does not preserve","not preserving",new ArrayList<>()));
    }

    @Override
    public String toString() {
        String result = "";
        for (String s: keySet()) {
            result += s + ": " + get(s) + " ";
        }
        return result;
    }
}
