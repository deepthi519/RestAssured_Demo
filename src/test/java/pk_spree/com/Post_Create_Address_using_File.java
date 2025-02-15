package pk_spree.com;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class Post_Create_Address_using_File {

	//Global Variable
	    
	@Test
	public void CreateAddress() throws IOException, ParseException {

		  //Create json object of JSONParser class to parse the JSON data
		  JSONParser jsonparser=new JSONParser();
		  //Create object for FileReader class, which help to load and read JSON file
		  FileReader reader = new FileReader(".\\TestData\\address.json");
		  //Returning/assigning to Java Object
		  Object obj = jsonparser.parse(reader);
		  //Convert Java Object to JSON Object, JSONObject is typecast here
		  JSONObject prodjsonobj = (JSONObject)obj;
		
		String bearerToken = "6u1y7UsXy0m6CV7oVE6VMF-u31D7-73GIo-iuQTomHk";
		Response response = RestAssured.given()
				.auth()
				.oauth2(bearerToken)
				.contentType(ContentType.JSON)
				.body(prodjsonobj)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();
		

		// Now let us print the body of the message to see what response
		  // we have recieved from the server
		  String responseBody = response.getBody().asString();
		  System.out.println("Response Body is =>  " + responseBody);
		  // Status Code Validation
		  int statusCode = response.getStatusCode();
		  System.out.println("Status code is =>  " + statusCode);
		  Assert.assertEquals(200, statusCode);	
 
		  JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		  String fname=jsonPathEvaluator.get("data.attributes.firstname").toString();
		  System.out.println("First Name is =>  " + fname);
		  Assert.assertEquals(fname, "deepthi");
		  // VErify that Token Type is Bearer or not
		  String Lname=jsonPathEvaluator.get("data.attributes.lastname").toString();
		  //String ExpTokenType = "Bearer";
		  Assert.assertEquals(Lname, "grandhi");
	}
	
	@Test(priority=1, enabled=false)
	public void getAllFilmsTest()
	{
		RestAssured.baseURI = "https://swapi-graphql.netlify.app";
		String query = "{\"query\":\"{\\n  allFilms(first: 2) {\\n    films {\\n      created\\n      director\\n      title\\n    }\\n  }\\n}\"}";
		given().log().all()
			.contentType("application/json")
			.body(query)
			.when().log().all()
			.post("/.netlify/functions/index")
				.then().log().all()
					.assertThat()
						.statusCode(200)
							.and()
								.body("data.allFilms.films[0].director", equalTo("George Lucas"));
		
	}
	
	@Test(priority=0)
	public void pokemon_v2_pokemon()
	{
		RestAssured.baseURI = "https://beta.pokeapi.co";
		String query = "{\"query\":\"{\\n  pokemon_v2_pokemon(limit: 10) {\\n    height\\n    id\\n    name\\n    order\\n    pokemon_species_id\\n  }\\n}\"}";
		given().log().all()
			.contentType("application/json")
			.body(query)
			.when().log().all()
			.post("/graphql/v1beta")
				.then().log().all()
					.assertThat()
						.statusCode(200)
							.and()
								.body("data.pokemon_v2_pokemon[1].height", equalTo(10))
								.and()
								.body("data.pokemon_v2_pokemon[2].name", equalTo("venusaur"));
		
	}
	
}
