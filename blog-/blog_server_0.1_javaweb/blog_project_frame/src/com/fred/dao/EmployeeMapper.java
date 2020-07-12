package com.fred.dao;

import com.fred.bean.Employee;

/**
 * @auther fred
 * @create 2020-06-15 6:36
 */
public interface EmployeeMapper {
    public Employee getEmpById(Integer id);
}
