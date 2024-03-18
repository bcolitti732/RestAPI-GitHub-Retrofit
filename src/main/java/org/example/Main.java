package org.example;

import com.vogella.java.retrofitgerrit.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        controller.start("octocat");
    }
}