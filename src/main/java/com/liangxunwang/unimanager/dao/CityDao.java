package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.City;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("cityDao")
public interface CityDao {
    List<City> list(Map<String, Object> map);
}
