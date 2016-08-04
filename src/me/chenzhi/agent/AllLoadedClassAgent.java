package me.chenzhi.agent;

import java.lang.instrument.Instrumentation;

public class AllLoadedClassAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
	for (Class<?> clazz : inst.getAllLoadedClasses()) {
	    System.out.println(clazz.getName());
	}
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
	for (Class<?> clazz : inst.getAllLoadedClasses()) {
	    System.out.println(clazz.getName());
	}
    }
}
