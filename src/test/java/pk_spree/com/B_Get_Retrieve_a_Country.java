package pk_spree.com;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class B_Get_Retrieve_a_Country extends Spreecom_TestData{
	@Test(priority = 1,enabled=false)
	public void getDefaultCountry() 
	{
		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification httpRequest = RestAssured.given();
		//Response response = httpRequest.get();
		Response response = httpRequest.request(Method.GET,"/api/v2/storefront/countries/pak");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		// First get the JsonPath object instance from the Response interface
		Assert.assertEquals(responseBody.contains("PAKISTAN") /*Expected value*/, true /*Actual Value*/);

	}
	
	  
	  @Test(dataProvider="country_iso", priority = 2)
	  public void getCountry(String iso, String iso_name_exp,String iso3_exp) {
		  
		  RestAssured.baseURI = "https://demo.spreecommerce.org/api/v2/storefront";
		  RequestSpecification httpRequest = RestAssured.given();
		  Response response = httpRequest.get("/countries/"+iso);
		  
			// Now let us print the body of the message to see what response
		  // we have recieved from the server
		  String responseBody = response.getBody().asString();
		  System.out.println("Response Body is =>  " + responseBody);
		  // Status Code Validation
		  int statusCode = response.getStatusCode();
		  System.out.println("Status code is =>  " + statusCode);
		  Assert.assertEquals(200, statusCode);
	 
		// Verify ISO_name
		  	JsonPath js = new JsonPath(response.asString());
			String iso_name_act = js.get("data.attributes.iso_name");
			System.out.println(iso_name_act);
			Assert.assertEquals(iso_name_act, iso_name_exp);
			
			// Verify ISO3
			String iso3_act = js.get("data.attributes.iso3");
			System.out.println(iso3_act);
			Assert.assertEquals(iso3_act, iso3_exp);
	  }
	
}
