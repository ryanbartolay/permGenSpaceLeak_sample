package com.ryan.runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

class ClassAInvocationHandler implements InvocationHandler {
	public ClassAInvocationHandler(ClassA a) {
		
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
}
public class ClassMetadataLeakSimulator {
	private static Map<String, ClassA> classLeakingMap = new HashMap<>();
	private static final int DEFAULT_ITERATION = 5_000_000;
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		for(int i = 0; i <= DEFAULT_ITERATION; i++) {
			String classFile = "file:" + i +".jar";
			URL[] classLoaderURL = new URL[] {
					new URL(classFile)
			};
			
			URLClassLoader newClassLoader = new URLClassLoader(classLoaderURL);
			
			ClassA t = (ClassA) Proxy.newProxyInstance(newClassLoader, 
					new Class<?>[] { ClassA.class }, 
					new ClassAInvocationHandler(new ClassAImpl()));
			
			classLeakingMap.put(classFile, t);
			Thread.sleep(1);
		}
	}
}
