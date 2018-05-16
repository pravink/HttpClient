# HttpClient
You can use this project to call any OAuth enabled API seamlessly.

For Any OAuth Enabled REST services you can use this project to get active token in just a matter of few steps.
1. download Jar 
2. import it in your classpath
3. use below code to get the active token for your specified OAuth Enabled REST Token API

	String certificatePath = "C:/Data/certificate.p12";
		String certificatePwd = "xxxxxx";
		String tokenUrl = "<token url>";
		String consumerKey = "<consumer-key>";
		String consumerSecret = "<consumer-secret-key>";
		
		TokenGenerator token = new TokenGenerator(certificatePath, certificatePwd, tokenUrl, consumerKey, consumerSecret);		
		// Now Here is you Active token
    	token.getActiveToken();
