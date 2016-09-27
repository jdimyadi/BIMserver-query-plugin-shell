Installing
----------

This project depends on quite a few jars that are distributed as part of the main `bimserver-1.4.0-FINAL-2015-11-04.jar`.

You will need the jars `bimserver-1.4.0-FINAL-2015-11-04-shared.jar:bimserver-1.4.0-FINAL-2015-11-04.jar:commons-io-1.4.jar:guava-18.0.jar:slf4j-api-1.6.2.jar` extracted from the main `bimserver-1.4.0-FINAL-2015-11-04.jar` and put on your `CLASSPATH`.

Other than that, it can be built like any normal java project, with the exception that you must include the `plugin` directory into any generated jars.

There is a Makefile included with this project that will build it. The make file assumes that the BIMserver jars have been extracted to an `unmanaged_libs` directory; this can be changed by setting the `BIMSERVER_PATH` variable, either in the Makefile, or as a parameter to make.

To add the plugin to a running BIMserver instance, you just need to move the generated jar file to the BIMserver plugin jar directory, and restart BIMserver. You can then enable or disable the plugin as usual from the BIMserver admin page.

This version of the plugin will look for MVDs at `https://raw.githubusercontent.com/flaviusb/bim-bits/master/`. You have to change the location in the code in `src/main/java/nz/ac/auckland/cs/CompiledQueryPlugin.java` if you want it to look somewhere else.
