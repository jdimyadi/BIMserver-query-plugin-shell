package nz.ac.auckland.cs;

import org.bimserver.plugins.queryengine.*;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;

import org.bimserver.models.store.ObjectDefinition;
import java.util.Collection;

import org.bimserver.plugins.Plugin;
import org.bimserver.plugins.PluginConfiguration;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bimserver.models.store.ObjectDefinition;
import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.PluginContext;
import org.bimserver.plugins.PluginException;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.queryengine.QueryEngine;
import org.bimserver.plugins.queryengine.QueryEnginePlugin;
import org.bimserver.utils.PathUtils;

import com.google.common.base.Charsets;


class CompiledQueryPlugin implements QueryEnginePlugin {
	private boolean initialized = false;
  // These are referred to as 'examples' in the UI, but these are actually all of the available compiled MVDs
  // Each 'query' is just a single named MVD at this point.
  // The MVD folder is filled with folders; each one contains an MVD. The folder name is the MVD name, and the
  // entrypoint class has a standard name (MVDQuery.class)
	private final Map<String, String> examples = new LinkedHashMap<String, String>(); 
	private PluginManager pluginManager;

	@Override
	public void init(PluginManager pluginManager) throws PluginException {
		this.pluginManager = pluginManager;
		initialized = true;
		initExamples(pluginManager);
	}

	private void initExamples(PluginManager pluginManager) {
		PluginContext pluginContext = pluginManager.getPluginContext(this);
		try {
			for (Path path : PathUtils.list(pluginContext.getRootPath().resolve("MVD"))) {
				//InputStream inputStream = Files.newInputStream(path);
				examples.put(path.getFileName().toString(), path.getFileName().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Collection<String> getExampleKeys() {
		return examples.keySet();
	}

	public String getExample(String key) {
		return examples.get(key);
	}
	
	@Override
	public String getDescription() {
		return "Compiled Query Engine Plugin";
	}

	@Override
	public String getVersion() {
		return "0.0";
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public QueryEngine getQueryEngine(PluginConfiguration pluginConfiguration) {
		PluginContext pluginContext = pluginManager.getPluginContext(this);
		return new CompiledQuery(pluginContext.getClassLoader(), pluginContext.getRootPath());
	}

	@Override
	public String getDefaultName() {
		return "CompiledQueryPlugin";
	}

	@Override
	public ObjectDefinition getSettingsDefinition() {
		return null;
	}

}
