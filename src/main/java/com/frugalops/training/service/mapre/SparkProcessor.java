package com.frugalops.training.service.mapre;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SparkProcessor implements Serializable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public Double aggregateBlocks(JavaRDD<Row> data){
        Double total = data.aggregate(0.0,
                (partitionD1, row1) -> partitionD1 + Double.valueOf((String) row1.getAs("blockCount"))
                , (pD1, pD2) -> pD1 + pD2);
        return total;
    }
}
