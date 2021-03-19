package com.samet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class WriteClass {


    private static RandomAccessFile myFile;
    private static int rwFlag;
    private static MappedByteBuffer buf;
    private static FileChannel channel;

    public static void createFile() {
        try {
            String fileName = "fileToWrite";
            myFile = new RandomAccessFile(fileName, "rw");

          /*  try {
                channel = myFile.getChannel();
                buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            buf.putInt(0);
            buf.force();*/
        } catch (FileNotFoundException e) {
            System.err.println("There has been an error creating a file to write to!");
            e.printStackTrace();
        }

    }

    public static void writeToFile(int data, int data2) {
        channel = myFile.getChannel();
        try {
            buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
            //buf.putInt(0);
            buf.force().rewind();
            if (buf.getInt() == 0) {
                buf.clear();
                buf.putInt(1);
                buf.putInt(data);
                buf.putInt(data2);
                buf.force();
                //buf.clear();
                System.out.println("First number is: " + data + " second is: " + data2 + " sum is: " + (data + data2) + "\n The file has been updated and you can now read it! ");
            } else {
                System.out.println("Data is already written, but it has not been read!");
            }
        } catch (IOException e) {
            System.err.println("There has been an error writing to the file!");
            e.printStackTrace();
        }
    }

    public static int getRandomData() {
        return (int) (Math.random() * 1000) + 1;
    }

    public static void setRwFlagWrite() {
        rwFlag = 1;
    }

    public static void main(String[] args) {
        createFile();
        while (true) {
            setRwFlagWrite();
            writeToFile(getRandomData(), getRandomData());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
