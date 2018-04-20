package com.fiveplus.dao;

import com.fiveplus.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper {
    List<Long> selectGroup();
    List<Test> findById(@Param("id") Integer id);
}
