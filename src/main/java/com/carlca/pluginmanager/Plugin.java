package com.carlca.pluginmanager;

import java.util.ArrayList;

public class Plugin {

	private String manufacturer;
	private String ident;
	private String plugin;
	private final ArrayList<Object> children;

	public Plugin(String manufacturer, String ident, String plugin) {
		this.manufacturer = manufacturer;
		this.ident = ident;
		this.plugin = plugin;
		children = new ArrayList<Object>();
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

	public void addChildren(Object children) {
		this.children.add(children);
	}

	public ArrayList<Object> getChildren() {
		return children;
	}

	public String toString() {
		return getClass().getSimpleName() + '(' + plugin + ')';
	}

}
