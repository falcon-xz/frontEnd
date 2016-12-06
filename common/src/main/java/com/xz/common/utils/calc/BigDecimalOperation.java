package com.xz.common.utils.calc;

import java.math.BigDecimal;

/**
 * falcon -- 2016/12/6.
 */
public class BigDecimalOperation {

    /**
     * 乘法
     * @param multiplier
     * @param multiplicand
     * @return
     */
    public static String multiply(String multiplier,String multiplicand){
        BigDecimal multiplier_bd = new BigDecimal(multiplier);
        BigDecimal multiplicand_bd = new BigDecimal(multiplicand);
        BigDecimal result_bd = multiplier_bd.multiply(multiplicand_bd);
        return result_bd.toString();
    }

    /**
     * 除法
     * @param dividend 被除数
     * @param divisor 除数
     * @return 结果保留2位小数
     */
    public static String division(String dividend,String divisor){
        BigDecimal dividend_bd = new BigDecimal(dividend);
        BigDecimal divisor_bd = new BigDecimal(divisor);
        BigDecimal result_bd = dividend_bd.divide(divisor_bd,2, BigDecimal.ROUND_HALF_UP);
        return result_bd.toString();
    }


    /**
     * 除法
     * @param dividend 被除数
     * @param divisor 除数
     * @return 结果保留2位小数
     */
    public static double divisionForLong(Long dividend,Long divisor){
        BigDecimal dividend_bd = new BigDecimal(dividend);
        BigDecimal divisor_bd = new BigDecimal(divisor);
        BigDecimal result_bd = dividend_bd.divide(divisor_bd,2, BigDecimal.ROUND_HALF_UP);
        return result_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 加法
     * @param addition 加数
     * @param augend 被加数
     * @return 结果保留2位小数
     */
    public static double add(Double addition,Double augend){
        BigDecimal addition_bd = new BigDecimal(addition);
        BigDecimal augend_bd = new BigDecimal(augend);
        BigDecimal result_bd = addition_bd.add(augend_bd);
        return result_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String add(String addition,String augend){
        BigDecimal addition_bd = new BigDecimal(addition);
        BigDecimal augend_bd = new BigDecimal(augend);
        BigDecimal result_bd = addition_bd.add(augend_bd);
        return result_bd.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }
    /**
     * 比较大小
     * @param value1 被比数
     * @param value2 比数
     * @return
     */
    public static int compare(Double value1,Float value2){
        BigDecimal value1_bd = new BigDecimal(value1);
        BigDecimal value2_bd = new BigDecimal(value2);
        return value1_bd.compareTo(value2_bd);
    }

    /**
     *  减法
     * @param minuend 被减数
     * @param subtrahend 减数
     * @return 结果保留2位小数
     */
    public static double subtraction(Double minuend,Float subtrahend){
        BigDecimal subtrahend_bd = new BigDecimal(subtrahend);
        BigDecimal minuend_bd = new BigDecimal(minuend);
        BigDecimal result_bd = minuend_bd.subtract(subtrahend_bd);
        return result_bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
