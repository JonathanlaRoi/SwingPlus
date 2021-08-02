package org.bigfoot.swingplus.form.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public abstract class JPFormValidator<TYPE> {
    @Getter
    @Setter
    private String id;

    public JPFormValidator(String id) {
        setId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JPFormValidator))
            return false;
        JPFormValidator other = (JPFormValidator) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    public abstract String validate(TYPE value);
}
