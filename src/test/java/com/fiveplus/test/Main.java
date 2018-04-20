package com.fiveplus.test;

import com.fiveplus.dao.TestMapper;
import com.fiveplus.utils.DBTask;
import com.fiveplus.utils.DBTaskMgr;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 */
public class Main {
    public static void main(String[] args){
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        TestMapper testMapper = (TestMapper)context.getBean("testMapper");

        DBTask task = new DBTask("db1",testMapper,"selectGroup");
        DBTask task2 = new DBTask("db2", testMapper, "selectGroup");
        DBTask task3 = new DBTask("db3", testMapper, "selectGroup");
        DBTask task4 = new DBTask("db4", testMapper, "selectGroup");
        DBTask task5 = new DBTask("db5", testMapper, "selectGroup");
        DBTask task6 = new DBTask("db6", testMapper, "selectGroup");
        DBTask task7 = new DBTask("db7", testMapper, "selectGroup");
        DBTask task8 = new DBTask("db8", testMapper, "selectGroup");
        DBTask task9 = new DBTask("db9", testMapper, "selectGroup");
        DBTask task10 = new DBTask("db10", testMapper, "selectGroup");
        DBTask task11 = new DBTask("db11", testMapper, "selectGroup");

        System.out.println("开始查询5千万数据表：");
        DBTaskMgr.instance().execute(task);
        while(true){
            if(!task.isFinish()){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }
        System.out.println(task.getRetValue());
        System.out.println("查询5千万数据表结束: " + task.getTime() + "ms");

        List<DBTask> taskList = new ArrayList<DBTask>();
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
        taskList.add(task6);
        taskList.add(task7);
        taskList.add(task8);
        taskList.add(task9);
        taskList.add(task10);
        taskList.add(task11);
        System.out.println("开始查询10个5百万数据表：");
        for (DBTask t : taskList)
        {
            DBTaskMgr.instance().execute(t);
        }
        while(true){
            int success = 0;
            for(DBTask t:taskList){
                if(!t.isFinish()){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }else{
                    ++success;
                }
            }
            if(success == taskList.size()){
                break;
            }
        }
        long alltime = 0;
        for (DBTask t : taskList)
        {
            if(t.getTime() > alltime) alltime = t.getTime();
            System.out.println(t.getRetValue());
        }
        System.out.println("查询5千万数据表结束: " + alltime + "ms");

    }
}
