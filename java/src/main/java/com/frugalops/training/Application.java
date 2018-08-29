package com.frugalops.training;

import com.frugalops.training.service.FileLoader;
import com.frugalops.training.service.mapre.SparkProcessor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Dataset<Row> dataset = context.getBean(FileLoader.class).loadFile();
        Double blockSizeCount  = context.getBean(SparkProcessor.class).aggregateBlocks(dataset.toJavaRDD());
        Logger logger = LoggerFactory.getLogger(SparkProcessor.class);
        logger.info("total generate block size: "+blockSizeCount);
        Dataset<Row> reduced = context.getBean(SparkProcessor.class).mapByDate(dataset,context.getBean(SparkSession.class));
        reduced.show();
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}