package com.pk.main;

public class Main {
	public static void main(String[] args) {
		 String certificatePath;
		 String certificatePwd;
		 String tokenUrl;
		 String consumerKey;
		 String consumerSecret;
		 
		certificatePath = "C:/Data/certificate.p12";
		certificatePwd = "xxxx";
		tokenUrl = "<token-url>";
		consumerKey = "<app-consumer-key>";
		consumerSecret = "<app-consumer-secret-key>";
		
		TokenGenerator token = new TokenGenerator(certificatePath, certificatePwd, tokenUrl, consumerKey, consumerSecret);
		System.out.println(token.getActiveToken());
	}
	
	
	/*
	 * SAMPLE HTTP PUT / POST Call using outh token 
	 * public boolean updateCheckListItemToEFI(CheckListDetailsResponse checkListDetailsResponse,String workFlowId) {

		HttpClient httpClient = null;
		try {

			// Create a URL for Update EFI API
			StringBuilder updateEFIUrl = new StringBuilder(checkListDetailsResponse.getChecklistTranId() + "/processes/"
					+ checkListDetailsResponse.getProcessId() + "/source-systems/" + "<path>");
			// Gets access Token for the  Gateway access
		
			HttpPut post = new HttpPut("<apiurl>" + updateEFIUrl);
			post.addHeader("Authorization", efiToken);
			post.addHeader("Content-Type", "application/json");
			String jsonFISUpdateInput;
			ObjectMapper mapper = new ObjectMapper();
			// Build Input JSON for the Update item EFI API
			FederatedInboxServiceInput inputRequest = buildfederatedInboxServiceInputRequest(checkListDetailsResponse,
					false, workFlowId);
			jsonFISUpdateInput = mapper.writeValueAsString(inputRequest.getData());
			StringEntity input = new StringEntity(jsonFISUpdateInput);
			post.setEntity(input);
			httpClient = HttpClientFactory.getHttpsClient();
			HttpResponse response = httpClient.execute(post);
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = buf.readLine()) != null) {
				out.append(line);
			}
			System.out.println(out.toString());
		} catch (Exception e) {
			logger.error("Exception at updateCheckListItemToEFI  FederatedInboxServiceImpl -" + e.getMessage(), e);
		
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
		}

		return true;
	}*/
}
