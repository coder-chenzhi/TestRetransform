package me.chenzhi.common;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class TestJavassist {
	
	public static void testCtMethod() {
		ClassPool cPool = new ClassPool(true);
		try {
			CtClass ctClazz = cPool.get("java.lang.String");
			for(CtMethod method: ctClazz.getDeclaredMethods()) {
				System.out.println(method.getName() + " " + method.getSignature());
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		testCtMethod();
	}
}
