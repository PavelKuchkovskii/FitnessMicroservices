package org.kucher.userservice.service.api;

public interface IEmailService {

    void sendSimpleEmail(String toAddress, String subject, String message);
}
