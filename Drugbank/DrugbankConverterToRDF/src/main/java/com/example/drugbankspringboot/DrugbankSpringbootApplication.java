package com.example.drugbankspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.graphdb.Connector;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.Drugbank"})
public class DrugbankSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrugbankSpringbootApplication.class, args);
		Connector.initRepo();
	}
}
