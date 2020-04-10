package org.aws.lab;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class Base {

    protected ClientConfiguration clientConfig;
    protected AWSCredentialsProvider credProvider;
    protected String region;

    public Base(){
        clientConfig = new ClientConfiguration();
        clientConfig.setProxyHost("p1proxy.frb.org");
        clientConfig.setProxyPort(8080);

        credProvider = new ProfileCredentialsProvider("me");

        region = "us-east-2";

    }

    protected void pr(String s){
        System.out.println(s);
    }

}