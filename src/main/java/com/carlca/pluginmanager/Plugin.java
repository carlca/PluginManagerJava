package com.carlca.pluginmanager;

import java.util.ArrayList;
import java.util.List;

public class Plugin {

	private String manufacturer;
	private String ident;
	private String plugin;
	private final ArrayList<Plugin> children;

	public Plugin(String manufacturer, String ident, String plugin) {
		this.manufacturer = manufacturer;
		this.ident = ident;
		this.plugin = plugin;
		children = new ArrayList<Plugin>();
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getIdent() {
		return ident;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public void addChild(Plugin child) {
		children.add(child);
	}

	public void addChildren(List<Plugin> children) {
		this.children.addAll(children);
	}

	public ArrayList<Plugin> getChildren() {
		return children;
	}

	public String toString() {
		return getClass().getSimpleName() + '(' + plugin + ')';
	}

}
