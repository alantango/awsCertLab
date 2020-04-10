package org.aws.lab;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class s3Lab extends Base{

    private AmazonS3 s3Client;

    public void run(){
        s3Client = AmazonS3ClientBuilder.standard()
                                .withClientConfiguration(clientConfig)
                                .withCredentials(credProvider)
                                .withRegion(region)
                                .build();

        listBuckets();
        // createBuckets();

    }

    public void listBuckets(){
        List<Bucket> buckets = s3Client.listBuckets();
        pr("-- list of buckets:");
        buckets.forEach( b -> pr(b.getName()) );
    }

    public void uploadObjects(){
        File file = new File("c:\\temp\\employeelist.txt");
        String key = "employeeList";
        s3Client.putObject("hr-files-z", key, file);
        pr("file uploaded with key " + key);

    }

    public void createBuckets(){
        for(int i=0; i<2; i++){
            String bucketName = "z2note-bundles-" + i;
            Bucket bucket = s3Client.createBucket(bucketName);
            pr("Bucket created: " + bucket==null ? "null" : bucket.getName());
        }       
        

    }

}