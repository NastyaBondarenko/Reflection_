package com.bondarenko.reflection.test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reflection {

    public Object createObject(Class<?> clazz) {
        try {
            Object instance = clazz.getConstructor().newInstance();
            return instance;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException exception) {
            throw new RuntimeException("Can`t create object by class", exception);
        }
    }

    public List<String> invokeAllMethodsWithoutParameters(Object object) {
        List<String> listOfMethods = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                listOfMethods.add(method.getName());
            }
        }
        return listOfMethods;
    }

    public List<String> invokeMethodsWithSignatureContainsFinal(Object object) {
        List<String> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isFinal(method.getModifiers())) {
                list.add(method.getName());
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                if (Modifier.isFinal(parameter.getModifiers())) {
                    list.add(method.getName());
                }
            }
        }
        return list;
    }

    public List<String> invokeNotPublicMethods(Class<?> clazz) {
        List<String> list = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                list.add(method.getName());
            }
        }
        return list;
    }

    public List<String> getSuperClassesAndInterfacesOfClass(Class<?> clazz) {
        List<String> list = new ArrayList<>();

        list.add(String.valueOf(clazz.getInterfaces()));
        list.add(String.valueOf(clazz.getSuperclass()));
        return list;
    }

    public void replaceAllPrivateFieldsToNullEquivalents(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                try {
                    Class<?> type = field.getType();
                    field.setAccessible(true);

                    if (Objects.equals(byte.class, type) || Objects.equals(int.class, type) || Objects.equals(short.class, type) || Objects.equals(long.class, type)) {
                        field.set(object, (byte) 0);
                    } else if (Objects.equals(float.class, type) || Objects.equals(double.class, type)) {
                        field.setFloat(object, 0.0f);
                    } else if (Objects.equals(boolean.class, type)) {
                        field.set(object, false);
                    } else if (Objects.equals(char.class, type)) {
                        field.set(object, '\u0000');
                    } else {
                        field.set(object, null);
                    }
                    field.setAccessible(false);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException("Cant replace private fields to null values ", e);
                }
            }
        }
    }
}


