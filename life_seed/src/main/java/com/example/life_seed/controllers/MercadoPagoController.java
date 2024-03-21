
package com.example.life_seed.controllers;

import com.example.life_seed.entitys.ProductRequest;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/allUser")
public class MercadoPagoController {
    
     @PostMapping("/create")
public ResponseEntity<String> payment(@RequestBody List<ProductRequest> productos) throws MPException {
    MercadoPagoConfig.setAccessToken("TEST-1384405973768600-031922-9823f9e8a8c4a79820d7e154faad58a2-1733948455");

    List<PreferenceItemRequest> items = new ArrayList<>();

    for (ProductRequest product : productos) {
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
            .id(product.getIdProduct())
            .title(product.getName())
            .description(product.getDesciption())
            .categoryId(product.getIdCategory())
            .quantity(1)
            .currencyId("COP")
            .unitPrice(new BigDecimal(product.getPrice()))
            .build();

                items.add(itemRequest);
    }

    PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(items)
            .build();

    try {
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);
        ///
        System.out.println(preference.getInitPoint());
        return ResponseEntity.ok(preference.getInitPoint());
    } catch (MPApiException ex) {
        System.out.printf(ex.getMessage());
        return ResponseEntity.ok("payment no");
    } catch (MPException ex) {
        ex.printStackTrace();
        return ResponseEntity.ok("payment no 2");

    }
}

}