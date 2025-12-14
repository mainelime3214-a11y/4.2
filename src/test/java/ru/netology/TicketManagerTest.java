package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketManagerTest {
    private TicketRepository repository;
    private TicketManager manager;

    private static final String SVO = "SVO";
    private static final String LED = "LED";
    private static final String DXB = "DXB";
    private static final String GOA = "GOA";

    // Создаем тестовые данные
    Ticket ticket1 = new Ticket(1, 10_000, SVO, LED, 90);
    Ticket ticket2 = new Ticket(2, 5_000, SVO, LED, 100);
    Ticket ticket3 = new Ticket(3, 15_000, SVO, LED, 120);
    Ticket ticket4 = new Ticket(4, 15_000, SVO, LED, 110);
    Ticket ticket5 = new Ticket(5, 50_000, SVO, DXB, 300);

    @BeforeEach
    void setUp() {
        repository = new TicketRepository();
        repository.add(ticket1);
        repository.add(ticket2);
        repository.add(ticket3);
        repository.add(ticket4);
        repository.add(ticket5);
        manager = new TicketManager(repository);
    }

    // --- Тесты поиска (Search) ---

    @Test
    void shouldFindAndSortMultipleTickets() {
        Ticket[] expected = {ticket2, ticket1, ticket3, ticket4};
        Ticket[] actual = manager.search(SVO, LED);
        assertArrayEquals(expected, actual);
    }

    // Ветка matches: From=False && To=X
    @Test
    void shouldReturnEmptyIfNothingMatches() {
        Ticket[] expected = {};
        Ticket[] actual = manager.search(GOA, DXB);
        assertArrayEquals(expected, actual); // ИСПРАВЛЕНО
    }

    // Ветка matches: From=True && To=False (Закрывает последнюю ветку логики &&)
    @Test
    void shouldNotFindIfOnlyFromMatches() {
        Ticket[] expected = {};
        Ticket[] actual = manager.search(SVO, GOA);
        assertArrayEquals(expected, actual); // ИСПРАВЛЕНО
    }

    // --- Тесты репозитория (Repository) ---

    @Test
    void shouldRemoveExisting() {
        repository.removeById(1);
        Ticket[] expected = {ticket2, ticket3, ticket4, ticket5};
        assertArrayEquals(expected, repository.findAll()); // ИСПРАВЛЕНО
    }

    @Test
    void shouldNotRemoveIfIdNotFound() {
        repository.removeById(100);
        assertEquals(5, repository.findAll().length);
    }

    @Test
    void shouldHandleRemoveFromEmpty() {
        TicketRepository emptyRepo = new TicketRepository();
        emptyRepo.removeById(1);
        assertEquals(0, emptyRepo.findAll().length);
    }

    // --- Тесты данных (Ticket) для 100% покрытия ---

    @Test
    void shouldCoverAllGettersAndCompareTo() {
        // Вызываем ВСЕ 5 геттеров (для 100% линий)
        assertEquals(1, ticket1.getId());
        assertEquals(10000, ticket1.getPrice());
        assertEquals(SVO, ticket1.getFrom());
        assertEquals(LED, ticket1.getTo());
        assertEquals(90, ticket1.getTravelTime());

        // Вызов toString() для покрытия последней строки
        assertNotNull(ticket1.toString());

        // Покрываем compareTo
        assertTrue(ticket1.compareTo(ticket2) > 0);
        assertTrue(ticket2.compareTo(ticket1) < 0);
        assertEquals(0, ticket3.compareTo(ticket4));
    }

    @Test
    void exhaustiveEqualsAndHashCodeTest() {
        Ticket t = new Ticket(1, 100, "A", "B", 10);

        // Покрытие всех веток equals (null, другой класс, совпадение)
        Ticket t2 = new Ticket(1, 100, "A", "B", 10);

        assertEquals(t, t);
        assertNotEquals(t, null);
        assertNotEquals(t, repository);

        assertEquals(t, t2);
        assertEquals(t.hashCode(), t2.hashCode());

        // Покрытие ветки несовпадения по каждому полю
        assertNotEquals(t, new Ticket(2, 100, "A", "B", 10)); // id
        assertNotEquals(t, new Ticket(1, 200, "A", "B", 10)); // price
        assertNotEquals(t, new Ticket(1, 100, "C", "B", 10)); // from
        assertNotEquals(t, new Ticket(1, 100, "A", "C", 10)); // to
        assertNotEquals(t, new Ticket(1, 100, "A", "B", 20)); // travelTime
    }
}