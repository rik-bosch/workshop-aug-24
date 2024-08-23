package models;

import java.io.PrintStream;

public class StudentList {
    private Student head;
    private Student tail;

    public StudentList() {
        head = new Student();
        tail = head;
    }

    static public StudentList mock() {
        var list = new StudentList();
        list.add(new Student(1, "Piet", 12, Education.VMBO_T));
        list.add(new Student(2, "Klaas", 16, Education.VMBO_T));
        list.add(new Student(3, "Henk", 14, Education.HAVO));
        list.add(new Student(4, "Joep", 17, Education.HAVO));
        list.add(new Student(5, "Frits", 13, Education.VWO));
        list.add(new Student(6, "Dirk", 18, Education.VWO));
        return list;
    }

    public Student first() {
        return head.getNext();
    }

    public void add(Student student) {
        tail.setNext(student);
        tail = student;
    }

    public void writeJson(PrintStream jsonOutput) {
        jsonOutput.print('[');
        for (var student = head.getNext(); student != null; student = student.getNext()) {
            student.writeJson(jsonOutput);
            if (student.getNext() != null) {
                jsonOutput.print(',');
            }
        }
        jsonOutput.print(']');
    }
}
