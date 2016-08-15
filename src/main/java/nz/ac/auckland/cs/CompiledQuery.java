package nz.ac.auckland.cs;

import org.bimserver.plugins.queryengine.*;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;
import java.io.File;
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

class CompiledQuery implements QueryEngine {
  private static String libPath = System.getProperty("java.class.path");
	private final ClassLoader classLoader;
	//private final JavaFileManager pluginFileManager;
	private Path rootPath;

	public CompiledQuery(ClassLoader classLoader, Path rootPath) {
		this.classLoader = classLoader;
		this.rootPath = rootPath;
		//this.pluginFileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);
	}
	@Override
	public IfcModelInterface query(IfcModelInterface model, String code, Reporter reporter, ModelHelper modelHelper) {
    return null;
  }
}
