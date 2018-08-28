package com.frugalops.training.config;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {
    //    @Value("${spark.master.url}")
//    private String sparkMasterUrl;
    @Bean
    public SparkConf sparkConf() {
        SparkConf conf = new SparkConf();
        conf.setAppName("frugalops")
                .setMaster("local[*]")
//                .setMaster("spark://hanqings-MacBook-Pro.local:7077")
//                .setMaster("ec2-54-84-89-66.compute-1.amazonaws.com")
                .set("spark.sql.shuffle.partitions","100");
        return conf;
    }
}
