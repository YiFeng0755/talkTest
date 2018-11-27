package com.youme.talktest;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HandleExcel {
    public void alterCell (String filePath, int sheetIndex, int rowNum, int cellNum, String value) throws IOException{
        Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);
        String conTent = cell.getStringCellValue();
        System.out.println("修改前:" + rowNum + "," + cellNum + " " + conTent + " " + Thread.currentThread().getName());
        FileOutputStream out = new FileOutputStream(filePath);
        cell.setCellValue(value);
        String conTent1 = cell.getStringCellValue();
        System.out.println("修改后:"+rowNum+","+cellNum+" "+conTent1+" "+Thread.currentThread().getName());
        wb.write(out);
    }
}

//public class Excel {
//    public static void main(String arg[]) throws IOException{
//        String filePath = "C:\\Users\\aa\\Documents\\WXWork\\1688853255353881\\Cache\\File\\2018-10\\新建 Microsoft Excel 工作表.xlsx";
//        HandleExcel xsl =new HandleExcel();
//        for(int i=0;i<5;i++){
//            xsl.alterCell(filePath,0,i,i,"y");
//        }
//    }
//}