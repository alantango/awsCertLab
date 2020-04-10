package org.aws.lab;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class s3Lab extends Base{

    public void run(){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                .withClientConfiguration(clientConfig)
                                .withCredentials(credProvider)
                                .withRegion(region)
                                .build();

        // s3Client.createBucket("hr-files-z");

        List<Bucket> buckets = s3Client.listBuckets();
        pr("-- list of buckets:");
        buckets.forEach( b -> pr(b.getName()) );
        
        File file = new File("c:\\temp\\employeelist.txt");
        String key = "employeeList";
        s3Client.putObject("hr-files-z", key, file);

        pr("file uploaded with key " + key);

    }

}