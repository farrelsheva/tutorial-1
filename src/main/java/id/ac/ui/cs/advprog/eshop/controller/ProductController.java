package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId, RedirectAttributes redirectAttributes){
        try{
            service.deleteById(productId);
        }catch (NoSuchElementException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/list";
        }
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, Model model){
        Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{productId}")
    public String editProductPost(@PathVariable String productId, @ModelAttribute Product product, RedirectAttributes redirectAttributes){
        Product existingProduct = service.findById(productId);
        if(existingProduct == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
            return "redirect:/product/list";
        }
        service.editById(productId, product);
        return "redirect:/product/list";
    }


    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

}

@Controller
@RequestMapping("/car")
class CarController extends ProductController{
    @Autowired
    private CarServiceImpl carService;

    @GetMapping("/createCar")
    public String CreateCarPage(Model model){
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model){
        carService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model){
        List<Car> allCars = carService.findALl();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model){
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model){
        System.out.println(car.getCarId());
        carService.update(car.getCarId(), car);

        return "redirect:listCar";
    }

    @PostMapping
    public String deleteCar(@RequestParam("carId") String carId){
        carService.deleteCarById(carId);
        return "redirect:listCar";
    }
}