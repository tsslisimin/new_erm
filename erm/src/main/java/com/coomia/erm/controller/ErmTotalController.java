package com.coomia.erm.controller;


import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.dto.StudentEnterDTO;
import com.coomia.erm.entity.*;
import com.coomia.erm.service.*;
import com.coomia.erm.util.*;
import com.sun.javafx.collections.MappingChange;
import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.nashorn.internal.ir.IfNode;
import org.apache.commons.collections.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/erm/api/total")
public class ErmTotalController {

    @Autowired
    private ErmStudentService ermStudentService;
    private ErmDictService dictService;
    @Autowired
    private UserService userService;
    @Autowired
    private ErmSchoolService ermSchoolService;
    @Autowired
    private ErmFundedService ermFundedService;
    @Autowired
    private ErmDictService ermDictService;

    @Autowired
    public ErmTotalController(ErmDictService dictService) {
        this.dictService = dictService;
        List<ErmDictEntity> poorDict = dictService.getDictByType("8");
        List<ErmDictEntity> zoneDict = dictService.getDictByType("21");
        poorDict.addAll(zoneDict);
        for (ErmDictEntity entity : poorDict) {
            dict.forEach((integer, s) -> {
                if (entity.getDictName().equals(s)) {
                    dict.put(integer, entity.getDictCode());
                }
            });
        }
    }

    private Map<Integer, String> dict = new HashMap<Integer, String>() {
        {
            put(25, "建档立卡");
            put(26, "农村低保");
            put(27, "残疾");
            put(28, "农村特困供养");
            put(29, "其他");
            put(30, "不困难");
            put(11, "幼儿园");
            put(12, "小学");
            put(13, "初中");
            put(14, "普高");
            put(15, "中职");
        }

    };


    @GetMapping("/list/outside")
    public R outside(@RequestParam Map<String, Object> params,int page,int limit) {
        params.put("page",page);
        params.put("limit",limit);
        List<ErmAccountEntity> list = ermSchoolService.listOutsideLimit(params);
        int i = ermSchoolService.listOutsideCount(params);
        PageUtils ret = new PageUtils(list, i, page, limit);
        return R.ok().put("page", ret);
    }

    @GetMapping("/export/outside")
    public void outsideExport(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        export(request, response, null, null, ermSchoolService.listOutside());
    }

    @PostMapping("/import/outside")
    public Object outsideImport(MultipartFile file, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        InputStream inputStream = file.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        ErmAccountEntity entity;
        List<ErmAccountEntity> list = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            for (int j = 6; j < sheet.getLastRowNum(); j++) {
                HSSFRow cells = sheet.getRow(j);
                entity = new ErmAccountEntity();
                Field[] fields = entity.getClass().getDeclaredFields();
//                int pos = 0;

                Integer zone = null;
                Integer isPoor = null;
                int index = 1;
                for (int c = 1; c < Short.valueOf(cells.getLastCellNum()).intValue(); c++) {
                    HSSFCell cell = cells.getCell(c);
                    // 处理就读阶段
                    if (c >= 11 && c <= 14) {
                        if (cell != null && !ObjectUtils.isEmpty(cell.toString())) {
                            zone = c;
                        }
                        continue;
                    }
                    //处理贫困类型
                    if (c >= 25 && c <= 29) {
                        if (cell != null && !ObjectUtils.isEmpty(cell.toString())) {
                            System.out.println(cell.toString());
                            isPoor = c;
                        }
                        continue;
                    }
                    Field field = fields[index];
                    field.setAccessible(true);
                    if (c == 15) {
                        //结束就读阶段
                        //结束 贫困类型
                        String code = cell == null || ObjectUtils.isEmpty(cell.toString()) ? dict.get(zone) : dict.get(zone == null ? c : zone);
                        field.set(entity, code);
                    } else if (c == 30) {
                        //结束 贫困类型
                        String code = cell == null || ObjectUtils.isEmpty(cell.toString()) ? dict.get(isPoor) : dict.get(isPoor == null ? c : isPoor);
                        field.set(entity, code);
                    } else {
                        field.set(entity, cell == null ? null :
                                field.getType().equals(String.class)
                                        ? cell.toString()
                                        : ObjectUtils.isEmpty(cell.toString())
                                        ? null
                                        : Integer.valueOf(cell.getCellType() == 0
                                        ? String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue())
                                        : cell.toString()));
                    }
                    index++;

                }
                list.add(entity);
            }
        }
        ermSchoolService.saveAccountBatch(list);


        String title = "导出成功人员查看";
        String filename = String.format("attachment;filename=%s.xls", title);

        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> cell;
        for (ErmAccountEntity aList : list) {
            cell = new HashMap<>();
            cell.put("name", aList.getName());
            cell.put("id", aList.getIdCard());
            data.add(cell);
        }
        ExcelUtil.downLoadExcelForMap(request, response, filename,
                data, new String[]{"name", "id"}, new String[]{"姓名", "身份证号"}, new boolean[1], title, title);

        return R.ok().put("fileName", filename);

    }

    private void isPoorProcess(HSSFCell cell, Field field) {

    }

    /**
     * x
     * 导出Excel报表
     *
     * @param request
     * @return
     */


    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {


        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        if (roleId == UserType.SCH.getCode() || roleId == UserType.OPER.getCode()) {
            params.put("schoolId", user.getSchoolId());
        }
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");

        Query query = new Query(params);
//            // 构造导出数据
        List<Map<String, Object>> dataList = ermStudentService.queryStudentsByFundIdAndSchoolIdAndPage(query);

        Integer total = ermStudentService.queryStudentsByFundIdAndSchoolIdTotal(params);
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).put("No", i + 1);
            Map<String, Object> model = dataList.get(i);
            if (model.get("gender") != null) {
                if (model.get("gender").toString().equals("1")) {
                    dataList.get(i).put("gender", "男");
                } else {
                    dataList.get(i).put("gender", "女");
                }
            } else {
                dataList.get(i).put("gender", "");
            }
            if (model.get("is_poor") != null) {
                String is_poor = dictService.getDictNameByCode(model.get("is_poor").toString());
                dataList.get(i).put("is_poor", is_poor);
            }
        }

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);

    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request,
                       HttpServletResponse response, Integer fundId, Integer schoolId, JwtAuthenticationToken token) {
        try {
            ErmFundedEntity entity = ermFundedService.queryObjectOriginal(fundId);

            String dictName = this.ermDictService.getDictNameByCode(entity.getSemester());
            String fundName = dictName + "-" + entity.getName();
//            if (null != entity.getSchzone() && entity.getSchzone().length() > 0)
//                fundName = dictName + "-" + entity.getName() + "-"
//                        + ermDictService.getDictNameByCode(entity.getSchzone());
            UserContext user = (UserContext) token.getPrincipal();
            ErmAdminEntity admin = userService.getByUser(user.getUsername());
            int roleId = admin.getRoleId();
            if (roleId == UserType.SCH.getCode() || roleId == UserType.OPER.getCode()) {
                schoolId = user.getSchoolId();
            }
            SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> params = new HashMap<String, Object>();

//            // 构造导出数据
            List<Map<String, Object>> dataList = ermStudentService.queryStudentsByFundIdAndSchoolId(fundId, schoolId);

            export(request, response, dictName, schoolId, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void export(HttpServletRequest request, HttpServletResponse response, String fundName, Integer schoolId, List<Map<String, Object>> dataList) throws Exception {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).put("No", i + 1);
            Map<String, Object> model = dataList.get(i);
            if (model.get("gender") != null) {
                if (model.get("gender").toString().equals("1")) {
                    dataList.get(i).put("gender", "男");
                } else {
                    dataList.get(i).put("gender", "女");
                }
            } else {
                dataList.get(i).put("gender", "");
            }
            if (model.get("is_poor") != null) {
                String is_poor = dictService.getDictNameByCode(model.get("is_poor").toString());
                switch (is_poor) {
                    case "建档立卡":
                        dataList.get(i).put("level1", "1");
                        break;
                    case "农村低保":
                        dataList.get(i).put("level2", "1");
                        break;
                    case "残疾":
                        dataList.get(i).put("level3", "1");
                        break;
                    case "农村特困供养":
                        dataList.get(i).put("level4", "1");
                        break;
                    case "其他":
                        dataList.get(i).put("level5", "1");
                    case "不困难":
                        dataList.get(i).put("level6", "1");
                        break;
                }
            }
            if (model.get("zone") != null) {

                switch (model.get("zone").toString()) {
                    case "学前":
                        dataList.get(i).put("zone1", "1");
                        break;
                    case "小学":
                        dataList.get(i).put("zone2", "1");
                        break;
                    case "初中":
                        dataList.get(i).put("zone3", "1");
                        break;
                    case "普高":
                        dataList.get(i).put("zone4", "1");
                        break;
                    case "中职":
                        dataList.get(i).put("zone5", "1");
                        break;
                }
            }


        }
//            String sheetName = "温湿度日记录";
//            String date = yearMonth;
        String[] head0 = new String[]{"序号", "学生姓名", "性别", "年龄", "身份证号", "家长姓名", "电话号码", "家庭人口数（人）",
                "乡镇", "学校名称", "学籍号", "就读阶段（在相应类型下画“1”）", "就读阶段（在相应类型下画“1”）", "就读阶段（在相应类型下画“1”）", "就读阶段（在相应类型下画“1”）", "就读阶段（在相应类型下画“1”）", "年级", "班次", "是否寄宿",
                "省", "市", "县", "乡镇", "村（居委会）", "组（号）",
                "建档立卡", "农村低保", "残疾学生", "农村特困供养", "其他困难", "不困难",
                "姓 名（账户名）", "账号", "身份证号", "与学生关系",
                "姓 名（账户名）", "银行账号",
                "姓名", "单位", "职务", "电话号码"
        };//在excel中的第3行每列的参数
        String[] head1 = new String[]{"学前", "小学", "初中", "普高", "中职"};//在excel中的第4行每列（合并列）的参数
        String[] headnum0 = new String[]{"4,5,0,0", "4,5,1,1", "4,5,2,2", "4,5,3,3", "4,5,4,4", "4,5,5,5", "4,5,6,6", "4,5,7,7",
                "4,5,8,8", "4,5,9,9", "4,5,10,10", "4,4,11,15", "4,5,16,16", "4,5,17,17", "4,5,18,18"
                , "4,5,19,19", "4,5,20,20", "4,5,21,21", "4,5,22,22", "4,5,23,23", "4,5,24,24",
                "4,5,25,25", "4,5,26,26", "4,5,27,27", "4,5,28,28", "4,5,29,29",
                "4,5,30,30", "4,5,31,31", "4,5,32,32", "4,5,33,33", "4,5,34,34",
                "4,5,35,35", "4,5,36,36",
                "4,5,37,37", "4,5,38,38", "4,5,39,39", "4,5,40,40", "4,5,41,41", "4,5,42,42"
        };//对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}
        String[] headnum1 = new String[]{"5,5,11,11", "5,5,12,12", "5,5,13,13",
                "5,5,14,14", "5,5,15,15"};
        String[] colName = new String[]{"No", "name", "gender", "age", "idcard", "parent_name", "telphone", "familyNum",
                "study_place", "school", "stuno", "zone1", "zone2", "zone3", "zone4", "zone5", "grade", "clazz", "lodging",
                "addressProvince", "addressCity", "addressArea", "addressTown", "addressTownship", "addressGroup",
                "level1", "level2", "level3", "level4", "level5", "level6",
                "archive_name", "archiveAcount", "archive_idcard", "archiveRelation",
                "supportName", "supportBankCard",
                "helper", "helperWorkPlace", "helperPosition", "helperTel"
        };//需要显示在excel中的参数对应的值，因为是用map存的，放的都是对应的key

        String schoolname = "";
        if (schoolId != null) {
            ErmSchoolEntity ermSchoolEntity = ermSchoolService.queryObject(schoolId);
            if (ermSchoolEntity != null) {
                schoolname = ermSchoolEntity.getName();
            }

        }

        String sheetName = String.format("慈利县%s学期本乡镇（或本校）内就读学生信息台账", fundName);
        ReportMergeXls.reportMergeXls(request, response, dataList, sheetName, head0,
                headnum0, head1, headnum1, colName, schoolname); //utils类需要用到的参数
    }
}
