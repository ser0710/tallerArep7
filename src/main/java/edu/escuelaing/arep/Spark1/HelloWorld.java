package edu.escuelaing.arep.Spark1;

import edu.escuelaing.arep.UrlReader;

import static spark.Spark.*;
public class HelloWorld {
    public static void main(String[] args) {
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStore(), getKey(), null, null);
        port(getPort());
        get("/local", (req, res) -> "Spark1");
        get("/remote", (req, res) -> UrlReader.read(getUrl(), getRemoteKey()));
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001; //returns default port
    }

    static String getKeyStore(){
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "certificados/ecikeystore1.p12"; //returns default port
    }

    static String getRemoteKey(){
        if(System.getenv("REMOTEKEY") != null){
            return System.getenv("REMOTEKEY");
        }
        return "certificados/ecikeystore2.p12";
    }

    static String getKey(){
        if (System.getenv("PASSWORD") != null) {
            return System.getenv("PASSWORD");
        }
        return "123456"; //returns default port
    }

    static String getUrl(){
        if(System.getenv("URL") != null){
            return System.getenv("URL");
        }
        return "https://ec2-3-90-65-95.compute-1.amazonaws.com:5002/local";
    }



}

//keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
