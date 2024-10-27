package com.business.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.business.entities.Product;
import com.business.repositories.ProductRepository;

import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Component
public class ProductServices 
{
	@Autowired
	private ProductRepository productRepository;

	// Add Product Method - Store Image as BLOB in Database
    public void addProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Get the original file name
            String fileName = imageFile.getOriginalFilename();
            System.out.println("Uploaded file name: " + fileName);

            // Define the path where the image should be saved (on server or local file system)
            String uploadDir = "src/main/resources/static/Images/";
            Path uploadPath = Paths.get(uploadDir);

            // Ensure the directory exists, if not, create it
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the specified directory
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set the product's pimage field to the file name (or full path if needed)
            product.setPimage(fileName);
            System.out.println(product);
            productRepository.save(product);
        }

        // Save the product (with the image file name) to the database
        
    }
	//getAll products
	public List<Product> getAllProducts()
	{
		List<Product> products=(List<Product>)this.productRepository.findAll();
		return products;
	}

	//get Single Product
	public Product getProduct(int id)
	{
		Optional<Product> optional = this.productRepository.findById(id);
		Product product=optional.get();
		return product;
	}

	//update Product
	public void updateproduct(Product p,int id)
	{
		p.setPid(id);
		Optional<Product> optional = this.productRepository.findById(id);
		Product prod=optional.get();

		if(prod.getPid()==id)
		{
			this.productRepository.save(p);				
		}
	}
	//delete product
	public void deleteProduct(int id)
	{
		this.productRepository.deleteById(id);
	}

	//Get Product By Name
	public Product getProductByName(String name)
	{
		
		Product product= this.productRepository.findByPname(name);
		if(product!=null)
		{
			return product;
		}
		return null;
	
	}
}