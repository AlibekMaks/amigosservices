package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
//    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo check email is exist

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        // todo send notification
    /*    notificationClient.sendNotification(
                new NotificationRequest(customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode", customer.getFirstName()))
        );*/

    }
}
