package com.codemayur.transactions_routine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.codemayur.transactions_routine")
public class TransactionsRoutineApp {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsRoutineApp.class, args);
    }

}
