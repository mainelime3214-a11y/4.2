package ru.netology;

import java.util.Arrays;

public class TicketManager {
    private TicketRepository repository;

    public TicketManager(TicketRepository repository) {
        this.repository = repository;
    }

    /**
     * Ищет билеты по аэропорту вылета и прилёта и сортирует их по цене.
     *
     * @param from Аэропорт вылета (IATA-код).
     * @param to Аэропорт прилёта (IATA-код).
     * @return Массив найденных и отсортированных билетов.
     */
    public Ticket[] search(String from, String to) {
        Ticket[] result = new Ticket[0];

        for (Ticket ticket : repository.findAll()) {
            if (matches(ticket, from, to)) {
                // Добавляем подходящий билет в массив result
                Ticket[] tmp = new Ticket[result.length + 1];
                System.arraycopy(result, 0, tmp, 0, result.length);
                tmp[tmp.length - 1] = ticket;
                result = tmp;
            }
        }

        // Сортируем найденные билеты, используя compareTo из класса Ticket
        Arrays.sort(result);

        return result;
    }

    // Метод определения соответствия билета условиям поиска
    public boolean matches(Ticket ticket, String from, String to) {
        return ticket.getFrom().equals(from) && ticket.getTo().equals(to);
    }


}