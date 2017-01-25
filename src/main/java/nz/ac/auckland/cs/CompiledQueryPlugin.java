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
import org.bimserver.plugins.PluginChangeListener;
import org.bimserver.plugins.queryengine.QueryEngine;
import org.bimserver.plugins.queryengine.QueryEnginePlugin;
import org.bimserver.utils.PathUtils;
import java.util.ArrayList;
import java.net.URLClassLoader;
import com.google.common.base.Charsets;
import java.net.URI;


public class CompiledQueryPlugin implements QueryEnginePlugin, PluginChangeListener {
	private boolean initialized = false;
  // These are referred to as 'examples' in the UI, but these are actually all of the available compiled MVDs
  // Each 'query' is just a single named MVD at this point.
  // The MVD folder is filled with folders; each one contains an MVD. The folder name is the MVD fully qualified class name, and the
  // root of the folder contains a standard file (name.txt) that contains the human readable name/description for the filter
	private final Map<String, String> examples = new LinkedHashMap<String, String>(); 
	private final Map<String, String> name_to_class = new LinkedHashMap<String, String>(); 
	private PluginManager pluginManager;
  private ArrayList<java.net.URL> jarlist = new ArrayList<java.net.URL>();
  private final java.net.URL[] signal = {};
  private URI base_path;

  public void pluginStateChanged(PluginContext context, boolean enabled) {
    if(enabled && initialized) {
      // Explicitly redo initialization every time you reenable the plugin
      // This allows for a quick and easy way to force refresh the mvd catalog
      initExamples(context);
    }
  }

	@Override
	public void init(PluginManager pluginManager) throws PluginException {
    try {
      base_path = new URI("https://raw.githubusercontent.com/jdimyadi/bim-bits/master/");
    } catch (java.net.URISyntaxException e) {
    }

    // This may cause a memory leak
    pluginManager.addPluginChangeListener(this);
		
    this.pluginManager = pluginManager;
		initialized = true;
		initExamples(pluginManager);
	}

	private void initExamples(PluginManager pluginManager) {
		PluginContext pluginContext = pluginManager.getPluginContext(this);
    initExamples(pluginContext);
  }
	private void initExamples(PluginContext pluginContext) {
		try {
      
      InputStream names_in = base_path.resolve("all.txt").toURL().openStream();
      String[] names = IOUtils.toString(names_in, Charsets.UTF_8.name()).split("\n");
      names_in.close();
      for(String class_name : names) {
        URI mvd_base = base_path.resolve("MVD/" + class_name + "/");
        InputStream name_stream = mvd_base.resolve("name.txt").toURL().openStream();
        String human_readable_name = IOUtils.toString(name_stream, Charsets.UTF_8.name());
        name_stream.close();
        examples.put(human_readable_name, human_readable_name);
        name_to_class.put(human_readable_name, class_name);
        jarlist.add(mvd_base.resolve("mvd.jar").toURL());
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
		return new CompiledQuery(new URLClassLoader(jarlist.toArray(signal), pluginContext.getClassLoader()), pluginContext.getRootPath(), name_to_class);
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
