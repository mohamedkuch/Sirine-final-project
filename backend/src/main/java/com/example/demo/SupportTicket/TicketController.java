package com.example.demo.SupportTicket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/open")
    public Ticket openTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.openTicket(ticketRequest);
    }

    @PostMapping("/{ticketId}/resolve")
    public Ticket resolveTicket(@PathVariable Long ticketId) {
        return ticketService.resolveTicket(ticketId);
    }
}