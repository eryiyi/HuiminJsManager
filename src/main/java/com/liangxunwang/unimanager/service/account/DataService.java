package com.liangxunwang.unimanager.service.account;


import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhl on 2015/6/11.
 */
@Service("dataService")
public class DataService implements SaveService {

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    public Object save(Object object) throws ServiceException {
        List<EmpKu> dataList = new ArrayList<EmpKu>();
        String filePath = (String) object;
        DataFormatter formatter = new DataFormatter();
        String account = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                EmpKu data = new EmpKu();
                Row row = rowIterator.next();
                if (row.getRowNum()>0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                data.setNickname(cell.getStringCellValue().replace(" ", ""));
                                break;
                            case 1:
                                data.setMobile(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                        }
                    }
                    data.setEmpkuid(UUIDFactory.random());
                    data.setDateline(System.currentTimeMillis()+"");
                    dataList.add(data);
                }
            }
            empKuDao.saveList(dataList);
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            throw new ServiceException("SAVE_ERROR");
        }
        return null;
    }

}
