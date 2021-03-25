package com.uniajc.schoolpickup.generics;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    return '@' + this.getClass().getName() + this.toJson();
  }

  @Override
  public boolean equals(Object object) {
    return object == null ? false : toString().equals(object.toString());
  }

  public String toJson() {
    try {
      return (new ObjectMapper()).writeValueAsString(this);
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  @Override
  public int hashCode() {
    return this.toJson().hashCode();
  }
}
