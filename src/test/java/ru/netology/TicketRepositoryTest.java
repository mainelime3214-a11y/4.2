package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class TicketRepositoryTest {
    private TicketRepository repository;

    // Тестовые данные (IATA-коды)
    private static final String SVO = "SVO";
    private static final String LED = "LED";
    private static final String DXB = "DXB";

    // Создаем тестовые билеты
    Ticket ticket1 = new Ticket(1, 10000, SVO, LED, 90);
    Ticket ticket2 = new Ticket(2, 5000, SVO, LED, 100);
    Ticket ticket3 = new Ticket(3, 15000, DXB, SVO, 300);

    @BeforeEach
    void setUp() {
        // Создаем новый репозиторий перед каждым тестом
        repository = new TicketRepository();
        repository.add(ticket1);
        repository.add(ticket2);
        repository.add(ticket3);
    }

    @Test
    void shouldFindAllTickets() {
        Ticket[] expected = {ticket1, ticket2, ticket3};
        Ticket[] actual = repository.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldRemoveExistingTicketById() {
        int idToRemove = 2; // Удаляем ticket2
        repository.removeById(idToRemove);

        Ticket[] expected = {ticket1, ticket3};
        Ticket[] actual = repository.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldNotChangeIfRemoveByIdNotExisting() {
        int nonExistingId = 99;
        repository.removeById(nonExistingId);

        // Ожидаемый результат: массив остался прежним
        Ticket[] expected = {ticket1, ticket2, ticket3};
        Ticket[] actual = repository.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldAddOneTicket() {
        Ticket newTicket = new Ticket(4, 8000, LED, DXB, 280);
        repository.add(newTicket);

        Ticket[] expected = {ticket1, ticket2, ticket3, newTicket};
        Ticket[] actual = repository.findAll();

        assertArrayEquals(expected, actual);
    }
}