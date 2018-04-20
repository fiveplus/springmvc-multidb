package com.fiveplus.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 数据库访问任务管理。将数据库访问任务放到线程池中执行。
 */
public class DBTaskMgr {
    private static class DBTaskMgrInstance{
        public static final DBTaskMgr instance = new DBTaskMgr();
    }

    public static DBTaskMgr instance(){
        return DBTaskMgrInstance.instance;
    }

    private ThreadPoolExecutor pool;

    public DBTaskMgr(){
        pool = new ThreadPoolExecutor(10,50,60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10000),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void execute(Runnable task){
        pool.execute(task);
    }

}
