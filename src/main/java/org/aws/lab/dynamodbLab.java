package org.aws.lab;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class dynamodbLab extends Base {

    private AmazonDynamoDB ddb;

    public dynamodbLab(){
        super();
        ddb = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public void run() {
        // AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
        //                 .withCredentials(credProvider)
        //                 .withClientConfiguration(clientConfig)
        //                 .withRegion(region)
        //                 .build();

        // listTables();
        saveSecurities(null);
    }

    public void saveSecurities(String securities){
        try{
            String payload = null;
            if(securities==null){
                payload = this.loadContentInResourceFile("securities-2.json");
            }else{
                payload = securities;
            }
            // String securities = 
            JSONArray jsonArray = new JSONArray(payload);
            for(Object o : jsonArray){
                JSONObject jo = new JSONObject(o.toString());
                Map<String, Object> item = jo.toMap();

                uploadItem(item);
                
                pr("saved into dynamoDB CUSIP: " + jo.getString("CUSIP"));
            }
        }catch(Exception e){
            pr("ERROR reading resource file: " + e.getMessage());
        }
    }

    public void uploadItem(Map<String, Object> item){
        try{
            Map<String,AttributeValue> vals = new HashMap<String,AttributeValue>();
            item.forEach( (s, obj) -> {
                String v = obj.toString();
                pr("...attrib: " + s + "=" + v);

                if(s.equals("Price")){
                    vals.put(s, new AttributeValue().withN(v));
                }else if(s.contains("Date")){
                    LocalDate dt = LocalDate.parse(v, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    vals.put(s, new AttributeValue(dt.format(DateTimeFormatter.ISO_LOCAL_DATE)));
                }else{
                    vals.put(s, new AttributeValue(v));
                }
            });
            PutItemRequest req = new PutItemRequest("Securities",vals);
            ddb.putItem(req);
        }catch(Exception ex){
            pr("ERROR putting table item: " + ex.toString());
        }

    }

    public void listTables(){
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