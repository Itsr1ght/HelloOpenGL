package org.example

import java.nio.file.Files
import java.nio.file.StandardCopyOption

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL11.*;

fun nativeLib(name: String): String {
    val os = System.getProperty("os.name").lowercase()

    return when {
        os.contains("win") -> "$name.dll"
        os.contains("mac") -> "lib$name.dylib"
        else -> "lib$name.so"
    }
}

fun extractNative(name: String, dir: java.nio.file.Path) {
    val fileName = nativeLib(name)
    val stream = object {}.javaClass.getResourceAsStream("/natives/$fileName")
        ?: error("Missing native resource: $name")

    val target = dir.resolve(fileName)

    stream.use {
        Files.copy(it, target, StandardCopyOption.REPLACE_EXISTING)
    }
}


fun main(){
    val tempDir = Files.createTempDirectory("lwjgl-natives")

    extractNative("lwjgl", tempDir)
    extractNative("glfw", tempDir)
    extractNative("lwjgl_opengl", tempDir)

    System.setProperty("org.lwjgl.librarypath", tempDir.toString())

    if (!glfwInit()) throw IllegalStateException("Unable to initialize GLFW");

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    var win = glfwCreateWindow(300, 300, "Hello World", 0, 0);

    glfwMakeContextCurrent(win);

    GL.createCapabilities();

    var buff = GL15.glGenBuffers();
    GL15.glBindBuffer(0, buff);


    glfwShowWindow(win)

    while (!glfwWindowShouldClose(win)) {

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT);
        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);

        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    
    glfwDestroyWindow(win);
    glfwTerminate();
}
