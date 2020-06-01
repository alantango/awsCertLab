package org.aws.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Base {

    protected ClientConfiguration clientConfig;
    protected AWSCredentialsProvider credProvider;
    protected String region;
    protected LambdaLogger llog;

    // public Base() {
    //     clientConfig = new ClientConfiguration();

    //     clientConfig.setProxyHost("p1proxy.frb.org");
    //     clientConfig.setProxyPort(8080);
    //     credProvider = new ProfileCredentialsProvider("me");

    //     region = "us-east-2";

    // }

    protected void pr(String s) {
        System.out.println(s);
    }

    public void setLambdaLogger(LambdaLogger logger){
        this.llog = logger;
    }

    protected File getResourceFullPath(String relativePath){
        URL url = getClass().getClassLoader().getResource(relativePath);
        File file = new File(url.getFile());
        return file;

    }

    protected String loadContentInResourceFile(String path) throws IOException {
        InputStream instrm = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(instrm));
        String line=null;
        StringBuffer buf = new StringBuffer();
        while( (line = br.readLine()) != null){
            buf.append(line);
        }
        br.close();
        return buf.toString();
    }

}