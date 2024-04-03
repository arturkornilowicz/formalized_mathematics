package org.mizar.latex;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter

public class Connective {

    private String repr;
    private Integer priority;
}
