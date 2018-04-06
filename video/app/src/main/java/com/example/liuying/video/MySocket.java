package com.example.liuying.video;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MySocket {
    private String ipAdd = "192.168.43.171";
    private int port = 8888;
    private Socket socket;
    //private ExecutorService mThreadPool;
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;
    OutputStream outputStream;
    private Handler mMainHandler;
    private ExecutorService mThreadPool;
    String receive_message;

    MySocket(){
        mThreadPool = Executors.newCachedThreadPool();
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        receive_message= response;
                        break;
                }
            }
        };
    }



    public void connect() {

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                    socket = new Socket(ipAdd, port);
                    // 判断客户端和服务器是否连接成功
                    System.out.println(socket.isConnected());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    public void forSendMessage(final String stmp) {
        if(socket.isConnected()) {
            // 利用线程池直接开启一个线程 & 执行该线程
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 步骤1：从Socket 获得输出流对象OutputStream
                        // 该对象作用：发送数据
                        outputStream = socket.getOutputStream();

                        // 步骤2：写入需要发送的数据到输出流对象中
                        outputStream.write((stmp).getBytes("utf-8"));
                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                        // 步骤3：发送数据到服务端
                        outputStream.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public void disconnect(){
        if(socket.isConnected()){

                try {
                    // 断开 客户端发送到服务器 的连接，即关闭输出流对象OutputStream
                    outputStream.close();

                    // 断开 服务器发送到客户端 的连接，即关闭输入流读取器对象BufferedReader
                    br.close();

                    // 最终关闭整个Socket连接
                    socket.close();

                    // 判断客户端和服务器是否已经断开连接
                    System.out.println(socket.isConnected());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
    }




}
