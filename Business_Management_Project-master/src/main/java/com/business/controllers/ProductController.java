package com.business.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.business.entities.Product;
import com.business.services.ProductServices;

@Controller
public class ProductController 
{
	@Autowired
	private ProductServices productServices;

	//	AddProduct
	@PostMapping("/addProduct") // URL mapping for adding a product
    public String addProduct(
            @RequestParam("name") String pname,
            @RequestParam("price") double pprice,
            @RequestParam("description") String pdescription,
            @RequestParam("imageFile") MultipartFile pimage,
            Model model) {

        try {
            Product product = new Product(); // Create a new product instance
            product.setPname(pname);
            product.setPprice(pprice);
            product.setPdescription(pdescription);

            // Call the service method to add the product
            productServices.addProduct(product, pimage);

            model.addAttribute("message", "Product added successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to add product: " + e.getMessage());
        }

        return "redirect:/products"; // Redirect to a page displaying the product list
    }
	//	UpdateProduct
	@GetMapping("/updatingProduct/{productId}")
	public String updateProduct(@ModelAttribute Product product,@PathVariable("productId") int id)
	{

		this.productServices.updateproduct(product, id);
		return "redirect:/admin/services";
	}
	//DeleteProduct
	@GetMapping("/deleteProduct/{productId}")
	public String delete(@PathVariable("productId") int id)
	{
		this.productServices.deleteProduct(id);
		return "redirect:/admin/services";
	}
	
}
