package ua.goit.notes;

import java.io.Serializable;

@FunctionalInterface
public interface BaseEntity<ID> extends Serializable {

    ID getId();

}
