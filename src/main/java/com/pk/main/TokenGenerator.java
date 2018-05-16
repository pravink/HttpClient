package com.pk.main;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.pk.httputils.ClientCustomSSL;

public class TokenGenerator {
	static String primaryToken = null;
	
	private String certificatePath;
	private String certificatePwd;
	private String tokenUrl;
	private String consumerKey;
	private String consumerSecret;
	
	public TokenGenerator(String certificatePath2, String certificatePwd2, String tokenUrl2, String consumerKey2,
			String consumerSecret2) {
		this.certificatePath = certificatePath2;
		this.certificatePwd = certificatePwd2;
		this.consumerKey = consumerKey2;
		this.consumerSecret = consumerSecret2;
		this.tokenUrl = tokenUrl2;
	}
	public String getActiveToken(){
		if (primaryToken==null){
			synchronized (this) {
					generateToken(certificatePath, certificatePwd, tokenUrl, consumerKey, consumerSecret);
			}
		}
		return primaryToken;
	}
	/**
	 * 
	 * @param certificatePath
	 * @param certificatePwd
	 * @param tokenUrl
	 * @param consumerKey
	 * @param consumerSecret
	 * @return
	 */
	private static String generateToken(String certificatePath, String certificatePwd, String tokenUrl, String consumerKey, String consumerSecret) {
		
		final String pair = consumerKey+ ":" +consumerSecret;
		final byte[] encodedBytes = Base64.encodeBase64(pair.getBytes());
		
		String auth=new String (encodedBytes);
		 
		try {
			primaryToken = ClientCustomSSL.GeneratePrimary(certificatePath,certificatePwd,tokenUrl,auth);
			JSONObject json = new JSONObject(primaryToken);
			primaryToken = (String) json.get("access_token");
			System.out.println("primary Token ="+ primaryToken);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primaryToken;
		
	}
	public String getCertificatePath() {
		return certificatePath;
	}
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	public String getCertificatePwd() {
		return certificatePwd;
	}
	public void setCertificatePwd(String certificatePwd) {
		this.certificatePwd = certificatePwd;
	}
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerSecret() {
		return consumerSecret;
	}
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
}
