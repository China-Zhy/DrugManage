package com.nxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    // 启用事务
public class DrugManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugManageApplication.class, args);
    }

}