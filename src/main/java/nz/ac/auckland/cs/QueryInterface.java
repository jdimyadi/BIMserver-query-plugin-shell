package nz.ac.auckland.cs;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;

public interface QueryInterface {
  public IfcModelInterface query(IfcModelInterface model, Reporter reporter, ModelHelper modelHelper);
}
