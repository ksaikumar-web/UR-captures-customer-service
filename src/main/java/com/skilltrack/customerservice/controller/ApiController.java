package com.skilltrack.customerservice.controller;
import com.skilltrack.customerservice.model.Customer;
import com.skilltrack.customerservice.model.Images;
import com.skilltrack.customerservice.service.CustomerApiService;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.ai.chat.client.ChatClient;

@RestController
@RequestMapping("/api/public")
public class ApiController {
	
    @Autowired
    private CustomerApiService apiService;
    
    private final ChatClient chatClient;
    
    public ApiController(ChatClient.Builder chatClient) {
    	this.chatClient = chatClient.build();
    }
    
    
    @GetMapping("/ask")
    public String ask(@RequestParam String prompt) {
    	return chatClient.prompt().user(prompt).call().content();
    }

    
    @PostMapping("/{customerId}/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestPart("file") MultipartFile file,@PathVariable String customerId) throws IOException{
		return apiService.uploadImage(file,customerId);
    }
    
    @GetMapping("/getImages/{customerId}")
    public ResponseEntity<?> getImagesById(@PathVariable String customerId){
    	return apiService.getImagesById(customerId);
    }
    
    @GetMapping("/getCustomerByID/{customerId}")
    public Customer getCustomer(@PathVariable String customerId) {
    	return apiService.getCustomerById(customerId);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        return apiService.register(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        return apiService.login(customer);
    }
}
