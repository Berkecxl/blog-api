package com.blog.blogapi.controller;

import com.blog.blogapi.application.messagecreate.MessageCreateRequest;
import com.blog.blogapi.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public boolean createMessage(@RequestBody MessageCreateRequest request) {
        return messageService.create(request);
    }
}
