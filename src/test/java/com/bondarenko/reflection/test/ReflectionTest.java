package com.bondarenko.reflection.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTest {
    Reflection reflection = new Reflection();
    ClassForTestMethods classForTest = new ClassForTestMethods();

    @Test
    @DisplayName("test Create Object")
    public void testCreateObject() {
        assertEquals(ClassForTestMethods.class, reflection.createObject(ClassForTestMethods.class).getClass());
    }

    @Test
    @DisplayName("Invoke Methods Without Parameters")
    public void invokeMethodsWithoutParameters() {

        List<String> listOfMethods = reflection.invokeAllMethodsWithoutParameters(classForTest);

        assertTrue(listOfMethods.contains("method1"));
        assertFalse(listOfMethods.contains("method2"));
        assertEquals(2, listOfMethods.size());
    }

    @Test
    @DisplayName("Invoke Methods With Signature Contains Final")
    public void invokeMethodsWithSignatureContainsFinal() {

        List<String> list = reflection.invokeMethodsWithSignatureContainsFinal(classForTest);

        assertTrue(list.contains("method3"));
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("invoke Not Public Methods")
    public void invokeNotPublicMethods() {

        List<?> list = reflection.invokeNotPublicMethods(ClassForTestMethods.class);

        assertTrue(list.contains("method1"));
        assertTrue(list.contains("method2"));
        assertTrue(list.contains("method3"));
        assertFalse(list.contains("method4"));

        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("get Super Classes And Interfaces OfClass")
    public void getSuperClassesAndInterfacesOfClass() {

        List<?> list = reflection.getSuperClassesAndInterfacesOfClass(ClassForTestMethods.class);

        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("replace All Private Fields To Null Equivalents")
    public void replaceAllPrivateFieldsToNullEquivalents() throws ReflectiveOperationException {

        ClassForTestPrivateFields classForTest = new ClassForTestPrivateFields();

        reflection.replaceAllPrivateFieldsToNullEquivalents(classForTest);

        assertEquals(10, classForTest.getPublicByte());
        assertEquals(0, classForTest.getPrivateByte());
        assertEquals(0, classForTest.getPrivateInt());
        assertEquals(10, classForTest.getPublicInt());
        assertEquals(0, classForTest.getPrivateShort());
        assertEquals(10, classForTest.getPublicShort());
        assertEquals(0, classForTest.getPrivateLong());
        assertEquals(10, classForTest.getPublicLong());
        assertEquals(0.0, classForTest.getPrivateDouble(), 0);
        assertEquals(10.0, classForTest.getPublicDouble(), 0);
        assertEquals(0.0, classForTest.getPrivateFloat(), 0);
        assertEquals(10.0, classForTest.getPublicFloat(), 0);
        assertEquals(0, classForTest.getPrivateChar());
        assertEquals('a', classForTest.getPublicChar());
        assertFalse(classForTest.getPrivateBoolean());
        assertTrue(classForTest.getPublicBoolean());
        assertNull(classForTest.getPrivateString());
        assertEquals("Hello!", classForTest.getPublicString());
    }
}
