package com.train.sync;

public class Calculator extends Thread { 
    int total; 

    public void run() { 
        synchronized (this) { 
            for (int i = 0; i < 10001; i++) { 
                total += i; 
            } 
            //����ɼ����ˣ������ڴ˶���������ϵȴ��ĵ����̣߳��ڱ������߳�A������ 
           //  
            //notifyAll();
            System.out.println();
        } 
        System.out.println();
    } 
}
