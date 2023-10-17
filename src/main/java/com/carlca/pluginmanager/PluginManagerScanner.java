package com.carlca.pluginmanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import javax.xml.parsers.*;
import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import org.javatuples.Triplet;
import org.json.JSONObject;

public class PluginManagerScanner {

	// private fields
	private PluginManagerFileCounter fileCounter;

	// property fields
	private String csvFileName;

	public List<Triplet<String, String, String>> getPlugins() {
		return plugins;
	}

	private PluginManagerScannerUtils utils;

	private List<Triplet<String, String, String>> plugins;

	public String getCsvFileName() {
		return csvFileName;
	}

	public static void main(String[] args) {
		PluginManagerScanner scanner = new PluginManagerScanner();
		scanner.processPlugins("");
	}

	// constructor
	public PluginManagerScanner() {
		fileCounter = new PluginManagerFileCounter();
		utils = new PluginManagerScannerUtils();
	}

	public void processPlugins(String pluginType) {
		// cater for when PluginManagerScanner is run without the front-end UI
		pluginType = ensureDefaultIfEmpty(pluginType, "CLAP");
		// check for DEMO plugin type
		if (pluginType.equals("DEMO")) {
			plugins = PluginManagerDemoData.getDemoData();
		} else {
			// use upper case for plugin folder
			String pluginFolder = "/Library/Audio/Plug-Ins" + "/" + pluginType + "/";
			// then switch to lower case
			pluginType = pluginType.toLowerCase();
			// build list of plugin triplets
			plugins = buildPluginTriplets(pluginType, pluginFolder);
			// Sort the plugin triplets
			utils.sortPluginTripletsByManufacturerAndPlugin(plugins);
			// Print the plugins to the console
			printPluginsToConsole(plugins);
		}
	}

	@NotNull
	private List<Triplet<String, String, String>> buildPluginTriplets(String pluginType, String pluginFolder) {
		Path startPath = Paths.get(pluginFolder);
		List<Triplet<String, String, String>> plugins = new ArrayList<>();
		List<Path> paths = new ArrayList<>();
		try {
			buildPListFileList(startPath, paths);
			// Process the files
			for (Path file : paths) {
				// This build the `paths` list of Manufacturer, Ident, Plugin Triplets
				processOnePListFile(pluginType, pluginFolder, plugins, file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return plugins;
	}

	@NotNull
	private static String ensureDefaultIfEmpty(String pluginType, String defaultType) {
		if (pluginType.isEmpty()) {
			pluginType = defaultType;
		}
		return pluginType;
	}

	private static void sortPluginTripletsByManufacturer(List<Triplet<String, String, String>> plugins) {
		plugins.sort(Comparator.comparing(Triplet::getValue0)); //.thenComparing(Triplet::getValue2));
	}

	private static void printPluginsToConsole(List<Triplet<String, String, String>> plugins) {
		for (Triplet<String, String, String> plugin : plugins) {
			System.out.println(plugin.getValue0() + " - " + plugin.getValue1() + " - " + plugin.getValue2());
		}
	}

	private void processOnePListFile(String pluginType, String pluginFolder, List<Triplet<String, String, String>> plugins,
			Path file) throws IOException {
		String plugin = file.toString().replace(pluginFolder, "");
		plugin = plugin.replace("." + pluginType + "/Contents/Info.plist", "");
		plugin = plugin + '.' + pluginType;
		// Open plist file as XML
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file.toFile());
			// Extract the CFBundleIdentifier key-value pair
			NodeList keys = doc.getElementsByTagName("key");
			String ident = "";
			for (int i = 0; i < keys.getLength(); i++) {
				Node key = keys.item(i);
				if (key.getTextContent().equals("CFBundleIdentifier")) {
					Node value = key.getNextSibling();
					while (value != null && value.getNodeType() != Node.ELEMENT_NODE) {
						value = value.getNextSibling();
					}
					if (value != null) {
						ident = value.getTextContent().toLowerCase();
						String manufacturer = utils.createManufacturer(ident, plugin, pluginType);
						plugins.add(
								new Triplet<>(manufacturer.toLowerCase(), ident, plugin));
					}
				}
			}
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

	private void buildPListFileList(Path startPath, List<Path> paths) throws IOException {
		Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!file.toString().contains(".bundle")) {
					if (file.toString().endsWith(".plist")) {
						paths.add(file);
					}
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}

	Map<String, List<String>> getPluginsByManufacturer(List<String> plugins) {
		Map<String, List<String>> pluginsByManufacturer = new HashMap<>();
		for (String plugin : plugins) {
			String[] parts = plugin.split(" - ");
			String manufacturer = parts[0];
			String ident = parts[1];
			String pluginName = parts[2];

			if (!pluginsByManufacturer.containsKey(manufacturer)) {
				pluginsByManufacturer.put(manufacturer, new ArrayList<>());
			}

			pluginsByManufacturer.get(manufacturer).add(ident + " - " + pluginName);
		}
		return pluginsByManufacturer;
	}

}
