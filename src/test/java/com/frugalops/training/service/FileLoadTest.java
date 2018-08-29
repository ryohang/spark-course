package com.frugalops.training.service;

import com.frugalops.training.Application;
import com.frugalops.training.service.mapre.FileLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class FileLoadTest {
    @Autowired
    private FileLoader fileLoader;


    @Test
    public void loadFromCSVTest() throws IOException {
        File csv = new File("/Users/ryo/Downloads/btc.csv");
        Dataset<Row> df = fileLoader.loadFromCSV(csv);
        assertTrue(df.count()>100);
    }
}
