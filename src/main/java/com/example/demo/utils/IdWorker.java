package com.example.demo.utils;

/*
 *
 * http://blog.csdn.net/antony9118/article/details/52766848
 * */
public class IdWorker {

    private volatile static IdWorker instance; //声明成 volatile

    public static IdWorker getSingleton(long dataCenterId, long workerId) {
        if (instance == null) {
            synchronized (IdWorker.class) {
                if (instance == null) {
                    instance = new IdWorker(dataCenterId, workerId);
                }
            }
        }
        return instance;
    }

    //开始该类生成ID的时间截，1498959703000l (2017-07-02 09:41:43) 这一时刻到当前时间所经过的毫秒数，占 41 位（还有一位是符号位，永远为 0）。
    private final long startTime = 1498959703000L;

    //机器id所占的位数
    private long workerIdBits = 5L;

    //数据标识id所占的位数
    private long dataCenterIdBits = 5L;

    //支持的最大机器id，结果是31,这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数（不信的话可以自己算一下，记住，计算机中存储一个数都是存储的补码，结果是负数要从补码得到原码）
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    //支持的最大数据标识id
    private long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    //序列在id中占的位数
    private long sequenceBits = 12L;

    //机器id向左移12位
    private long workerIdLeftShift = sequenceBits;

    //数据标识id向左移17位
    private long dataCenterIdLeftShift = workerIdBits + workerIdLeftShift;

    //时间截向左移5+5+12=22位
    private long timestampLeftShift = dataCenterIdBits + dataCenterIdLeftShift;

    //生成序列的掩码，这里为1111 1111 1111
    private long sequenceMask = -1 ^ (-1 << sequenceBits);

    private long workerId;

    private long dataCenterId;

    //同一个时间截内生成的序列数，初始值是0，从0开始
    private long sequence = 0L;

    //上次生成id的时间截
    private long lastTimestamp = -1L;

    private IdWorker(long dataCenterId, long workerId) {
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(
                String.format("workerId[%d] is less than 0 or greater than maxWorkerId[%d].", workerId, maxWorkerId));
        }
        if (dataCenterId < 0 || dataCenterId > maxDataCenterId) {
            throw new IllegalArgumentException(
                String.format("dataCenterId[%d] is less than 0 or greater than maxDataCenterId[%d].", dataCenterId, maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    //生成id
    public synchronized long nextId() {
        long timestamp = timestampGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则自增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //生成下一个毫秒级的序列
                timestamp = tilNextMillis();
                //序列从0开始
                sequence = 0L;
            }
        } else {
            //如果发现是下一个时间单位，则自增序列回0，重新自增
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        //看本文第二部分的结构图，移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampLeftShift)
            | (dataCenterId << dataCenterIdLeftShift)
            | (workerId << workerIdLeftShift)
            | sequence;
    }


    protected long tilNextMillis() {
        long timestamp = timestampGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timestampGen();
        }
        return timestamp;
    }

    protected long timestampGen() {
        return System.currentTimeMillis();
    }

    public long decodeTimestamp(long id) {
        return (id >> timestampLeftShift) + startTime;
    }

    public long decodeDataCenterId(long id) {
        return (id >> workerIdLeftShift) & ((1 << dataCenterIdBits) - 1);
    }

    public long decodeWorkerId(long id) {
        return (id >> sequenceBits) & ((1 << workerIdBits) - 1);
    }

    public long decodeSequence(long id) {
        return id & ((1 << sequenceBits) - 1);
    }

    public long encodeId(long tempTimestamp, long tempSequence) {
        //看本文第二部分的结构图，移位并通过或运算拼到一起组成64位的ID
        return ((tempTimestamp - startTime) << timestampLeftShift)
            | (dataCenterId << dataCenterIdLeftShift)
            | (workerId << workerIdLeftShift)
            | tempSequence;
    }

}
