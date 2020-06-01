package org.aws.lab;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class s3Lab extends Base {

    private AmazonS3 s3Client;

    public s3Lab(){
        super();
        s3Client = AmazonS3ClientBuilder.defaultClient();
            // s3Client = AmazonS3ClientBuilder.standard()
            // .withClientConfiguration(clientConfig)
            // .withCredentials(credProvider)
            // .withRegion(region)
            // .build();
    }

    public void run() {
        try{
            // listBuckets();

            // createBuckets();

            // uploadObjects("securities-2.json");

            String content = extractS3ObjectData("bk-securities","securities-1.json");

            pr("file content:\n" + content);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void listBuckets() {
        List<Bucket> buckets = s3Client.listBuckets();
        pr("-- list of buckets:");
        buckets.forEach(b -> pr(b.getName()));
    }

    public void uploadObjects(String resourceFile) {
        File f = this.getResourceFullPath(resourceFile);
        s3Client.putObject("bk-securities", f.getName(), f);
        pr("file uploaded with key " + f.getName());
    }

    public void createBuckets() {
        for (int i = 0; i < 2; i++) {
            String bucketName = "z2note-bundles-" + i;
            Bucket bucket = s3Client.createBucket(bucketName);
            pr("Bucket created: " + bucket == null ? "null" : bucket.getName());
        }
    }

    public String extractS3ObjectData(String bucketName, String key) throws IOException {
        
        pr("....extracting " + key + " in bucket " + bucketName);
        
        InputStream s3Input = null;
        String res = null, line=null;
        BufferedReader bf = null;
        try{
            S3Object s3Obj = s3Client.getObject(new GetObjectRequest(bucketName, key));
            pr("...s3Obj returned: " + (s3Obj!=null));

            StringBuffer buf = new StringBuffer();
            s3Input = s3Obj.getObjectContent();
            bf = new BufferedReader(new InputStreamReader(s3Input));
            while( (line=bf.readLine()) != null){
                buf.append(line);
            }
            res = buf.toString();

        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            if(bf!=null) bf.close();
            if(s3Input!=null) s3Input.close();
        }
        
        return res;
    }

}