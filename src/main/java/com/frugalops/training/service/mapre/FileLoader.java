package com.frugalops.training.service.mapre;


import com.google.common.collect.Iterators;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class FileLoader {
    @Autowired
    protected SparkSession sparkSession;
    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Dataset<Row> loadFromCSV(File file) throws IOException {
//        StructType schema = new StructType()
//                .add("date", "date")
//                .add("txcount", "int")
//                .add("generatedcoins", "long")
//                .add("activeaddresses", "int");
        Dataset<Row> df = sparkSession.read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", ",")
//                .csv(file.getAbsolutePath());
//                .option("mode", "DROPMALFORMED")
//                .schema(schema)
                .load(file.getAbsolutePath());
        return df;
    }

}
