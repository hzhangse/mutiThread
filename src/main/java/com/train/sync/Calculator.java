package com.train.sync;

public class Calculator extends Thread { 
    int total; 

    public void run() { 
        synchronized (this) { 
            for (int i = 0; i < 10001; i++) { 
                total += i; 
            } 
            //（完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒 
           //  
            //notifyAll();
            System.out.println();
        } 
        System.out.println();
    } 
}
