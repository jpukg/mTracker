package com.ebay.build.profiler.mdda.util;


public class ParaDownload {
	
//    public static void threaddownload(List<DArtifact> artifacts,boolean debug) {
//        
//  
//       int size = artifacts.size();
//       int downloadcount = 0;
//       while(downloadcount < size){		
//    	    	     
//    	   if(MyDownThread2.activeCount() < 50){
//       			
//       				MyDownThread2 md = new MyDownThread2(artifacts.get(downloadcount),debug);
//       				
//       				downloadcount++;
//       				
//       				md.start();
//       				 
//       		}
//       }
//       while(MyDownThread2.activeCount() != 3){
//    	   
//       }
//    }
//
//    
//    static class MyDownThread2 extends Thread{
// 
//
//       private DArtifact artifact;
//       private boolean debug;
//       
//       public MyDownThread2(DArtifact a, boolean debug) {
//    	   this.artifact = a;
//    	   this.debug = debug;
//       }
//     
//       @Override
//		public void run() {
//
//    	   HttpClient client = new HttpClient();  
//	       
//    	   String url = artifact.getQuick_url();
//    	   
//			if (debug) {
//				System.out.println("[MDDA] Downloading : " + url);
//			}
//	       GetMethod httpGet = new GetMethod(url);  
//	       
//	        try {  
//	            client.executeMethod(httpGet);  
//	              
//	            InputStream in = httpGet.getResponseBodyAsStream();  
//	            
//	            File localfile = artifact.generateFilePath();
//	            
//	            FileOutputStream out = new FileOutputStream(localfile);  
//	             
//	            byte[] b = new byte[1024];  
//	            
//	            int len = 0;  
//	            
//	            while((len = in.read(b))!= -1){  
//	                out.write(b,0,len);  
//	            }  
//	            
//	            in.close();  
//	            
//	            out.close(); 
//	            
//	            long size = localfile.length();
//	         
//	            if(!artifact.artifactOK(size)){
//	            	localfile.delete();
//	            	System.out.println("[MDDA] Downloading failed due to size not match,  : " + url);
//	            } else if (debug) {
//					System.out.println("[MDDA] Downloaded : " + url);
//				}
//	            
//	            if (localfile.exists()) {
//	            	File sha1File = new File(localfile.getParent(), localfile.getName() + ".sha1");
//	            	if (!sha1File.exists()) {
//	            		String jarMD5 = MD5Generator.createMessageDisgestChecksum(localfile, "SHA1");
//	            		FileUtils.writeToFile(sha1File, jarMD5);
//	            	}
//	            	if (debug && sha1File.exists()) {
//	            		System.out.println("[MDDA] SHA1 generated for artifacts: " + localfile);
//	            	}
//	            }
//	        }catch (Exception e){  
//	            e.printStackTrace();  
//	        }finally{  
//	            httpGet.releaseConnection();  
//	        } 
//	        
//		}
//    }
}