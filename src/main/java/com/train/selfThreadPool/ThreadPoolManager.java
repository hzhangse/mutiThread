package com.train.selfThreadPool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



 abstract class Upload {
    protected String info;
    abstract boolean uploadPic();
    public String getInfo(){
        return info;
    }
}
class TaskUpload extends Upload {
    
    public TaskUpload(String info){
        this.info = info;
    }
    public String getInfo(){
        return info;
    }
    @Override
    public boolean uploadPic()  {
        // TODO Auto-generated method stub
        System.out.println(info+"sleep begin ....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(info+"sleep end ....");
        return false;
    }
}
public class ThreadPoolManager {
    private static ThreadPoolManager instance = null;
    private List<Upload> taskQueue = Collections.synchronizedList(new LinkedList<Upload>());//�������
    private WorkThread[] workQueue ;    //�����̣߳�����ִ��������̣߳�
    private static int worker_num = 5;    //�����߳�������Ĭ�Ϲ����߳�������5��
    private static int worker_count = 0;
    
    private ThreadPoolManager(){
        this(5);
    }
    private ThreadPoolManager(int num){
        worker_num = num;
        workQueue = new WorkThread[worker_num];
        for(int i=0;i<worker_num;i++){
            workQueue[i] = new WorkThread(i);
        }
    }
    
    public static synchronized ThreadPoolManager getInstance(){
        if(instance==null)
            instance = new ThreadPoolManager();
        return instance;
    }
    
    public void addTask(Upload task){
        //��������еĲ���Ҫ����
        synchronized (taskQueue) {
            if(task!=null){
                taskQueue.add(task);
                taskQueue.notifyAll();
                System.out.println("task id "+task.getInfo() + " submit!");
            }
                
        }
    }
    
    public void BatchAddTask(Upload[] tasks){
        //��������е��޸Ĳ���Ҫ����
        synchronized (taskQueue) {
            for(Upload e:tasks){
                if(e!=null){
                    taskQueue.add(e);
                    taskQueue.notifyAll();
                    System.out.println("task id "+e.getInfo() + " submit!");
                }
            }        
        }
    }
    
    public void destory(){
        System.out.println("pool begins to destory ...");
        for(int i = 0;i<worker_num;i++){
            workQueue[i].stopThread();
            workQueue[i] = null;
        }
        //��������еĲ���Ҫ����
        synchronized (taskQueue) {
            taskQueue.clear();
        }
        
        System.out.println("pool ends to destory ...");
    }
    
    private class WorkThread extends Thread{
        private int taksId ;
        private boolean isRuning = true;
        private boolean isWaiting = false;
        
        
         
        public WorkThread(int taskId){
            this.taksId= taskId;
            this.start();
        }
        
        public boolean isWaiting(){
            return isWaiting;
        }
        // ������������ʱ������������ֹ�̣߳���Ҫ�ȴ��������֮���⵽isRuningΪfalse��ʱ���˳�run()����
        public void stopThread(){
            isRuning = false;
        }
        
        @Override
        public void run() {
            while(isRuning){
                Upload temp = null;
                //��������еĲ���Ҫ����
                synchronized (taskQueue) {
                    //�������Ϊ�գ��ȴ��µ��������
                    while(isRuning&&taskQueue.isEmpty()){
                        try {
                            taskQueue.wait(20);
                        } catch (InterruptedException e) {
                            System.out.println("InterruptedException occre...");
                            e.printStackTrace();
                        }
                    }
                    if(isRuning)
                        temp = taskQueue.remove(0);
                }
                //���ȴ����������ʱ����ֹ�߳�(����stopThread����)��� temp �� null
                if(temp!=null){
                    System.out.println("task info: "+temp.getInfo()+ " is begining");
                    isWaiting = false;
                    temp.uploadPic();
                    isWaiting = true;
                    System.out.println("task info: "+temp.getInfo()+ " is finished");
                }    
            }
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Upload[] tasks = createBatchTask(7);
        ThreadPoolManager pool = ThreadPoolManager.getInstance();
        pool.BatchAddTask(tasks);
        pool.destory();
    }
    private static Upload[] createBatchTask(int n){
        Upload[] tasks = new TaskUpload[n];
        for(int i = 0;i<n ;i++ ){
            tasks[i] = new TaskUpload("task id is "+ i);
        }
        return tasks;
    }

}