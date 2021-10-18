package com.example.demo.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.manager.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author SongNuoHui
 * @since 2021-10-15
 */
@Service
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from tb_student ${ew.customSqlSegment} ")
    IPage<Student> te(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select({"select s_id,s_name,s_user_name,s_pass,s_sex," +
            "s_id_card,s_address,del_flag,create_time,update_time " +
            "FROM tb_student LIMIT #{startIndex},#{pageSize}"})
    List<Student> queryLimit(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
