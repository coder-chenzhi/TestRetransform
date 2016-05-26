package me.chenzhi.common;

import java.lang.management.ManagementFactory;

import com.sun.tools.attach.VirtualMachine;

public class AttachJVM {
    public static void main(String[] args) {
	try {
	    String agentPath = "AllLoadedClassAgent.jar";
	    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	    // Attach (to ourselves)
	    VirtualMachine vm = VirtualMachine.attach(pid);
	    vm.loadAgent(agentPath);
	} catch (Exception e) {
	    System.err.println("Agent Installation Failed. Stack trace follows...");
	    e.printStackTrace(System.err);
	}
    }
}
