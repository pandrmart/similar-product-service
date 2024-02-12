package com.training.springboot.app.product.service;

import java.util.List;

import com.training.springboot.app.product.model.ProductDetail;

/**
 * The service interface for managing similar products.
 * 
 * @author Pablo
 *
 */
public interface ISimilarProductService {

	/**
	 * Gets a list of similar products for the provided product id.
	 * 
	 * @param productId the product identifier
	 * @return a list of similar products for the provided product id
	 */
	public List<ProductDetail> findSimilarProducts(String productId);

	/**
	 * Gets a list of similar products for the provided product id from cache.
	 * 
	 * @param productId the product identifier
	 * @return a list of similar products for the provided product id
	 */
	public List<ProductDetail> findSimilarProductsFromCache(String productId);

}
