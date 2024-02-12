package com.training.springboot.app.product.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.training.springboot.app.product.model.ProductDetail;

/**
 * This interface is declared as a feign client.
 * The name for feign client is required but not relevant in this scenario,
 * since we are not using service discovery or load balancing.
 * 
 * @author Pablo
 *
 */
@FeignClient(name = "product", url = "localhost:3001")
public interface ProductRestClient {

	/**
	 * Gets a list of similar product ids for the provided product id
	 * by sending a request to the defined endpoint.
	 * 
	 * @param productId the product identifier
	 * @return a list of similar product ids for the provided product id
	 */
	@GetMapping("/product/{productId}/similarids")
	public List<String> findSimilarProductIds(@PathVariable String productId);

	/**
	 * Gets the ProductDetail corresponding to the provided product id
	 * by sending a request to the defined endpoint.
	 * 
	 * @param productId the product identifier
	 * @return the ProductDetail corresponding to the provided product id
	 */
	@GetMapping("/product/{productId}")
	public ProductDetail findById(@PathVariable String productId);

}
