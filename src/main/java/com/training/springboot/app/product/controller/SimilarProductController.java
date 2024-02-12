package com.training.springboot.app.product.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.training.springboot.app.product.model.ProductDetail;
import com.training.springboot.app.product.service.ISimilarProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

/**
 * Rest controller that exposes an endpoint to get similar products to the provided product id.
 * Implements resilience enabling circuit breaker features (failures and slow responses) and time limiter.
 * 
 * @author Pablo
 *
 */
@RestController
public class SimilarProductController {

	@Autowired
	private ISimilarProductService similarProductService;

	private final Logger logger = LoggerFactory.getLogger(SimilarProductController.class);

	/**
	 * Exposes an endpoint to get similar products to the provided product id.
	 * Time limiter function needs a future return type to be able to calculate the delay.
	 * 
	 * @param productId the product identifier we want to query
	 * @return a list of ProductDetail
	 */
	@CircuitBreaker(name = "similar-products", fallbackMethod = "findSimilarProductsFallback")
	@TimeLimiter(name = "similar-products")
	@GetMapping("/product/{productId}/similar")
	public CompletableFuture<List<ProductDetail>> findSimilarProducts(@PathVariable String productId) {
		return CompletableFuture.supplyAsync(() -> {
			return similarProductService.findSimilarProducts(productId);
		});
	}

	/**
	 * Fallback method.
	 * For test purposes, we use a simple local cache to return the latest retrieved data
	 * if the circuit breaker detects more fails or delays than the allowed by configuration.
	 * 
	 * @param productId the product identifier we want to query
	 * @param e the exception thrown
	 * @return a list of ProductDetail
	 */
	public CompletableFuture<List<ProductDetail>> findSimilarProductsFallback(String productId, Throwable e) {
		logger.info(String.format("Fallback method invoked, error: %s", e.getMessage()));
		return CompletableFuture.supplyAsync(() -> similarProductService.findSimilarProductsFromCache(productId));
	}

}
