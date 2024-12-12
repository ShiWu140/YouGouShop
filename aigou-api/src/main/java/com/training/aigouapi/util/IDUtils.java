package com.training.aigouapi.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 生成唯一ID
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/12 14:22
 */
public class IDUtils {
    private final long epoch = 1288834974657L; // 自定义的起始时间戳
    private final long machineIdBits = 5L; // 机器ID位数
    private final long sequenceBits = 12L; // 序列号位数

    private final long maxMachineId = -1L ^ (-1L << machineIdBits); // 最大机器ID
    private final long sequenceMask = -1L ^ (-1L << sequenceBits); // 最大序列号

    private long machineId; // 机器ID
    private long sequence = 0L; // 当前序列号
    private long lastTimestamp = -1L; // 上次时间戳

    public IDUtils(long machineId) {
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + maxMachineId);
        }
        this.machineId = machineId;
    }

    public synchronized long get() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - epoch) << (machineIdBits + sequenceBits))
                | (machineId << sequenceBits)
                | sequence;
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

//    public static void main(String[] args) {
//        // 创建 IDUtils 实例
//        IDUtils idUtils = new IDUtils(1L);
//
//        // 测试生成唯一ID的方法
//        Set<Long> uniqueIds = new HashSet<>();
//        int duplicateCount = 0;
//
//        for (int i = 0; i < 10000; i++) {
//            long id = idUtils.get();
//            System.out.println("Generated ID: " + id);
//            if (!uniqueIds.add(id)) {
//                duplicateCount++;
//            }
//        }
//
//        System.out.println("Total IDs generated: 10000");
//        System.out.println("Number of duplicate IDs: " + duplicateCount);
//    }
}
