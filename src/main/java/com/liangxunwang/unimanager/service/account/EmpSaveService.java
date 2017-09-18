package com.liangxunwang.unimanager.service.account;


import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.CountDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.MinePackageDao;
import com.liangxunwang.unimanager.model.Count;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.MinePackage;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by zhl on 2015/6/11.
 */
@Service("empSaveService")
public class EmpSaveService implements SaveService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    @Autowired
    @Qualifier("minePackageDao")
    private MinePackageDao minePackageDao;

    public Object save(Object object) throws ServiceException {
        List<Emp> dataList = new ArrayList<Emp>();
        String filePath = (String) object;
        DataFormatter formatter = new DataFormatter();
        String account = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                Emp data = new Emp();
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
                            case 2:
                                data.setYqnum(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 3:
                                data.setProvinceid(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 4:
                                data.setCityid(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 5:
                                data.setAreaid(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 6:
                                data.setSign(formatter.formatCellValue(cell).replace(" ", ""));
                                break;

                        }
                    }
                    data.setEmpid(UUIDFactory.random());
                    data.setDateline(System.currentTimeMillis() + "");
                    data.setIs_use("1");
                    data.setRolestate("0");
                    data.setRzstate2("1");
                    data.setIs_manager("1");
                    data.setPassword("96e79218965eb72c92a549dd5a330112");
                    data.setCover(Constants.PHOTOURLS[new Random().nextInt(61)]);//头像

                    data.setEmp_sex("0");//默认女
                    data.setLevel_id(Constants.DEFAULT_LEVEL);//默认青铜会员
                    data.setEmp_up_mobile("10000000000");
                    String emp_number = "";
                    //生成账号相关
                    emp_number = StringUtil.getStringRandom();
                    Emp member1 = empDao.findByNumber(emp_number);
                    if(member1 != null){
                        //说明改账号存在了
                        emp_number = StringUtil.getStringRandom();
                        Emp member2 = empDao.findByNumber(emp_number);
                        if(member2 != null){
                            //说明该账号存在了
                            //todo
                        }else {
                            data.setEmp_number(emp_number);
                        }
                    }else{
                        data.setEmp_number(emp_number);
                    }

                    data.setEmp_up(Constants.MOBILE_UP_DEFAULT_id);
                    dataList.add(data);
                }
            }
            empDao.saveList(dataList);
            for(Emp emp:dataList){
                RegisterUsers users = new RegisterUsers();
                User user = new User().username(emp.getEmpid()).password("123456");
                users.add(user);
                Object result = easemobIMUsers.createNewIMUserSingle(users);
                Assert.assertNotNull(result);

                //钱包表
                MinePackage minePackage = new MinePackage();
                minePackage.setPackage_id(UUIDFactory.random());
                minePackage.setEmp_id(emp.getEmpid());
                minePackage.setPackage_money("0");
                minePackageDao.save(minePackage);
                //积分表
                Count count = new Count();
                count.setId(UUIDFactory.random());
                count.setEmpId(emp.getEmpid());
                count.setCount(String.valueOf(0));
                countDao.save(count);
            }

            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            throw new ServiceException("SAVE_ERROR");
        }
        return null;
    }

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

}
