package com.carlca.pluginmanager;

import java.io.File;

public class PluginManagerFileCounter {
	
	public static int countFiles(File folder) {
		int count = 0;
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					count++;
				} else if (file.isDirectory() && file.getName() != "Contents") {
					count += countFiles(file); // Recursively count files in subdirectories
				}
			}
		}
		return count;
	}
}