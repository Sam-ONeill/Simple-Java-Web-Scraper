
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class webfetch{
	public static void main(String args[]) throws IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://www.cs.nuim.ie");
		CloseableHttpResponse response = client.execute(request);
		// Get the response
		BufferedReader rd = new BufferedReader
		 (new InputStreamReader(response.getEntity().getContent()));
		BufferedWriter wd = new BufferedWriter(new FileWriter(new File("LandingPage.txt")));
		
		String line = "";
		while ((line = rd.readLine()) != null) {
		 //System.out.println(line);
			 wd.write(line);
			} 
		response.close();
		client.close();
		FetchUrls();
		
		}
	//Parse LandingPage.txt and grab all embedded hypertext url's
	public static void FetchUrls() throws IOException {
		BufferedReader crd = new BufferedReader
				(new FileReader("LandingPage.txt"));
			int count = 0;
			// String regex was created with refernce to the following forum link https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url
			String regex = "(https?|http):\\/\\/(www\\.)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			String line = "";
			while ((line = crd.readLine()) != null) {
				Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			    Matcher m = p.matcher(line);
			    while (m.find() == true ) {
			    	WriteChildren(count,m.group(0));
					 //System.out.println(m);

			    	count++;
			    }
			
			 //System.out.println(line);
			 
			} 
			crd.close();
			
	}
	// Save each of those files in a separate file called ChildX.text
	public static void WriteChildren(int counter, String url) throws ClientProtocolException, UnsupportedOperationException {
		//System.out.println(url);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		CloseableHttpResponse response;
		try {
			response = client.execute(request);
			// Get the response
			BufferedReader rd = new BufferedReader
			 (new InputStreamReader(response.getEntity().getContent()));
			BufferedWriter wd = new BufferedWriter(new FileWriter(new File("child"+counter+".txt")));
			String line = "";
			while ((line = rd.readLine()) != null) {
			 //System.out.println(line);
				 wd.write(line);
				} 
			response.close();
			client.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		

	}
	
	
	
	
	
