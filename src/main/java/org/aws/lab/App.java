package org.aws.lab;

public class App 
{
    public static void main( String[] args )
    {
        String flag = args.length>0 ? args[0] : "";

        switch(flag){
            case "":
                // System.out.println("no flag, nothing to do");
                new dynamodbLab().run();
                // new s3Lab().run();
                break;
            case "s3":
                new s3Lab().run();
                break;
            case "dynamodb":
                new dynamodbLab().run();
                break;
            default:
        }

    }

    
    
}
