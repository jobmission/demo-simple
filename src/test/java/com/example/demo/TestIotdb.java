package com.example.demo;

import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.session.Session;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.file.metadata.enums.CompressionType;
import org.apache.tsfile.file.metadata.enums.TSEncoding;
import org.apache.tsfile.read.common.Field;
import org.apache.tsfile.read.common.RowRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestIotdb {
    private static final String host = "127.0.0.1";
    private static final int port = 6667;
    private static final String username = "root";
    private static final String password = "root";

    @Disabled
    @Test
    public void testIoT() throws Exception {
        Session session = new Session(host, port, username, password);
        session.open();
        String storageGroup = "root.g1";
        try {
//            session.setStorageGroup(storageGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String device = storageGroup + ".f1.d1";

        List<String> measurements = new ArrayList<>();
        measurements.add("ua");
        measurements.add("temperature");

        measurements.forEach(measurement -> {
            try {
                String measurementPoint = device + "." + measurement;
                boolean flag = session.checkTimeseriesExists(measurementPoint);
                if (!flag) {
                    session.createTimeseries(measurementPoint, TSDataType.INT32, TSEncoding.RLE, CompressionType.SNAPPY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        for (int i = 100; i < 102; i++) {
            List<Object> values = new ArrayList<>();
            List<TSDataType> types = new ArrayList<>();
            values.add(i);
            types.add(TSDataType.INT32);
            values.add(100 + i);
            types.add(TSDataType.INT32);
            long time = System.currentTimeMillis();
            session.insertRecord(device, time, measurements, types, values);
        }

        SessionDataSet dataSet = session.executeQueryStatement("select * from root.g1.f1.d1");
        List<String> list = dataSet.getColumnNames();
        for (String s : list) {
            System.out.printf("%-35s", s);
        }
        System.out.println();
        dataSet.setFetchSize(1024);
        while (dataSet.hasNext()) {
            RowRecord record = dataSet.next();
            System.out.printf("%-35s", record.getTimestamp());
            for (Field field : record.getFields()) {
                System.out.printf("%-35s", field.getIntV());
            }
            System.out.println();
        }
        dataSet.closeOperationHandle();
        session.close();
        Thread.sleep(2000);
    }
}
