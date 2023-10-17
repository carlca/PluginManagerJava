package com.carlca.pluginmanager;

import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginManagerScannerUtils {

  public void sortPluginTripletsByManufacturerAndPlugin(List<Triplet<String, String, String>> plugins) {
    plugins.sort(Comparator.comparing((Triplet<String, String, String> triplet) -> 
            triplet.getValue0()).thenComparing(triplet -> triplet.getValue2()));
  }
	
	public String createManufacturer(String ident, String plugin, String pluginType) {
		Map<String, String> replacements = getReplacementsMap(plugin, pluginType);

		String manufacturer = ident;
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			manufacturer = manufacturer.replaceFirst(entry.getKey(), entry.getValue());
		}

		manufacturer = stripLastCharacter(manufacturer);
		manufacturer = stripLastCharacter(manufacturer);

		if (manufacturer.isEmpty()) {
			manufacturer = "com.native-instruments";
		}

		if (!manufacturer.contains("w.a.production")) {
			int dotPos = manufacturer.indexOf(".");
			if (dotPos > 0) {
				manufacturer = manufacturer.substring(0, dotPos);
			}
		}

		manufacturer = checkSpecialCases(manufacturer, ident, plugin);

		return manufacturer;
	}

	@NotNull
	private Map<String, String> getReplacementsMap(String plugin, String pluginType) {
		Map<String, String> replacements = new HashMap<>();
		replacements.put(plugin.toLowerCase(), "");
		replacements.put(pluginType, "");
		replacements.put("\\.\\.", ".");
		replacements.put("uk\\.co\\.", "");
		replacements.put("co\\.uk\\.", "");
		replacements.put("jp\\.co\\.", "");
		replacements.put("com\\.", "");
		replacements.put("net\\.", "");
		replacements.put("se\\.", "");
		replacements.put("ch\\.", "");
		replacements.put("org\\.", "");
		replacements.put("ca\\.", "");
		replacements.put("de\\.", "");
		replacements.put("jp\\.", "");
		replacements.put("ly\\.", "");
		replacements.put("cn\\.", "");
		replacements.put("maizesoft.msp", "samplescience");
		return replacements;
	}

	private String checkSpecialCases(String manufacturer, String ident, String plugin) {
		if (manufacturer.contains("w.a.production") || plugin.contains("Dragonfly")) {
			return plugin;
		}

		if (ident.contains("kontrol") || ident.contains("kontakt") || ident.contains("phasis")
			|| ident.contains("supercharger") || ident.contains("replika") || ident.contains("reaktor")
			|| ident.contains("raum.fx") || ident.contains("guitar rig") || ident.contains("freak.fx.vst")
			|| ident.contains("flair.fx.vst") || ident.contains("fm8.fx.vst") || ident.contains("fm8.synth.vst")
			|| ident.contains("choral") || ident.contains("absynth")) {
			return "native instruments";
		}

		if (manufacturer.contains("Native-Instruments")) {
			return "native instruments";
		}

		if (manufacturer.equals("g")) {
			return "gtune";
		}

		if (plugin.equals("Xhip") || plugin.equals("NeuralNote")) {
			return plugin.toLowerCase();
		}

		if (plugin.contains("Odin2")) {
			return "the wave warden";
		}

		if (plugin.contains("Darvasa")) {
			return "igorski";
		}

		if (ident.contains(".korg.")) {
			return "korg";
		}

		return manufacturer;
	}

	private String stripLastCharacter(String manufacturer) {
		if (manufacturer.endsWith(".")) {
			manufacturer = manufacturer.substring(0, manufacturer.length() - 1);
		}
		return manufacturer;
	}
}
