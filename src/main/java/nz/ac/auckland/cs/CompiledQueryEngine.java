package nz.ac.auckland.cs;

import org.bimserver.plugins.queryengine.*;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.ModelHelper;
import org.bimserver.plugins.Reporter;

class CompiledQueryPlugin implements QueryEnginePlugin {
  QueryEngine getQueryEngine(PluginConfiguration pluginConfiguration) throws PluginException {
    return new CompiledQuery();
  }
  Collection<String> getExampleKeys() {
    // Our 'example' keys are the names of all of the filters that are loaded
    // The values are the same as the key, as we only really name a filter,
    // and the filter does all the filtering.
    // We get this from ...
    return null;
  }
  String getExample(String key) {
    // Our 'examples' are the filters that are loaded.
    return "";
  }
}
