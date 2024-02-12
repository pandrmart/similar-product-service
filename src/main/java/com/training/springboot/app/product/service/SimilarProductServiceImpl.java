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

	private Map<String, List<String>> similarProductCache = new HashMap<>();
	private Map<String, ProductDetail> productCache = new HashMap<>();

	private Logger logger = LoggerFactory.getLogger(SimilarProductServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDetail> findSimilarProducts(String productId) {

		logger.info(String.format("Getting similar products for product id: %s", productId));

		List<String> similarProductIds = productRestClient.findSimilarProductIds(productId);
		similarProductCache.put(productId, similarProductIds);

		List<ProductDetail> productDetailList = similarProductIds.stream().map(similarProductId -> {
			try {
				logger.debug(String.format("Getting product by id: %s", similarProductId));
				ProductDetail productDetail = productRestClient.findById(similarProductId);
				productCache.put(similarProductId, productDetail);
				return productDetail;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return null;
		})
		.filter(Objects::nonNull)
		.collect(Collectors.toList());

		return productDetailList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDetail> findSimilarProductsFromCache(String productId) {
		List<ProductDetail> similarProducts = new ArrayList<>();
		if (similarProductCache.containsKey(productId)) {
			for (String similarProductId : similarProductCache.get(productId)) {
				if (productCache.containsKey(similarProductId)) {
					similarProducts.add(productCache.get(similarProductId));
				}
			}
		}
		return similarProducts;
	}

}
