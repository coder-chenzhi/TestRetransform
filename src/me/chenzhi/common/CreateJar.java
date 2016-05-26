package me.chenzhi.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import me.chenzhi.retransformString.AgentMain;
import me.chenzhi.retransformString.DemoTransformer;
import me.chenzhi.retransformString.ModifyMethodTest;
import me.chenzhi.retransformString.TransformerService;
import me.chenzhi.retransformString.TransformerServiceMBean;

public class CreateJar {

    /**
     * Creates the temporary agent jar file if it has not been created
     * 
     * @return The created agent file name
     * @TODO check if agent.jar exists before create.
     */
    public static String create(String[] classNames) {
	FileOutputStream fos = null;
	JarOutputStream jos = null;
	File tmpFile = null;
	try {
	    tmpFile = File.createTempFile(AgentMain.class.getName(), ".jar");
	    System.out.println("Temp File:" + tmpFile.getAbsolutePath());
	    tmpFile.deleteOnExit();
	    StringBuilder manifest = new StringBuilder();
	    manifest.append("Manifest-Version: 1.0\nAgent-Class: " + AgentMain.class.getName() + "\n");
	    manifest.append("Can-Redefine-Classes: true\n");
	    manifest.append("Can-Retransform-Classes: true\n");
	    manifest.append("Premain-Class: " + AgentMain.class.getName() + "\n");
	    ByteArrayInputStream bais = new ByteArrayInputStream(manifest.toString().getBytes());
	    Manifest mf = new Manifest(bais);
	    fos = new FileOutputStream(tmpFile, false);
	    jos = new JarOutputStream(fos, mf);
	    addClassesToJar(jos, AgentMain.class, DemoTransformer.class, ModifyMethodTest.class,
		    TransformerService.class, TransformerServiceMBean.class);
	    jos.flush();
	    jos.close();
	    fos.flush();
	    fos.close();

	} catch (Exception e) {
	    throw new RuntimeException("Failed to write Agent installer Jar", e);
	} finally {
	    if (fos != null)
		try {
		    fos.close();
		} catch (Exception e) {
		}
	}

	return (tmpFile == null ? null : tmpFile.getAbsolutePath());
    }

    /**
     * Writes the passed classes to the passed JarOutputStream
     * 
     * @param jos
     *            the JarOutputStream
     * @param clazzes
     *            The classes to write
     * @throws IOException
     *             on an IOException
     */
    protected static void addClassesToJar(JarOutputStream jos, Class<?>... clazzes) throws IOException {
	for (Class<?> clazz : clazzes) {
	    jos.putNextEntry(new ZipEntry(clazz.getName().replace('.', '/') + ".class"));
	    jos.write(getClassBytes(clazz));
	    jos.flush();
	    jos.closeEntry();
	}
    }

    /**
     * Returns the bytecode bytes for the passed class
     * 
     * @param clazz
     *            The class to get the bytecode for
     * @return a byte array of bytecode for the passed class
     */
    public static byte[] getClassBytes(Class<?> clazz) {
	InputStream is = null;
	try {
	    is = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
	    ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
	    byte[] buffer = new byte[8092];
	    int bytesRead = -1;
	    while ((bytesRead = is.read(buffer)) != -1) {
		baos.write(buffer, 0, bytesRead);
	    }
	    baos.flush();
	    return baos.toByteArray();
	} catch (Exception e) {
	    throw new RuntimeException("Failed to read class bytes for [" + clazz.getName() + "]", e);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (Exception e) {
		}
	    }
	}
    }
}
