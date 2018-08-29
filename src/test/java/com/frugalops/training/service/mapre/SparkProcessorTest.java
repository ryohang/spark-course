package com.frugalops.training.service.mapre;


import com.frugalops.training.Application;
import com.frugalops.training.service.FileLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SparkProcessorTest {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private SparkProcessor sparkProcessor;
    @Autowired
    private SparkSession sparkSession;
    private URL btcCSV;

    @Before
    public void setUp(){
        btcCSV = Thread.currentThread().getContextClassLoader().getResource("data/btc.csv");
    }

    @Test
    public void aggregateBlocksTest() throws IOException {
        File csv = new File(btcCSV.getPath());
        Dataset<Row> df = fileLoader.loadFromCSV(csv);
        Double totalBlocks = sparkProcessor.aggregateBlocks(df.toJavaRDD());
        assertTrue(totalBlocks>0);
    }

    @Test
    public void mapByDateTest() throws IOException {
        File csv = new File(btcCSV.getPath());
        Dataset<Row> df = fileLoader.loadFromCSV(csv);
        Dataset<Row> dataset = sparkProcessor.mapByDate(df,sparkSession);
        assertTrue(df.count()>dataset.count());
    }
}
