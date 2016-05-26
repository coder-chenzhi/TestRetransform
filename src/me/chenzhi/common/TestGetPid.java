package me.chenzhi.common;

import java.lang.management.ManagementFactory;

public class TestGetPid {
    public static void main(String[] args) {
	String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	System.out.println(pid);
    }
}
