package org.aws.lab;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class dynamodbLab extends Base {
    public void run() {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withCredentials(credProvider)
                .withClientConfiguration(clientConfig).withRegion(region).build();

        ListTablesResult resp = ddb.listTables();
        List<String> tableNames = resp.getTableNames();

        pr("list tables:");

        for (String table : tableNames) {
            DescribeTableResult res = ddb.describeTable(table);
            Long count = res.getTable().getItemCount();
            pr(table + "; items:" + (count != null ? count : "0"));
        }
    }
}