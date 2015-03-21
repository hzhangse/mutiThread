package com.train.sync;

public class ReadResult extends Thread { 
    Calculator c; 

    public ReadResult(Calculator c) { 
            this.c = c; 
    } 

    public void run() { 
            synchronized (c) { 
                    try { 
                            System.out.println(Thread.currentThread() + "�ȴ�������������"); 
                            c.wait(); 
                    } catch (InterruptedException e) { 
                            e.printStackTrace(); 
                    } 
                    System.out.println(Thread.currentThread() + "������Ϊ��" + c.total); 
            } 
    } 

    public static void main(String[] args) { 
            Calculator calculator = new Calculator(); 

            //���������̣߳��ֱ��ȡ������ 
            for (int i=0;i<3;i++)
            new ReadResult(calculator).start(); 
          
            //���������߳� 
            calculator.start(); 
    } 
}
