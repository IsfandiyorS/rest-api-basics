package com.epam.esm.utils;

import com.epam.esm.entity.Identifiable;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class BaseUtils {

    public boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isEmpty(List<?> items) {
        return items == null || items.isEmpty();
    }

    public boolean isEmpty(Object l) {
        return l == null;
    }

//    public static void error(Logger log, Exception e) {
//        if (e.getCause() == null)
//            log.error(e.getStackTrace(), e);
//        else
//            log.error(e.getStackTrace(), e.getCause());
//    }

    public boolean isStringLengthValid(String field, int minLength, int maxLength){
        return field.length()>=minLength && field.length()<=maxLength;
    }

    public String toErrorParams(Object... args) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(args).forEach(t -> builder.append("#").append(toStringErrorParam(t)));
        return builder.toString().substring(1);
    }

    private String toStringErrorParam(Object argument) {
        if (argument instanceof Identifiable) {
            return argument.getClass().getSimpleName();
        }
        return argument.toString();
    }

}
