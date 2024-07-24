package com.svb.sailpoint.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils extends org.springframework.util.CollectionUtils{
	
	
	public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
	    return Optional.ofNullable(list)
	            .orElseGet(ArrayList::new)
	            .stream()
	            .collect(Collectors.groupingBy(keyFunction));
	}

}
