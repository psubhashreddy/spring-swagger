package springvthswagger.service;

import springvthswagger.model.Product;

public interface ProductService {

	Iterable<Product> listAllProducts();

	Product getProductById(int productId);

	void saveProduct(Product product);

	void deleteProduct(int productId);
}
