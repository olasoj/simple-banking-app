package com.interview.question.utils.generator;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Generator {

    public static <T> String generateId(java.util.Map<String, T> object) {
        long lastKey = 0;
        for (java.util.Map.Entry<String, T> entry : object.entrySet())
            lastKey = Long.parseLong(entry.getKey());
        return String.format("%010d", lastKey);
    }

}
