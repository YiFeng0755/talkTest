package com.youme.talktest;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

public class write {
    public static void main (String args []) throws IOException{
        String filePath = "C:\\Users\\aa\\Documents\\WXWork\\1688853255353881\\Cache\\File\\2018-10\\新建 Microsoft Excel 工作表.xlsx";
        for (int i =0;i<5;i++){
            Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
            Sheet sheet=wb.getSheetAt(0);
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(i);
            String conTent = cell.getStringCellValue();
            System.out.println("修改前:"+conTent);
            cell.setCellValue("y");
            String conTent1 = cell.getStringCellValue();
            System.out.println("修改后:"+conTent1);
            FileOutputStream out = new FileOutputStream(filePath);
            wb.write(out);
        }
//        Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
//        Sheet sheet=wb.getSheetAt(0);
//        Row row = sheet.getRow(3);
//        Cell cell = row.getCell(5);
//        String conTent = cell.getStringCellValue();
//        System.out.println("修改前:"+conTent);
//        cell.setCellValue("y");
//        String conTent1 = cell.getStringCellValue();
//        System.out.println("修改后:"+conTent1);
//        FileOutputStream out = new FileOutputStream(filePath);
//        wb.write(out);

    }
}


//package com.youme.talktest;
//
//import java.io.IOException;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import java.io.RandomAccessFile;
//import java.nio.channels.FileChannel;
//import java.nio.channels.FileLock;
//import java.util.Date;
//
//class excel1 implements Runnable{
//    private String filePath;
//    private int sheetIndex;
//    private int rowNum;
//    private int cellNum;
//    private String value;
//    public excel1(String filePath, int sheetIndex, int rowNum, int cellNum, String value){
//        this.filePath = filePath;
//        this.sheetIndex = sheetIndex;
//        this.rowNum = rowNum;
//        this.cellNum = cellNum;
//        this.value = value;
//    }
//    public void alterCell (){
//        try{
//            Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
//            Sheet sheet=wb.getSheetAt(sheetIndex);
//            Row row = sheet.getRow(rowNum);
//            Cell cell = row.getCell(cellNum);
//            String conTent = cell.getStringCellValue();
//            System.out.println("修改前:"+rowNum+","+cellNum+" "+conTent+" "+Thread.currentThread().getName());
//            FileOutputStream out = new FileOutputStream(filePath);
//            FileChannel fc=out.getChannel();
//            FileLock fl=null;
//            while(true){
//                try {
//                    fl = fc.tryLock();
//                    System.out.println("没有有其他线程正在操作该文件，获得锁"+Thread.currentThread().getName());
//                    cell.setCellValue(value);
//                    String conTent1 = cell.getStringCellValue();
//                    System.out.println("修改后:"+rowNum+","+cellNum+" "+conTent1+" "+Thread.currentThread().getName());
//                    wb.write(out);
////            out.close();
//                    fl.release();
//                    break;
//                } catch (Exception e) {
//                    System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒"+Thread.currentThread().getName());
//                    try{
//                        Thread.sleep(1000);
//                    }catch (Exception e1){
//                        e1.printStackTrace();
//                    }
//                }
//            }
//
//
//        }catch (IOException e){
//            e.printStackTrace();
//
//        }
//    }
//
//    public void run(){
//        System.out.println("this thread name is:"+Thread.currentThread().getName());
//        alterCell();
//
//    }
//}
//
//public class excel {
//    public static void main (String arg []){
//        String filePath = "C:\\Users\\aa\\Documents\\WXWork\\1688853255353881\\Cache\\File\\2018-10\\新建 Microsoft Excel 工作表.xlsx";
////        excel1 r1 = new excel1(filePath,0,0,0,"y");
////        Thread t1 = new Thread(r1);
////        t1.start();
//        new Thread(new excel1(filePath,0,0,0,"y")).start();
//        new Thread(new excel1(filePath,0,1,1,"y")).start();
//        new Thread(new excel1(filePath,0,2,2,"y")).start();
//        new Thread(new excel1(filePath,0,3,3,"y")).start();
//        new Thread(new excel1(filePath,0,4,4,"y")).start();
////        for (int i =0;i<5;i++){
////            excel1 r1 = new excel1(filePath,0,i,i,"y");
////            Thread t1 = new Thread(r1);
////            t1.start();
////        }
//
//    }
//}