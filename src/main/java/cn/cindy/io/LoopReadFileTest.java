package cn.cindy.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LoopReadFileTest {
	public static final File folder = new File(
			"F:/Jmeter/SDK_Risk_Jmeter_Repo/Dev/src/test/jmeter");
	
	public static List<String> fileNamesList = new ArrayList<String>();

	public static void main(String[] args) {
		listFilesForFolder(folder);
		BufferedReader br = null;
		try {
			for (int i = 0; i < fileNamesList.size(); i++) {
				br = new BufferedReader(new FileReader(fileNamesList.get(i)));
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历文件夹,将所有的文件存入fileNamesList变量中
	 * @param folder 文件夹
	 */
	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				fileNamesList.add(fileEntry.getAbsoluteFile().toString());
			}
		}
	}

}
