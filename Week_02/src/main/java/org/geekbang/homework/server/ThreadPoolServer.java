package org.geekbang.homework.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池服务
 */
public class ThreadPoolServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8082);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                executorService.execute(() -> service(socket));
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
            printWriter.println("Hello.ThreadPool");
            printWriter.close();
            socket.close();
            System.out.println(ThreadPoolServer.class.getSimpleName());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}