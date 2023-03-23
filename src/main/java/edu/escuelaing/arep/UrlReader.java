package edu.escuelaing.arep;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class UrlReader {

    public static String read(String url, String file) {
        try {

            // Create a file and a password representation
            File trustStoreFile = new File(file);
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);


            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // We can now read this URL
            return readURL(url);


        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException ex) {
            Logger.getLogger(UrlReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String readURL(String sitetoread) throws MalformedURLException {
        // Crea el objeto que representa una URL2
        URL siteURL = new URL(sitetoread);
        StringBuilder answer = new StringBuilder();
        try {
            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(siteURL.openStream()));

            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                answer.append(inputLine);
            }
        } catch (IOException x) {
            System.err.println("error");
        }
        return answer.toString();
    }
}

