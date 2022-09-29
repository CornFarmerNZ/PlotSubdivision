/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plotsubdivision;

import java.util.Random;

/**
 *
 * @author tim
 */
public class IdGenerator {

    Random random = new Random();
    private int counter;
    private static IdGenerator generator = null;

    private IdGenerator() {
        this.counter = 1;
    }

    public synchronized int getID() {
        return this.counter++;
    }

    public static IdGenerator getIdGenerator() {
        if (generator == null) {
            generator = new IdGenerator();
        }
        return generator;
    }

}
