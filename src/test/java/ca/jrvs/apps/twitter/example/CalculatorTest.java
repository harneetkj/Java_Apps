package ca.jrvs.apps.twitter.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void CalculatorTest() {
        calculator = new Calculator();
    }

    @Test
    public void evaluate_first() {

        int sum = calculator.evaluate("1+2");
    }
@Test
    public void evaluate_second(){
            int sum = calculator.evaluate("1.1+2");
        }



}