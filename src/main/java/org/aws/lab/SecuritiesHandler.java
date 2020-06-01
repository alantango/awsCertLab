package org.aws.lab;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class SecuritiesHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        
        LambdaLogger logger = context.getLogger();
        
        String securitiesBag = null;

        try{
            S3EventNotificationRecord record = s3event.getRecords().get(0);
            
            String bucketName = record.getS3().getBucket().getName();
            String objKey = record.getS3().getObject().getUrlDecodedKey();
            Long objSize = record.getS3().getObject().getSizeAsLong();

            logger.log("...found bucket: " + bucketName + ", file: " + objKey + " (" + objSize + ")");
            
            s3Lab lab = new s3Lab();
            securitiesBag = lab.extractS3ObjectData(bucketName,objKey);
            logger.log("..securities file from S3: " + objKey + "\n" + securitiesBag);

            dynamodbLab dLab = new dynamodbLab();
            dLab.saveSecurities(securitiesBag);

        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

        return "OK";
    }
    
}