package com.frugalops.training.service.mapre;

import javafx.util.Pair;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SparkProcessor implements Serializable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public Dataset<Row> mapByDate(Dataset<Row> data, SparkSession sc) {
        JavaRDD<Row> inputRdd = data.toJavaRDD();
        JavaPairRDD<String, Double> pairMap = inputRdd.mapToPair(raw -> {
            LocalDate date = LocalDate.parse(raw.getAs("date"));
            date = date.withDayOfMonth(01);
            Double blockSize = Double.valueOf((String)raw.getAs("blockSize"));
            return new Tuple2<>(date.toString(), blockSize);
        });
        JavaPairRDD<String, Double> result = reduceByKey(pairMap);
        StructType schema = DataTypes.createStructType(
                new StructField[] {
                        DataTypes.createStructField("date", DataTypes.StringType, false),
                        DataTypes.createStructField("blockSize", DataTypes.DoubleType, false),
        });
        JavaRDD<Row> rowRDD = result.map(tuple -> RowFactory.create(tuple._1(),tuple._2()));
        Dataset<Row> dataset = sc.createDataFrame(rowRDD,schema).toDF();
        return dataset;
    }


    public Double aggregateBlocks(JavaRDD<Row> data){
        Double total = data.aggregate(0.0,
                (partitionD1, row1) -> partitionD1 + Double.valueOf((String) row1.getAs("blockCount"))
                , (pD1, pD2) -> pD1 + pD2);
        return total;
    }


    private static <T> JavaPairRDD<T, Double> reduceByKey(JavaPairRDD<T, Double> pairMap) {
        return pairMap.reduceByKey((v1, v2) -> {
                    return v1+v2;
               }
        );
    }
}
