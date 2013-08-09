package com.ebay.build.profiler.mdda.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ebay.build.profiler.mdda.bean.DArtifact;
import com.ebay.build.profiler.mdda.bean.DArtifacts;

public class PreDownload {

	public static void start(File file, File localrepo){
		
		List<DArtifact> dalist = getArtifacts(file);
				
		makeDirectory(getFolderList(dalist, localrepo));
	
		ParaDownload.threaddownload(filterExistingArtifact(dalist));
	}

	private static List<DArtifact> filterExistingArtifact(List<DArtifact> dalist) {
		List<DArtifact> results = new ArrayList<DArtifact>();
		for (DArtifact artifact : dalist) {
			if (!artifact.generateFilePath().exists()) {
				results.add(artifact);
			}
		}
		return results;
	}

	public static List<DArtifact> getArtifacts(File file) {

		DArtifacts das = XMLConnector.unmarshal(file);

		List<DArtifact> dalist = das.getDArtifactList();
		
		return dalist;
	}
	


	public static Set<File> getFolderList(List<DArtifact> dalist, File localrepo) {
		Set<File> folderlist = new HashSet<File>();
		
		for (DArtifact artifact : dalist) {
			artifact.setLocalRepo(localrepo.toString());
			folderlist.add(artifact.generateFolderPath());
		}

		return folderlist;
	}
	

	// TODO use ANT mkdir util method.
	public static void makeDirectory(Set<File> folderlist) {
		for (File folder : folderlist) {
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
	}
}
