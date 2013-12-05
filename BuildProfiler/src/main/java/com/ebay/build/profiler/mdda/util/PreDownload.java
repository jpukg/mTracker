package com.ebay.build.profiler.mdda.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ebay.build.profiler.mdda.bean.DArtifact;
import com.ebay.build.profiler.mdda.bean.DArtifacts;
import com.ebay.build.profiler.mdda.http.DownloadExecutor;

public class PreDownload {

	public static void start(File file, File localrepo,boolean debug) {
		long startTime = System.currentTimeMillis();
		List<DArtifact> dalist;
		
		if (file.exists()) {
			dalist = getArtifacts(file);
		} else {
			System.out.println("[INFO] " + file + " is not exists, skip pre downloading.");
			return;
		}
		
		System.out.println("[INFO] MDDA Loaded " + dalist.size() + " artifacts from cache file.");

		if (!dalist.isEmpty()) {
			
			Set<File> dirs = getFolderList(dalist, localrepo);
			makeDirectory(dirs);
			
			List<DArtifact> artifacts2Download = filterExistingArtifact(dalist);
			
			if (debug) {
				System.out.println("[DEBUG] MDDA made directories: " + dirs);
				System.out.println("[DEBUG] MDDA " + artifacts2Download.size() + " artifacts to download.");				
			}
			
			if (artifacts2Download.size() > 0) {
				if (debug) {
					System.out.println("[DEBUG] MDDA start downloading " + artifacts2Download.size() + " artifacts...");
				}
				
				DownloadExecutor de = new DownloadExecutor(artifacts2Download, debug);
				de.run();
				
//				MultithreadArtifactsDownloader downloader = new MultithreadArtifactsDownloader(artifacts2Download, debug);
//				downloader.run();
				
				//ParaDownload.threaddownload(artifacts2Download, debug);
			}
		}
		
		System.out.println("[MDDA] Total time for pre downloading " + (System.currentTimeMillis() - startTime));
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

	private static List<DArtifact> getArtifacts(File file) {
		DArtifacts das = XMLConnector.unmarshal(file);
		return das.getDArtifactList();
	}


	private static Set<File> getFolderList(List<DArtifact> dalist, File localrepo) {
		Set<File> folderlist = new HashSet<File>();
		
		for (DArtifact artifact : dalist) {
			artifact.setLocalRepo(localrepo.toString());
			folderlist.add(artifact.generateFolderPath());
		}

		return folderlist;
	}
	

	// TODO use ANT mkdir util method.
	private static void makeDirectory(Set<File> folderlist) {
		for (File folder : folderlist) {
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
	}
	
	public static void main(String[] args) {
		PreDownload.start(new File("E:/var/lib/jenkins/raptor.build.tracking/mdda/claw-1fe8d8e598f0ad4c673ca1246c42d96c/dcl.xml"), 
				new File("E:/var/lib/jenkins/raptor.build.tracking/mdda/claw-1fe8d8e598f0ad4c673ca1246c42d96c/repo"), true);
	}
}
