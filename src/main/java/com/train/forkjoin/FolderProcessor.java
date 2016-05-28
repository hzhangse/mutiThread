
package com.train.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FolderProcessor extends RecursiveTask<List<String>> {
	
	private static final long	serialVersionUID	= 1L;
	
	private String	          path;
	private String	          extension;
	
	public FolderProcessor(String path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}
	
	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<String>();
		List<FolderProcessor> tasks = new ArrayList<FolderProcessor>();
		File file = new File(path);
		File content[] = file.listFiles();
		if (content != null) {
			for (int i = 0; i < content.length; i++) {
				if (content[i].isDirectory()) {
					FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
					// 异步方式提交任务
					task.fork();
					tasks.add(task);
				} else {
					if (checkFile(content[i].getName())) {
						list.add(content[i].getAbsolutePath());
					}
				}
			}
		}
		if (tasks.size() > 50) {
			System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
		}
		
		addResultsFromTasks(list, tasks);
		return list;
	}
	
	/** 
	 * that will add to the list of files 
	the results returned by the subtasks launched by this task. 
	 * @param list 
	 * @param tasks 
	 */
	private void addResultsFromTasks(List<String> list,
	                List<FolderProcessor> tasks) {
		for (FolderProcessor item : tasks) {
			list.addAll(item.join());
		}
	}
	
	/** 
	 * This method compares if the name of a file 
	passed as a parameter ends with the extension you are looking for 
	 * @param name 
	 * @return 
	 */
	private boolean checkFile(String name) {
		return name.endsWith(extension);
	}
	
	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
		FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
		
		pool.execute(system);
		pool.execute(apps);
		
		pool.shutdown();
		
		List<String> results = null;
		results = system.join();
		System.out.printf("System: %d files found.\n", results.size());
		
		results = apps.join();
		System.out.printf("Apps: %d files found.\n", results.size());
		
	}
}