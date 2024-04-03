package org.mizar.classes;

import java.util.*;
import lombok.*;
import org.dom4j.*;
import org.mizar.latex.*;
import org.mizar.xml_names.*;

@Setter
@Getter
@ToString

public class Reservation extends Item {

    private List<ReservationSegment> segments = new LinkedList<>();

    public Reservation(Element element) {
        super(element);
        for (Element element1: element.elements(ESXElementName.RESERVATION_SEGMENT)) {
            segments.add(new ReservationSegment(element1));
        }
    }

    @Override
    public void preProcess() {
        super.preProcess();
        setLatexOutput(Texts.R1);
    }

    @Override
    public void process() {
        for (ReservationSegment reservationSegment: segments) {
            reservationSegment.run();
        }
    }

    @Override
    public void postProcess() {
        setLatexOutput(getLatexOutput() + texRepr(RepresentationCase.GENERAL) + ".");
        super.postProcess();
    }

    @Override
    public Representation texRepr(Integer representationCase) {
        return new Representation(LaTeX.texReprTextAnd(segments,representationCase));
    }
}
