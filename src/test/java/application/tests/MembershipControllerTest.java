package application.tests;

import application.controllers.MembershipController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MembershipControllerTest {
    private final double STUDENT_DISCOUNT = 0.8;
    private MembershipController myClass;
    Method calculateSuggestedPrice;
    @BeforeEach
    void setUp() throws NoSuchMethodException {
        myClass = new MembershipController();
        calculateSuggestedPrice = MembershipController.class.getDeclaredMethod("calculateSuggestedPrice", long.class, Boolean.class);
        calculateSuggestedPrice.setAccessible(true);
    }

    @Test
    public void testPriceSuggestionStudent() throws  InvocationTargetException, IllegalAccessException {
        double result = (double) calculateSuggestedPrice.invoke(myClass, 10, true);

        assertEquals(25 * STUDENT_DISCOUNT, result);
        assertNotEquals(25, result);
    }

    @Test
    public void testPriceSuggestionRegular() throws  InvocationTargetException, IllegalAccessException{
        double result = (double) calculateSuggestedPrice.invoke(myClass, 20, false);

        assertEquals(50, result);
        assertNotEquals(50 * STUDENT_DISCOUNT, result);
    }
}