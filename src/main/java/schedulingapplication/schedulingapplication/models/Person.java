package schedulingapplication.schedulingapplication.models;

public class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(this.getId());
        sb.append("\nName: ");
        sb.append(this.getName());
        return String.valueOf(sb);
    }
}