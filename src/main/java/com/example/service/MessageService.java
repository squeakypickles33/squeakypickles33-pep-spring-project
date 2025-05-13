package com.example.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addMessage(Message message) throws Exception {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message is missing or longer than 255 characters.");
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new Exception("User does not exist, message not added");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() throws Exception {
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(Integer id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.get();
    }

    public Integer deleteMessageByMessageId(Integer id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer updateMessageByMessageId(Integer id, Message newText) throws Exception {
        try {
            if (newText.getMessageText().isBlank() || newText.getMessageText().length() > 255) {
                throw new IllegalArgumentException("Message is missing or longer than 255 characters.");
            }
            Optional<Message> existingMessage = messageRepository.findById(id);
            if (existingMessage.isEmpty()) {
                throw new Exception("Message not found");
            }
            existingMessage.get().setMessageText(newText.getMessageText());
            messageRepository.save(existingMessage.get());
            return 1;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
