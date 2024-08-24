package input.parser;

import input.parser.token.EofToken;
import input.parser.token.NumberToken;
import input.parser.token.StringToken;
import input.parser.token.SymbolToken;
import models.Education;
import models.Student;
import models.StudentList;

import java.text.ParseException;

public class StudentParser {
    private final JsonLex lexer;

    public StudentParser(JsonLex lexer) {
        this.lexer = lexer;
    }

    /*
    json
        : studentArr EOF                        { return p1 }
        ;
    */
    public StudentList parseJson() throws ParseException {
        var list = getStudentArr();
        if (list == null) {
            throw error("Expected student list");
        }
        expectEof();
        return list;
    }

    /*
    studentArr                                  { var studentList = new StudentList() }
        : '[' student (',' student)* ']'        { studentList.add(p2); studentList.add(p4); return studentList }
        | '[' ']'                               { return studentList }
        ;
    */
    private StudentList getStudentArr() throws ParseException {
        if (checkSymbol('[')) {
            var studentList = new StudentList();
            var student = getStudent();
            if (student != null) {
                studentList.add(student);
                while (checkSymbol(',')) {
                    student = getStudent();
                    if (student == null) {
                        throw error("Expected student object");
                    }
                    studentList.add(student);
                }
            }
            expectSymbol(']');
            return studentList;
        }
        else {
            return null;
        }
    }

    /*
    student                             { var student = new Student() }
        : '{' pair (',' pair)* '}'      { return student }
        | '{' '}'                       { return student }
        ;
    */
    private Student getStudent() throws ParseException {
        if (checkSymbol('{')) {
            var student = new Student();
            if (readPair(student)) {
                while (checkSymbol(',')) {
                    if (!readPair(student)) {
                        throw error("Expected object field");
                    }
                }
            }
            expectSymbol('}');
            return student;
        }
        else {
            return null;
        }
    }

    /*
    pair
        : "id" ':' NUMBER               { student.setId(p3) }
        | "name" ':' STRING             { student.setName(p3) }
        | "age" ':' NUMBER              { student.setAge(p3) }
        | "education" ':' education     { student.setEducation(p3) }
        ;
    */
    private boolean readPair(Student student) throws ParseException {
        var token = lexer.getToken();
        if (token instanceof StringToken stringToken) {
            expectSymbol(':');

            switch (stringToken.value()) {
                case "id":
                    var id = getNumber();
                    student.setId(id);
                    break;

                case "name":
                    var name = getString();
                    student.setName(name);
                    break;

                case "age":
                    var age = getNumber();
                    student.setAge(age);
                    break;

                case "education":
                    var education = getEducation();
                    student.setEducation(education);
                    break;

                default:
                    throw error("Unknown field " + stringToken.value());
            }
            return true;
        }
        else {
            lexer.putBack(token);
            return false;
        }
    }

    /*
    education
        : "VMBO_T"      { return Education.VMBO_T }
        | "HAVO"        { return Education.HAVO }
        | "VWO"         { return Education.VWO }
        ;
     */
    private Education getEducation() throws ParseException {
        var token = lexer.getToken();
        if (token instanceof StringToken stringToken) {
            return switch (stringToken.value()) {
                case "VMBO_T" -> Education.VMBO_T;
                case "HAVO" -> Education.HAVO;
                case "VWO" -> Education.VWO;
                default -> throw error("Unknown education");
            };
        }
        else {
            throw error("Expected education string");
        }
    }

    private boolean checkSymbol(char symbol) {
        var token = lexer.getToken();
        if (token instanceof SymbolToken symbolToken && symbolToken.value() == symbol) {
            return true;
        }
        else {
            lexer.putBack(token);
            return false;
        }
    }

    private void expectSymbol(char symbol) throws ParseException {
        var token = lexer.getToken();
        if (!(token instanceof SymbolToken symbolToken && symbolToken.value() == symbol)) {
            throw error("Expected '%c' symbol".formatted(symbol));
        }
    }

    private int getNumber() throws ParseException {
        var token = lexer.getToken();
        if (token instanceof NumberToken numberToken) {
            return numberToken.value();
        }
        else {
            throw error("Expected number");
        }
    }

    private String getString() throws ParseException {
        var token = lexer.getToken();
        if (token instanceof StringToken stringToken) {
            return stringToken.value();
        }
        else {
            throw error("Expected string");
        }
    }

    private void expectEof() throws ParseException {
        var token = lexer.getToken();
        if (!(token instanceof EofToken)) {
            throw error("Expected EOF");
        }
    }

    private ParseException error(String msg)  {
        return new ParseException(msg, lexer.getOffset());
    }
}
