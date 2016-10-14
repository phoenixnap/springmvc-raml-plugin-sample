package com.phoenixnap.oss.sample.client.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.phoenixnap.oss.sample.client.DrinkClient;
import com.phoenixnap.oss.sample.client.model.CreateDrinkRequest;
import com.phoenixnap.oss.sample.client.model.GetDrinkByIdResponse;
import com.phoenixnap.oss.sample.client.model.GetDrinksResponse;
import com.phoenixnap.oss.sample.client.model.UpdateDrinkByIdRequest;
import com.phoenixnap.oss.sample.client.test.factory.DrinkFactory;
import com.phoenixnap.oss.sample.main.ClientLauncher;
import com.phoenixnap.oss.sample.server.ServerLauncher;

import feign.FeignException;

/**
 * Test suite that runs integration tests to ensure the automatically generated
 * DrinkClient & DrinkController are working in sync. Client requests generated
 * by the client should map directly onto an endpoint made available by the
 * controller.
 * 
 * 
 * @author kristiang
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServerLauncher.class, ClientLauncher.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class TestDrinksClient {

	@Autowired
	private DrinkClient drinkClient;

	@Test
	public void getDrinksIntegrationTest() throws Exception {
		ResponseEntity<List<GetDrinksResponse>> getDrinksResponse = drinkClient.getDrinks();

		Assert.assertEquals(HttpStatus.OK, getDrinksResponse.getStatusCode());
		Assert.assertNotNull(getDrinksResponse.getBody());
		Assert.assertTrue(getDrinksResponse.getBody().size() > 0);
	}

	@Test
	public void getDrinkByIdIntegrationTest_Pass() throws Exception {
		ResponseEntity<GetDrinkByIdResponse> getDrinksResponse = drinkClient.getDrinkById("fanta");

		Assert.assertEquals(HttpStatus.OK, getDrinksResponse.getStatusCode());
		Assert.assertNotNull(getDrinksResponse.getBody());
		Assert.assertEquals("Fanta", getDrinksResponse.getBody().getName());
		Assert.assertEquals("SOFT_DRINK", getDrinksResponse.getBody().getType());
	}

	@Test
	public void getDrinkByIdIntegrationTest_Fail_NotFound() throws Exception {
		try {
			drinkClient.getDrinkById("sprite");
			Assert.fail("The item should not be available! Are the server and client working properly?!");
		} catch (HttpClientErrorException hce) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, hce.getStatusCode());
		} catch (HystrixRuntimeException hre) {
			FeignException cause = (FeignException) hre.getCause();
			Assert.assertEquals(HttpStatus.NOT_FOUND.value(), cause.status());
		}
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void createDrinkIntegrationTest() throws Exception {
		CreateDrinkRequest createDrinkRequest = DrinkFactory.getDrink();

		ResponseEntity createDrinkResponse = drinkClient.createDrink(createDrinkRequest);
		Assert.assertEquals(HttpStatus.ACCEPTED, createDrinkResponse.getStatusCode());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void updateDrinkIntegrationTest() throws Exception {
		UpdateDrinkByIdRequest updateDrinkRequest = new UpdateDrinkByIdRequest();
		updateDrinkRequest.setName("Beer");

		ResponseEntity updateDrinkResponse = drinkClient.updateDrinkById("Martini", updateDrinkRequest);
		Assert.assertEquals(HttpStatus.OK, updateDrinkResponse.getStatusCode());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void deleteDrinkIntegrationTest() throws Exception {
		ResponseEntity updateDrinkResponse = drinkClient.deleteDrinkById("cocacola");
		Assert.assertEquals(HttpStatus.ACCEPTED, updateDrinkResponse.getStatusCode());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void endToEndDrinksIntegrationTest() {
		// get list and establish base number of items
		ResponseEntity<List<GetDrinksResponse>> getDrinksResponseOrig = drinkClient.getDrinks();

		// create item
		CreateDrinkRequest createDrinkRequest = DrinkFactory.getDrink();
		ResponseEntity createDrinkResponse = drinkClient.createDrink(createDrinkRequest);
		Assert.assertEquals(HttpStatus.ACCEPTED, createDrinkResponse.getStatusCode());

		// get all drinks and assert they are one larger than when they started
		// off
		ResponseEntity<List<GetDrinksResponse>> getDrinksResponseAfterCreate = drinkClient.getDrinks();
		Assert.assertEquals(getDrinksResponseOrig.getBody().size() + 1, getDrinksResponseAfterCreate.getBody().size());

		// get created item
		ResponseEntity<GetDrinkByIdResponse> getDrink = drinkClient.getDrinkById(createDrinkRequest.getName());

		// update item
		UpdateDrinkByIdRequest updateDrinkRequest = new UpdateDrinkByIdRequest();
		updateDrinkRequest.setName("Beer");

		ResponseEntity updateDrinkResponse = drinkClient.updateDrinkById(getDrink.getBody().getName(),
				updateDrinkRequest);
		Assert.assertEquals(HttpStatus.OK, updateDrinkResponse.getStatusCode());

		// get last updated details
		getDrink = drinkClient.getDrinkById(updateDrinkRequest.getName());
		Assert.assertEquals(HttpStatus.OK, getDrink.getStatusCode());
		// assert the new name is retrievable
		Assert.assertNotNull(getDrink.getBody());
		Assert.assertEquals(updateDrinkRequest.getName(), getDrink.getBody().getName());

		// assert the item is not accessible by its old name
		try {
			drinkClient.getDrinkById(createDrinkRequest.getName());
			Assert.fail("The item should not be available! Are the server and client working properly?");
		} catch (HttpClientErrorException hce) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, hce.getStatusCode());
		} catch (HystrixRuntimeException hre) {
			FeignException cause = (FeignException) hre.getCause();
			Assert.assertEquals(HttpStatus.NOT_FOUND.value(), cause.status());
		}

		// delete item
		ResponseEntity deleteDrink = drinkClient.deleteDrinkById(updateDrinkRequest.getName());
		Assert.assertEquals(HttpStatus.ACCEPTED, deleteDrink.getStatusCode());

		// assert the item isn't available
		try {
			drinkClient.getDrinkById(updateDrinkRequest.getName());
			Assert.fail("The item should not be available! Are the server and client working properly?");
		} catch (HttpClientErrorException hce) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, hce.getStatusCode());
		} catch (HystrixRuntimeException hre) {
			FeignException cause = (FeignException) hre.getCause();
			Assert.assertEquals(HttpStatus.NOT_FOUND.value(), cause.status());
		}

		// assert list size is same as when we started off
		ResponseEntity<List<GetDrinksResponse>> getDrinksResponseFinal = drinkClient.getDrinks();
		Assert.assertEquals(getDrinksResponseOrig.getBody().size(), getDrinksResponseFinal.getBody().size());
	}
}
