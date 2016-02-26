// import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.*;
import java.util.*;

class FileManipulation{
  private static String filename;

  FileManipulation(String filename){
    this.filename=filename;
  }

  static void write(int position, String record){
    writeToRandomAccessFile(filename,position,record);
  }
  static String read(int position){
    return readFromRandomAccessFile(filename,position);
  }
  private static String readFromRandomAccessFile(String file, int position) {
    String record = null;
    try {
      RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
      // moves file pointer to position specified
      fileStore.seek(position);
      // reading String from RandomAccessFile
      record = fileStore.readUTF();
      fileStore.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return record;
  }

  private static void writeToRandomAccessFile(String file, int position, String record) {
    try {
      RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
      // moves file pointer to position specified
      fileStore.seek(position);
      // writing String to RandomAccessFile
      fileStore.writeUTF(record);
      fileStore.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}




//-------------DB.java----------------------------------------------------------
    // String data = "KitKat (4.4 - 4.4.2)";
    // file.write(100, data);
    // file.println("String written into RAFile from Java Program : " + data);
    // String fromFile = file.read(100);
    // file.println("String read from RAFile in Java : " + fromFile);
