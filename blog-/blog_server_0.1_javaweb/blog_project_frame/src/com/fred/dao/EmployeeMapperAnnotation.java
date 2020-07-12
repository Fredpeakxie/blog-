package com.fred.dao;

import com.fred.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @auther fred
 * @create 2020-06-15 9:50
 */
public interface EmployeeMapperAnnotation {

    /**
     * @param id
     * @return
     */
    @Select("select * from tbl_employee where id =#{id}" )
    public Employee getEmpById(Integer id);
}
