package com.train.sync;

public class ThreadA { 
    public static void main(String[] args) { 
        Calculator b = new Calculator(); 
        //���������߳� 
        b.start(); 
        //�߳�Aӵ��b�����ϵ������߳�Ϊ�˵���wait()��notify()���������̱߳������Ǹ���������ӵ���� 
        synchronized (b) { 
            try { 
                System.out.println("�ȴ�����b��ɼ��㡣����"); 
                //��ǰ�߳�A�ȴ� 
               b.wait(); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
            System.out.println("b���������ܺ��ǣ�" + b.total); 
        } 
       
    } 
}
