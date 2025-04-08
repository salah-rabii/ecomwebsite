//package com.myecommerce.service;
//
//
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import com.myecommerce.DTO.ProductRequestStripe;
//import com.myecommerce.DTO.StripeResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//@Service
//public class StripeService {
//
//    @Value("${stripe.secretKey}")
//    private String secretKey;
//
//    public StripeResponse checkoutProducts(ProductRequestStripe productRequest) {
//        // Set the secret key to authenticate Stripe API requests
//        Stripe.apiKey = secretKey;
//
//        // Build product data
//        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                .setName(productRequest.getName())
//                .build();
//
//        // Build price data
//        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
//                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
//                .setUnitAmount(productRequest.getAmount())
//                .setProductData(productData)
//                .build();
//
//        // Build line item
//        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
//                .setQuantity(productRequest.getQuantity())
//                .setPriceData(priceData)
//                .build();
//
//        // Build session parameters
//        SessionCreateParams params = SessionCreateParams.builder()
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("http://localhost:3000/produit")
//                .setCancelUrl("http://localhost:3000/cancel")
//                .addLineItem(lineItem)
//                .build();
//
//        try {
//            // Create a session using Stripe API
//            Session session = Session.create(params);
//
//            return StripeResponse.builder()
//                    .status("success")
//                    .message("Payment initiated")
//                    .sessionId(session.getId())
//                    .sessionUrl(session.getUrl())
//                    .build();
//        } catch (StripeException ex) {
//            System.out.println("Stripe error: " + ex.getMessage());
//
//            // Retourner une réponse d'erreur au lieu de null
//            return StripeResponse.builder()
//                    .status("error")
//                    .message("Payment processing failed: " + ex.getMessage())
//                    .build();
//        }
//    }
//}

package com.myecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.myecommerce.DTO.ProductRequestStripe;
import com.myecommerce.DTO.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse checkoutProducts(ProductRequestStripe productRequest) {
        // Set the secret key to authenticate Stripe API requests
        Stripe.apiKey = secretKey;

        try {
            // Build product data
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(productRequest.getName())
                    .build();

            // Build price data
            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                    .setUnitAmount(productRequest.getAmount())
                    .setProductData(productData)
                    .build();

            // Build line item
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(productRequest.getQuantity())
                    .setPriceData(priceData)
                    .build();

            // Build session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/success")
                    .setCancelUrl("http://localhost:3000/cancel")
                    .addLineItem(lineItem)
                    .build();

            // Log avant de créer la session
            System.out.println("Creating Stripe session with params: " + params.toString());

            // Create a session using Stripe API
            Session session = Session.create(params);

            // Log des informations de session
            System.out.println("Session created successfully. ID: " + session.getId());
            System.out.println("Session URL: " + session.getUrl());

            return StripeResponse.builder()
                    .status("success")
                    .message("Payment initiated")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();
        } catch (StripeException ex) {
            // Log plus détaillé des erreurs Stripe
            System.out.println("Stripe error code: " + ex.getCode());
            System.out.println("Stripe error message: " + ex.getMessage());
            System.out.println("Stripe error details: " + ex.getStripeError());
            ex.printStackTrace();

            return StripeResponse.builder()
                    .status("error")
                    .message("Payment processing failed: " + ex.getMessage())
                    .build();
        } catch (Exception ex) {
            System.out.println("General error: " + ex.getMessage());
            ex.printStackTrace();

            return StripeResponse.builder()
                    .status("error")
                    .message("Server error: " + ex.getMessage())
                    .build();
        }
    }
}

