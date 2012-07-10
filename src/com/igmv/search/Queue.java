package com.igmv.search;

import java.util.List;

public interface Queue<T> {
	void add(T anItem);

	void add(List<T> items);

	T remove();

	T get();
}