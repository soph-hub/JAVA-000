package org.geekbang.homework.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程阻塞服务
 */
public class SingleThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 ok");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.println("Hello.SingleThread");
            printWriter.close();
            socket.close();
            System.out.println(SingleThreadServer.class.getSimpleName());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}