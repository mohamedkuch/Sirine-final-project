package com.example.demo.SupportTicket;

import com.example.demo.email.EmailService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service

public class TicketService {
    private final TicketRepository ticketRepository;
    private final EmailService mailSender;

    @Autowired
    public TicketService(TicketRepository ticketRepository,@Qualifier("emailService") EmailService mailSender) {
        this.ticketRepository = ticketRepository;
        this.mailSender = mailSender;
    }

    public Ticket openTicket(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setEmail(ticketRequest.getEmail());
        ticket.setStatus("In Progress");
        return ticketRepository.save(ticket);
    }

    public Ticket resolveTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        ticket.setStatus("Resolved");
        ticketRepository.save(ticket);

        // Notify user via email
        String emailMessage = "Your support ticket (ID: " + ticket.getId() + ") has been resolved.";
        mailSender.sendEmail(ticket.getEmail(), "Support Ticket Resolved", emailMessage);

        return ticket;
    }
}