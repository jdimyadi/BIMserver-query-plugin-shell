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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CompiledQuery implements QueryEngine {
	private final URLClassLoader classLoader;
	private Path rootPath;
  private Map<String, String> name_to_class = new LinkedHashMap<String, String>();

	private static final Logger LOGGER = LoggerFactory.getLogger(CompiledQuery.class);

	public CompiledQuery(URLClassLoader classLoader, Path rootPath, Map<String, String> name_to_class) {
		this.classLoader = classLoader;
		this.rootPath = rootPath;
    this.name_to_class = name_to_class;
	}
	@Override
	public IfcModelInterface query(IfcModelInterface model, String code, Reporter reporter, ModelHelper modelHelper) {
    try {
      if(!name_to_class.containsKey(code)) {
        reporter.info("Nonexistent filter tried.");
        return null;
      }
      QueryInterface the_query = (QueryInterface) classLoader.loadClass(name_to_class.get(code)).newInstance();
      return the_query.query(model, reporter, modelHelper);
    } catch (ClassNotFoundException e) {
      LOGGER.error("", e);
      reporter.error(e);
    } catch (InstantiationException e) {
      LOGGER.error("", e);
      reporter.error(e);
    } catch (IllegalAccessException e) {
      LOGGER.error("", e);
      reporter.error(e);
    }
    return null;
  }
}
