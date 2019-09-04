package springvthswagger.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springvthswagger.model.Product;
import springvthswagger.repository.ProductRepository;
import springvthswagger.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Iterable<Product> listAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(int productId) {
		return productRepository.findOne(productId);
	}

	@Override
	public void saveProduct(Product product) {
		Product productPersisted = productRepository.getOne(product.getId());
		if(productPersisted != null) {
			productRepository.saveAndFlush(product);
		}		
	}

	@Override
	public void deleteProduct(int productId) {
		productRepository.delete(productId);		
	}

}
