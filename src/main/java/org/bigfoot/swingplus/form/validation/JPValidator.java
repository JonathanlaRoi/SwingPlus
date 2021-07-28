package org.bigfoot.swingplus.form.validation;

import java.util.Objects;

public abstract class JPValidator<TYPE> {
	private String id;
	
	public JPValidator(String id){
		setId(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract String validate(TYPE value);
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof JPValidator))
			return false;
		JPValidator other = (JPValidator) obj;
		return Objects.equals(this.getId(), other.getId());
	}
}
