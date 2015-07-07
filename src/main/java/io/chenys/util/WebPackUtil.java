package io.chenys.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * maven project pack code
 * @author cys
 *
 */
public class WebPackUtil {
	
	public static String DEST_PATH = "F:/pack_temp/";
	public static String ZIP_FILE_NAME = "update.zip"; 
	
	public static String WEBAPP_DIR = "src/main/webapp/";
	public static String SRC_DIR = "target/classes/";
	
	//工程根目录：不同工程Maven工程只需修改这个配置文件
	public static String PROJECT_ROOT = "E:/Java/workspaceNew/sharding/";
	/**
	 * 需要打包文件列表：
	 * 样式：src/main/java/com/xxx.java 或者 src/main/webapp/WEB-INF/views/xxx.jsp
	 */
	public static String packfile = "packFileList.txt";
	
	public static List<String> fileList = new ArrayList<String>();
	
	public static void main(String[] args) {
		//获取所有待打包文件路径（包括内部类匿名文件）
		getFilePath();
		//清除文件
		deleteAllFiles(new File(DEST_PATH));
		//拷贝待打包文件
		for (String string : fileList) {
			String destFilePath = "";
			if (string.indexOf(WEBAPP_DIR)!=-1) {
				destFilePath = string.replace(PROJECT_ROOT + WEBAPP_DIR, DEST_PATH);
			}else {
				destFilePath = string.replace(PROJECT_ROOT + SRC_DIR, DEST_PATH);
			}
			copyFile(string, destFilePath);
		}
		//压缩zip
		File f = new File(DEST_PATH);
        ZipOutputStream out=null;
		try {
			out = new ZipOutputStream(new FileOutputStream(DEST_PATH+ZIP_FILE_NAME));
			zip(out, f, null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (null!=out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("zip done");
	}
	
	//读取packfile.txt获取所有打包文件路径
	public static void getFilePath(){
		
		List<String> list = readPackFile();
		
		Pattern srcPattern = Pattern.compile("^(src/main/java/)|(.java)$");
		Pattern webPattern = Pattern.compile("^(src/main/webapp/)");
		for (String string : list) {
			File file;
			Matcher matcherSrc = srcPattern.matcher(string);
			if (matcherSrc.find()) {
				String after = matcherSrc.replaceAll("");
				String fileName=PROJECT_ROOT+SRC_DIR+after+".class";
				file = new File(fileName);
				if (file.exists()) {
					fileList.add(fileName);
					getInnerClasses(file.getParentFile().listFiles(),file.getName().replaceAll("[.][^.]+$", ""));
				}
				continue;
			}
			
			Matcher matcherWeb = webPattern.matcher(string);
			if (matcherWeb.find()) {
				String after = matcherWeb.replaceAll("");
				String fileName=PROJECT_ROOT + WEBAPP_DIR + after;
				file = new File(fileName);
				if (file.exists()) {
					fileList.add(fileName);
				}
			}
			
		}
		
	}
	
	public static void getInnerClasses(File[] files,String OutterName) {
		Pattern pattern = Pattern.compile("^("+OutterName+"\\$)");
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());
            if (file.isFile()) {
            	if (matcher.find()) {
            		fileList.add(file.getAbsolutePath().replaceAll("\\\\", "/"));
				}
            }
		}
	}
	
	public static List<String> readPackFile() {
        List<String> filesList = new ArrayList<String>();
		InputStreamReader inputStreamReader = new InputStreamReader(WebPackUtil.class.getResourceAsStream("/"+packfile));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	filesList.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return filesList;
    }
	
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfile = new File(newPath);
			if (!newfile.exists()) {
				newfile.getParentFile().mkdirs();
			}
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}catch (Exception e) {
			System.out.println("复制单个文件操作出错"); 
			e.printStackTrace();
		}
	}
	
	//打包（遇到文件夹递归）
	public static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fc = f.listFiles();
            if (base != null)
                out.putNextEntry(new ZipEntry(base + "/"));
            base = base == null ? "" : base + "/";
            for (int i = 0; i < fc.length; i++) {
                zip(out, fc[i], base + fc[i].getName());
            }
        } else {
        	if (!f.getName().matches(".*\\.zip$")) {
        		System.out.println("zipping " + f.getAbsolutePath());
        		out.putNextEntry(new ZipEntry(base));
        		FileInputStream in = new FileInputStream(f);
        		int b;
        		while ((b = in.read()) != -1)
        			out.write(b);
        		in.close();
			}
        }
    }
	
	//删除文件夹自身及其下所有文件
	public static void deleteAllFiles(File path) {
	    if (!path.exists())
	        return;
	    if (path.isFile()) {
	        path.delete();  
	        return;
	    }
	    File[] files = path.listFiles();
	    for (int i = 0; i < files.length; i++) {
	    	deleteAllFiles(files[i]);
	    }
	    path.delete();
	}
}
