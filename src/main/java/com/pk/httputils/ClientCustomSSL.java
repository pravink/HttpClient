package com.pk.httputils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

	  private static CloseableHttpClient client;

	    public static HttpClient getHttpsClient(String filename,String password) throws Exception {

	        if (client != null) {
	            return client;
	        }
	        try{
	        			  KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
	        			  KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        			  String pKeyPassword = password;
	        			  InputStream keyInput = new FileInputStream(filename);
	        			  keyStore.load(keyInput, pKeyPassword.toCharArray());
	        			  keyInput.close();

	        			  keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());
	        			 //  System.out.println(" keyManagerFactory.init := ");
	        SSLContext sslcontext = SSLContexts.custom().useSSL().build();
	        sslcontext.init(keyManagerFactory.getKeyManagers(), new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());  // need to pass store values here
	        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        client = HttpClients.custom().setSSLSocketFactory(factory).build();
	        
	       // System.out.println("HTTPClient created := "+client);

	        } catch (Exception e){
	        	System.out.println(" Exception "+e);
	        }
	        
	        return client;
	    }

	    public static void releaseInstance() {
	        client = null;
	    }
	
	
    public static String GeneratePrimary(String filename,String password ,String url,String auth) throws Exception {
    	//System.out.println("Begin  GeneratePrimary");
    	    	   	
    	HttpPost post = new HttpPost(url);
    	post.addHeader("Authorization", "Basic "+auth);   //base64 values of key_secreate
    	post.addHeader("Content-Type", "application/x-www-form-urlencoded");  
        	
    	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    	parameters.add(new BasicNameValuePair("grant_type", "client_cert"));
   
    	
		post.setEntity(new UrlEncodedFormEntity(parameters));
		
		//System.out.println("GeneratePrimary: getHttpsClient");
		HttpClient httpClient = getHttpsClient(filename,password);
	//	System.out.println("httpClient.execute(post)");
    	HttpResponse response = httpClient.execute(post);
   // 	System.out.println("HttpResponse response="+response);
        InputStream ips  = response.getEntity().getContent();
        BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));
        if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
        {
            throw new Exception(response.getStatusLine().getReasonPhrase());
        }
        StringBuilder sb = new StringBuilder();
        String s;
        while(true )
        {
            s = buf.readLine();
            if(s==null || s.length()==0)
                break;
            sb.append(s);

        } System.out.println("Primary Token: " + sb.toString());
        buf.close();
        ips.close(); 
    	return sb.toString();
       
    	
    }

}
