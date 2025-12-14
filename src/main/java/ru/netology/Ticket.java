package ru.netology;

import java.util.Objects;

public class Ticket implements Comparable<Ticket> {
    private int id;
    private int price;
    private String from;
    private String to;
    private int travelTime; // Время в пути (в минутах)

    // Конструктор
    public Ticket(int id, int price, String from, String to, int travelTime) {
        this.id = id;
        this.price = price;
        this.from = from;
        this.to = to;
        this.travelTime = travelTime;
    }

    // --- ГЕТТЕРЫ (ОБЯЗАТЕЛЬНЫ ДЛЯ 100% ПОКРЫТИЯ ЛИНИЙ) ---

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    // КРИТИЧЕСКИ ВАЖНЫЙ ГЕТТЕР, который отсутствовал и вызывал ошибку компиляции
    public int getTravelTime() {
        return travelTime;
    }

    // --- Методы для сортировки и сравнения ---

    @Override
    public int compareTo(Ticket other) {
        // Сортировка по цене (по возрастанию)
        return this.price - other.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && price == ticket.price && travelTime == ticket.travelTime && Objects.equals(from, ticket.from) && Objects.equals(to, ticket.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, from, to, travelTime);
    }

    // Вспомогательный метод для отладки
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", travelTime=" + travelTime +
                '}';
    }
}