package com.blog.blogapi.service;

import com.blog.blogapi.application.messagecreate.MessageCreateRequest;
import com.blog.blogapi.data.MessageRepository;
import com.blog.blogapi.domain.assembler.MessageAssembler;
import com.blog.blogapi.domain.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageAssembler messageAssembler;

    public boolean create(MessageCreateRequest request) {
        log.info("Message Create request started with name: {}", request.getName());
        try {
            Message message = messageAssembler.apply(request);
            messageRepository.save(message);

            log.info("Message Create request completed with name: {}", request.getName());
            return true;
        } catch (Exception e) {
            log.error("Message Create request failed with error: {}", e.getMessage());
            return false;
        }
    }
}
