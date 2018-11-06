package com.coomia.erm.entity;

import java.util.List;
import java.util.Map;

import static com.coomia.erm.constants.DictConstants.FUNDED_TYPE_MAP;

/**
 * @author hcqi .
 * @since 2018/10/25
 * describe:
 * email:hechuanqi.top@gmail.com
 */
public class ErmFundedV2Entity {

    private Integer id;
    private String schoolName;
    private String name;
    private Integer type;
    private String typeName;
    private Integer schFundId;
    private Integer schoolId;
    private Integer fId;
    private Integer sfId;


    private Integer totalCount;
    private Integer totalMoney;
    private Integer inCount;
    private Integer money;
    private List<Map> students;

    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
        setTypeName(FUNDED_TYPE_MAP.get(type));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSchFundId() {
        return schFundId;
    }

    public void setSchFundId(Integer schFundId) {
        this.schFundId = schFundId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getInCount() {
        return getStudents().size();
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public List<Map> getStudents() {
        return students;
    }

    public void setStudents(List<Map> students) {
        this.students = students;
        setInCount(students.size());
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
