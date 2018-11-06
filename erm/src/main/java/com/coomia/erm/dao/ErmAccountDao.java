package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmAccountEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author hcqi .
 * @since 2018/10/24
 * describe:
 * email:hechuanqi.top@gmail.com
 */
@Mapper
public interface ErmAccountDao extends BaseDao<ErmAccountEntity> {

    List<ErmAccountEntity> selectByLimit(Map<String, Object> par);

    @Select("select * from tb_erm_account ")
    @ResultType(Map.class)
    List<Map<String, Object>> selectAll();

    int listOutsideCount(Map<String, Object> params);

}
