package com.olokos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReadClass {


    private static RandomAccessFile myFile;
    private static int rwFlag;
    private static MappedByteBuffer buf;
    private static FileChannel channel;

    public static void createFile() {
        try {
            String fileName = "fileToWrite";
            myFile = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            System.err.println("There has been an error creating a file to write to!");
            e.printStackTrace();
        }

    }

    public static void setRwFlagRead() {

        rwFlag = 0;
    }

    public static void readBuffer() {
        int data1;
        int data2;
        channel = myFile.getChannel();
        try {
            buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, 20);

            if (buf.getInt() == 1) {
                data1 = buf.getInt();
                data2 = buf.getInt();
                System.out.println("The sum of previous numbers is: " + (data1 + data2));
            } else {
                System.out.println("Nothing new was written before, so we won't read it.");
            }
            buf.clear();
            buf.putInt(0);
            buf.force();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createFile();
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readBuffer();
            setRwFlagRead();
        }
    }
}
