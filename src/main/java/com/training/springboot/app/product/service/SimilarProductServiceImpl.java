package com.training.springboot.app.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.springboot.app.product.client.ProductRestClient;
import com.training.springboot.app.product.model.ProductDetail;

/**
 * Similar product service implementation.
 * 
 * @author Pablo
 *
 */
@Service
public class SimilarProductServiceImpl implements ISimilarProductService {

	@Autowired
	private ProductRestClient productRestClient;

	private Map<String, List<ProductDetail>> cache = new HashMap<>();

	private Logger logger = LoggerFactory.getLogger(SimilarProductServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDetail> findSimilarProducts(String productId) {

		logger.info(String.format("Getting similar products for product id: %s", productId));

		List<String> similarProductIds = productRestClient.findSimilarProductIds(productId);

		List<ProductDetail> productDetailList = similarProductIds.stream().map(similarProductId -> {
			try {
				logger.debug(String.format("Getting product by id: %s", similarProductId));
				return productRestClient.findById(similarProductId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return null;
		})
		.filter(Objects::nonNull)
		.collect(Collectors.toList());

		cache.put(productId, productDetailList);

		return productDetailList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDetail> findSimilarProductsFromCache(String productId) {
		return cache.containsKey(productId) ? cache.get(productId) : new ArrayList<>();
	}

}
