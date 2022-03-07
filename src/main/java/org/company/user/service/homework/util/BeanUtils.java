package org.company.user.service.homework.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BeanUtils {

    public static <T,S> T copyProperties(S source, Class<T> aclass) {
        T target = org.springframework.beans.BeanUtils.instantiateClass(aclass);
        org.springframework.beans.BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T,S> List<T> copyProperties(Collection<S> source, Class<T> aclass) {
        return source.stream()
                .map(s -> copyProperties(s, aclass))
                .collect(Collectors.toList());
    }

}
