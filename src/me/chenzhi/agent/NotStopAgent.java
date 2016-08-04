package me.chenzhi.agent;

import java.lang.instrument.Instrumentation;

public class NotStopAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
	System.out.println("Start to loop...");
	while (true) {

	}
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
	System.out.println("Start to loop...");
	while (true) {

	}
    }
}
