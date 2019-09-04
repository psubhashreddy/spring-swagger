package springvthswagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Model;
import springvthswagger.model.Product;
import springvthswagger.service.ProductService;

@RestController
@RequestMapping("/product")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class ProductController {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "View a list of available products", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	public Iterable<Product> list(Model model) {
		Iterable<Product> productList = productService.listAllProducts();
		return productList;
	}

	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Search a product with an ID",response = Product.class)
	public Product showProduct(@PathVariable Integer id, Model model) {
		Product product = productService.getProductById(id);
		return product;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "Add a product")
	public ResponseEntity saveProduct(@RequestBody Product product) {
		productService.saveProduct(product);
		return new ResponseEntity("Product saved successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Update a product")
	public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product) {
		Product storedProduct = productService.getProductById(id);
		storedProduct.setDescription(product.getDescription());
		storedProduct.setImageUrl(product.getImageUrl());
		storedProduct.setPrice(product.getPrice());
		productService.saveProduct(storedProduct);
		return new ResponseEntity("Product updated successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ApiOperation(value = "Delete a product")
	public ResponseEntity delete(@PathVariable Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
	}

}
