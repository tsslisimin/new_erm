package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmFundedV2Entity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.coomia.erm.constants.DictConstants.FUNDED_TYPE_MAP;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErmFundedDaoTest {
    @Autowired
    ErmFundedDao ermFundedDao;

    @Test
    public void queryFundListV2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 10);
        map.put("fId", 39);
        List<Map<String, Object>> maps = ermFundedDao.queryFundMember(map);
        System.out.println(maps);

        List<ErmFundedV2Entity> v2 = ermFundedDao.queryFundListV2(map);


        System.out.println(v2);
    }
}