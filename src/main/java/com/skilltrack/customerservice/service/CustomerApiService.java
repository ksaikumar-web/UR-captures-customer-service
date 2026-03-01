package com.skilltrack.customerservice.service;

import com.google.api.client.http.InputStreamContent;
import com.skilltrack.customerservice.model.Customer;
import com.skilltrack.customerservice.model.Images;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;

@Service
public class CustomerApiService {
    @Value("${customerdata.baseurl}")
    private String customerDataBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    
    private final WebClient webClient;
    
    public CustomerApiService(WebClient webClient) {
    	this.webClient = webClient;
    }
    

    public ResponseEntity<?> register(Customer customer) {
        ResponseEntity<Customer> response = restTemplate.postForEntity(
                customerDataBaseUrl + "/register",
                customer,
                Customer.class
        );
        return response;
    }

    public ResponseEntity<?> login(Customer customer) {
        ResponseEntity<Customer> response = restTemplate.postForEntity(
                customerDataBaseUrl + "/login",
                customer,
                Customer.class
        );
        return response;
    }
    
    public Customer getCustomerById(String customerId) {
    	
        	return restTemplate.getForObject(customerDataBaseUrl + "/getCustomer"+"/"+customerId,Customer.class);
        	
    }
    
    public ResponseEntity<?> getImagesById(String customerId){
    	Flux<Images> resp = webClient
    			.get()
    			.uri(customerDataBaseUrl + "/" + customerId +"/getImages")
    			.retrieve().bodyToFlux(Images.class);
    	
    	List<Images> images = resp.collectList().block();
    	
    	return ResponseEntity.ok(images);
    }
    
    public ResponseEntity<?> uploadImage(MultipartFile file,String customerId) throws IOException{
    	
    	ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
    		@Override
    		public String getFilename() {
    			return file.getOriginalFilename();
    		}
    	};
    	
    	MultipartBodyBuilder builder = new MultipartBodyBuilder();
    	builder.part("file", fileAsResource)
    	.header("Content-Disposition", "form-data; name=\"file\";filename=\""+file.getOriginalFilename()+"\"")
    	.contentType(MediaType.APPLICATION_OCTET_STREAM);
    	
    	
    	ResponseEntity<Images> responseBody = webClient.post()
    			.uri(customerDataBaseUrl+"/"+customerId+"/upload")
    			.contentType(MediaType.MULTIPART_FORM_DATA)
    			.body(BodyInserters.fromMultipartData(builder.build()))
    			.retrieve()
    			.toEntity(Images.class)
    			.block();
    	
    	return ResponseEntity.ok(responseBody);
    
    }
//    
//    private MultiValueMap<String, Object> createBody(MultipartFile file) throws IOException{
//    	MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//    	body.add("file", new InputStreamResource(file.getInputStream()) {
//    		@Override
//    		public String getFilename() {
//    			return file.getOriginalFilename();
//    		}
//    	});
//    	
//    	return body;
//    }
    
//    public ResponseEntity<?> uploadImage(MultipartFile file,String customerId) throws IOException{
//    	
//    	HttpHeaders headers = new HttpHeaders();
//    	
//    	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//    	
//    	ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
//    		public String getFileName() {
//    			return file.getOriginalFilename();
//    		}
//    	};
//    	
//    	System.out.println(fileAsResource);
//    	
//    	HttpHeaders filePartHeaders = new HttpHeaders();
//    	
//    	filePartHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//    	
//    	filePartHeaders.setContentDispositionFormData("file", file.getOriginalFilename());
//    	
//    	HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(fileAsResource,filePartHeaders);
//    	
//    	MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//    	
//    	body.add("file", filePart);
//    	
//    	HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(body,headers);
//    	
//    	ResponseEntity<?> responseEntity = restTemplate.postForEntity(customerDataBaseUrl+"/"+customerId+"/upload", requestEntity,Images.class);
//    	
//    	System.out.println("Hellooo"+responseEntity.getBody().toString());
//    	return responseEntity;
//    }
}
