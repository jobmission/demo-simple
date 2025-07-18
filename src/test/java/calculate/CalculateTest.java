package calculate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class CalculateTest {

    @Disabled
    @Test
    public void testCRUD() {
        try {
            // 初始化DAO
            PTagDAO pTagDAO = new PTagDAO();
            VTagDAO vTagDAO = new VTagDAO();
            PTagValueDAO pTagValueDAO = new PTagValueDAO();
            VTagValueDAO vTagValueDAO = new VTagValueDAO();

            // 添加一些PTag
            Integer ptagId1 = pTagDAO.addPTag(new PTag("temp1", "Temperature sensor 1"));
            Integer ptagId2 = pTagDAO.addPTag(new PTag("temp2", "Temperature sensor 2"));

            // 准备计算
            FormulaParser formulaParser = new FormulaParser(pTagDAO, vTagDAO, pTagValueDAO, vTagValueDAO);

            // 添加VTag前检查循环依赖
            CycleDetector checker = new CycleDetector(vTagDAO);
            String formula1 = "ptag|" + ptagId1 + " + ptag|" + ptagId2;
            if (formulaParser.checkVTagFormula(formula1) && !checker.hasCircularDependency("v_formula1", null, formula1)) {
                Integer vtagId1 = vTagDAO.addVTag(new VTag("v_formula1", formula1, "v_formula1"));
                String formula2 = "vtag|" + vtagId1 + "*2";
                if (formulaParser.checkVTagFormula(formula2) && !checker.hasCircularDependency("v_formula2", null, formula2)) {
                    Integer vtagId2 = vTagDAO.addVTag(new VTag("v_formula2", formula2, "v_formula2"));

                    String formula3 = "1.1 + abs(vtag|" + vtagId2 + " - 12)";
                    Integer vtagId3 = vTagDAO.addVTag(new VTag("v_formula3", formula3, "v_formula3"));

                    String formula4 = "1000 + max(vtag|" + vtagId2 + ", vtag|" + vtagId1 + ")";
                    Integer vtagId4 = vTagDAO.addVTag(new VTag("v_formula4", formula4, "v_formula4"));

                    String formula5 = "10000 + max(vtag|" + vtagId4 + ", vtag|" + vtagId3 + ", vtag|" + vtagId2 + ", vtag|" + vtagId1 + ")";
                    Integer vtagId5 = vTagDAO.addVTag(new VTag("v_formula5", formula5, "v_formula5"));

                    String formula6 = "100000 + min(vtag|" + vtagId5 + ", vtag|" + vtagId4 + ", vtag|" + vtagId3 + ", vtag|" + vtagId2 + ", vtag|" + vtagId1 + ")";
                    if (formulaParser.checkVTagFormula(formula6) && !checker.hasCircularDependency("v_formula6", null, formula6)) {
                        vTagDAO.addVTag(new VTag("v_formula6", formula6, "v_formula6"));
                    }

                    String formula7 = "1000000 + ifnull(ptag|1,0.9999999)";
                    if (formulaParser.checkVTagFormula(formula7) && !checker.hasCircularDependency("v_formula7", null, formula7)) {
                        vTagDAO.addVTag(new VTag("v_formula7", formula7, "v_formula7"));
                    }
                    String formula8 = "100000 + vtag|6";
                    if (formulaParser.checkVTagFormula(formula8) && !checker.hasCircularDependency("v_formula8", null, formula8)) {
                        vTagDAO.addVTag(new VTag("v_formula8", formula8, "v_formula8"));
                    } else {
                        System.err.println("公式检测失败，无法修改VTag v_formula1");
                    }

                    String formula9 = "(vtag|1+vtag|2)/(vtag|3-vtag|4)";
                    if (formulaParser.checkVTagFormula(formula9) && !checker.hasCircularDependency("v_formula9", null, formula9)) {
                        vTagDAO.addVTag(new VTag("v_formula9", formula9, "v_formula9"));
                    } else {
                        System.err.println("公式检测失败" + formula9);
                    }

                    String formula10 = "rvsd(1,300," + System.currentTimeMillis() / 1000 + ")";
                    if (formulaParser.checkVTagFormula(formula10) && !checker.hasCircularDependency("v_formula10", null, formula10)) {
                        vTagDAO.addVTag(new VTag("v_formula10", formula10, "v_formula10"));
                    } else {
                        System.err.println("公式检测失败" + formula10);
                    }
                }
            }

            Timestamp tagTime = new Timestamp(System.currentTimeMillis());
            pTagValueDAO.addTagValue(new TagData(ptagId1, tagTime, 15.5));
            pTagValueDAO.addTagValue(new TagData(ptagId2, tagTime, 45.0));


            // 计算VTag
            vTagDAO.getNotReferencedVTagIds().forEach(tagId -> {
                try {
                    Double value = formulaParser.calculateVTag(vTagDAO.getVTag(tagId), tagTime);
                    vTagValueDAO.addTagValue(new TagData(tagId, tagTime, value));
                    System.out.println("VTag ID: " + tagId + ", Value: " + value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            vTagDAO.getReferenceDepth().forEach(vTagReferenceDepth -> {
                try {
                    Double value = formulaParser.calculateVTag(vTagDAO.getVTag(vTagReferenceDepth.getTagId()), tagTime);
                    vTagValueDAO.addTagValue(new TagData(vTagReferenceDepth.getTagId(), tagTime, value));
                    System.out.println("VTag ID: " + vTagReferenceDepth.getTagId() + ", Value: " + value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

