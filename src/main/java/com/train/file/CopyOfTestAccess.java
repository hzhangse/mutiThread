package com.train.file;

public class CopyOfTestAccess {

	public CopyOfTestAccess() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	    Thread_writeFile thf3=new Thread_writeFile();  
        Thread_ReadFile thf4=new Thread_ReadFile();  
        thf3.start();  
       // thf4.start();  

	}


}
