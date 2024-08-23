package models;

import java.io.PrintStream;

public class Student {
    private int id;
    private String name;
    private int age;
    private Education education;
    private Student next;

    public Student() {}
    public Student(int id, String name, int age, Education education) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.education = education;
    }

    public void writeJson(PrintStream stream) {
        stream.print('{');
        stream.printf("\"id\":%d,", id);
        stream.printf("\"name\":\"%s\",", name);
        stream.printf("\"age\":%d,", age);
        stream.printf("\"education\":\"%s\"", education.toString());
        stream.print('}');
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Student getNext() {
        return next;
    }

    public void setNext(Student next) {
        this.next = next;
    }
}
