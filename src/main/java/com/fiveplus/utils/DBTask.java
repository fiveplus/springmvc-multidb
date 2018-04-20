package com.fiveplus.utils;

import java.lang.reflect.Method;

/**
 * 数据库访问任务定义。将每一个对数据库访问的请求包装为一个任务对象，放到任务管理中，
 * 然后等待任务执行完成，取出执行结果。
 */
public class DBTask implements Runnable{
    // 操作数据库标识，用于指定访问的数据库。与spring配置文件中的数据动态数据源定义一致。
    private final String dbKey;

    // mybatis数据库访问对象
    private final Object dbAccessObject;

    // mysbatis数据库访问方法名称，用于反射调用
    private final String methodName;

    // 存储可变参数的值
    private final Object[] paraArray;

    // 存储可变参数类型
    @SuppressWarnings("rawtypes")
    private final Class[] paraClassArray;

    // 数据库操作结果。查询操作返回查询结果; 插入、删除、修改操作返回null。
    private Object operateResult;

    // 操作数据库抛出的异常信息
    private Exception exception;

    // 标识任务是否已经执行
    private boolean finish;

    private long time;

    /**
     * 构造函数
     * @param dbKey 数据库标识
     * @param dbAccessObject 数据库访问对象
     * @param methodName 数据库访问方法名称
     * @param paraArray 参数列表
     */
    public DBTask(final String dbKey, final Object dbAccessObject, final String methodName,
                  final Object... paraArray)
    {
        this.dbKey = dbKey;
        this.dbAccessObject = dbAccessObject;
        this.methodName = methodName;
        this.paraArray = paraArray;
        finish = false;
        exception = null;

        paraClassArray = new Class[paraArray.length];
        for (int index = 0; index < paraArray.length; ++index)
        {
            paraClassArray[index] = paraArray[index].getClass();
        }

        operateResult = null;
    }

    /**
     *
     * 任务执行函数
     *
     */
    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();
        try
        {
            DBIndetifer.setDBKey(dbKey);
            Method method = dbAccessObject.getClass().getMethod(methodName, paraClassArray);

            // 查询操作返回查询结果; 插入、删除、修改操作返回null
            operateResult = method.invoke(dbAccessObject, paraArray);
        }
        catch (Exception e)
        {
            exception = e;
            e.printStackTrace();
        }

        finish = true;
        time = System.currentTimeMillis() - startTime;
    }

    /**
     *
     * 返回操作结果。查询操作返回查询结果; 插入、删除、修改操作返回null
     *
     * @return 操作结果
     */
    public Object getRetValue()
    {
        return operateResult;
    }

    /**
     * 抛出数据库操作异常
     *
     * @return 异常
     */
    public Exception getException()
    {
        return exception;
    }

    /**
     *
     * 返回任务是否已执行
     *
     * @return 标记
     */
    public boolean isFinish()
    {
        return finish;
    }

    public long getTime(){
        return time;
    }

}
