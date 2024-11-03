package br.com.fantasticpalmtree.model;

public class Client {
    static private long idCounter = 0;

    private long id = idCounter++;
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
