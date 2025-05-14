package com.example.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account newAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(newAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {
            Message addedMessage = messageService.addMessage(message);
            return new ResponseEntity<>(addedMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable Integer messageId) {
        try {
            Message message = messageService.getMessageByMessageId(messageId);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId) {
        try {
            Integer rowsAffected = messageService.deleteMessageByMessageId(messageId);
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMessageId(@PathVariable Integer messageId, @RequestBody Message newText) {
        try {
            Integer rowsAffected = messageService.updateMessageByMessageId(messageId, newText);
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

     @GetMapping("/accounts/{accountId}/messages")
     public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        try {
            List<Message> messagesByUser = messageService.getAllMessagesByUser(accountId);
            return new ResponseEntity<>(messagesByUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
     }

}
