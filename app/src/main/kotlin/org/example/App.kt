package org.example

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL11.*;


fun main(){

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
