package pk_spree.com;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Post_Create_Address_using_File_creating_Oauth_from_Util {


	//Global Variable
	String outh_token;

	@Test()
	public void Create_Address() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(Util_Functions.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(Util_Functions.readFile("address.json"))
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		// System.out.println("Response Body is => " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

	}
}
