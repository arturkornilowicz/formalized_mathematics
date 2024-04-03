package org.mizar.pub;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
public class Args {

    String kind;
    Integer number;
    Integer allArgs, leftArgs, rightArgs;

    @Override
    public String toString() {
        return kind + number + ": " + leftArgs + "(" + allArgs + ")" + rightArgs;
    }
}
