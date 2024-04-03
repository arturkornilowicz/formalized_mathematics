package org.mizar.variables;

import lombok.*;

@Getter

public class Identifier {

    private String string;
    private String letters;
    private String digits;
    private int cuttingPos;
    private boolean wellComposed = true; //abc123

    public Identifier(String string) {
        this.string = string;
        letters = letters();
        digits = digits();
    }

    @Override
    public String toString() {
        return string;
    }

    private String letters() {
        cuttingPos = 0;
        while (cuttingPos < string.length() && Character.isLetter(string.charAt(cuttingPos))) {
            cuttingPos++;
        }
        return string.substring(0,cuttingPos);
    }

    private String digits() {
        String result = string.substring(cuttingPos);
        if (result.length() == 0) {
            wellComposed = false;
        }
        for (int i = 0; i < result.length(); i++) {
            wellComposed = wellComposed && Character.isDigit(result.charAt(i));
        }
        return result;
    }
}
