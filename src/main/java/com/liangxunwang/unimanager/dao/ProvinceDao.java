package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("provinceDao")
public interface ProvinceDao {
    List<Province> list();
}
