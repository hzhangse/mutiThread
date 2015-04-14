package com.train.file;

public class TestAccess {

	public TestAccess() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	    Thread_writeFile thf3=new Thread_writeFile();  
       // Thread_ReadFile thf4=new Thread_ReadFile();  
        thf3.start();  
       // thf4.start();  

	}


}
