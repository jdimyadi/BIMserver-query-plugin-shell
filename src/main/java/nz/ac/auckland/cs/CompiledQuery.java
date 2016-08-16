package nz.ac.auckland.cs;

import org.bimserver.plugins.queryengine.*;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;
import org.bimserver.plugins.VirtualClassLoader;
import org.bimserver.plugins.VirtualFile;
import org.bimserver.plugins.VirtualFileManager;
import org.bimserver.plugins.queryengine.QueryEngine;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URLClassLoader;

class CompiledQuery implements QueryEngine {
	private final URLClassLoader classLoader;
	private Path rootPath;
  private Map<String, String> name_to_class = new LinkedHashMap<String, String>();

	public CompiledQuery(URLClassLoader classLoader, Path rootPath, Map<String, String> name_to_class) {
		this.classLoader = classLoader;
		this.rootPath = rootPath;
    this.name_to_class = name_to_class;
	}
	@Override
	public IfcModelInterface query(IfcModelInterface model, String code, Reporter reporter, ModelHelper modelHelper) {
    try {
      if(!name_to_class.containsValue(code)) {
        return null;
      }
      QueryInterface the_query = (QueryInterface) classLoader.loadClass(name_to_class.get(code)).newInstance();
      return the_query.query(model, reporter, modelHelper);
    } catch (ClassNotFoundException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }
    return null;
  }
}
