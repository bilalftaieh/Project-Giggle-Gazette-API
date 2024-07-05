package com.gigglegazette.article_service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {
    @GetExchange("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id);
}
