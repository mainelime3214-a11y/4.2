package ru.netology;

import java.util.Arrays;

public class TicketRepository {
    private Ticket[] tickets = new Ticket[0];

    public void add(Ticket ticket) {
        Ticket[] tmp = Arrays.copyOf(tickets, tickets.length + 1);
        tmp[tmp.length - 1] = ticket;
        tickets = tmp;
    }

    public Ticket[] findAll() {
        return tickets;
    }

    public void removeById(int id) {
        Ticket found = null;
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                found = ticket;
                break;
            }
        }

        if (found == null) {
            // В этом задании не требуется, но лучше добавить исключение (например, NotFoundException)
            return;
        }

        Ticket[] tmp = new Ticket[tickets.length - 1];
        int index = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getId() != id) {
                tmp[index] = ticket;
                index++;
            }
        }
        tickets = tmp;
    }
}