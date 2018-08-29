package com.frugalops.training.service;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileLoader {
    @Autowired
    protected SparkSession sparkSession;
    @Value("${processor.input}")
    private String inputFilePath;

    public Dataset<Row> loadFile() {
        return loadFromCSV(new File(inputFilePath));
    }
    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Dataset<Row> loadFromCSV(File file) {
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
