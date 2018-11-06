package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmAccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErmAccountDaoTest {
    @Autowired
    ErmAccountDao ermAccountDao;

    @Test
    public void selectByLimit() {

    }

    @Test
    public void save() {
        List<ErmAccountEntity> entities = new ArrayList<>();
        ErmAccountEntity entity = new ErmAccountEntity();
        entity.setAddressArea("22333");
        entity.setArchiveName("22333");
        entity.setAge(20);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        entities.add(entity);
        ermAccountDao.saveBatch(entities);
    }
}