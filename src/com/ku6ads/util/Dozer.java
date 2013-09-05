package com.ku6ads.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;

public class Dozer {
	
private static final Dozer instance = new Dozer();
	
	DozerBeanMapper mapper;
	
	@SuppressWarnings("unchecked")
	private Dozer(){
		mapper = new DozerBeanMapper();
		List s = new ArrayList();
		s.add("dozerBeanMapping.xml");
		mapper.setMappingFiles(s);
	}
	
	public static Dozer getInstance(){
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public Object mapper(Object obj, Class c){
		return mapper.map(obj, c);
	}
	
	public void mapper(Object obj, Object obj2){
		mapper.map(obj, obj2);
	}
}
