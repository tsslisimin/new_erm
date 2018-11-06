/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.util;

import java.util.Arrays;

/**
 * 随机生成验证码SDK
 */
public class CaptchaUtil {

    /**
     * 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
     */
    public static enum SecurityCodeLevel {
        Simple, Medium, Hard
    };

    // 字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O)
    private static final char[] CHAR_CODE = { '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 产生默认验证码，6位低等难度<br>
     * 调用此方法和调用getSecurityCode(4, SecurityCodeLevel.Simple, false)有一样的行为
     *
     * @see #getSecurityCode(int, SecurityCodeLevel, boolean)
     * @return 验证码
     */
    public static char[] getSecurityCode() {
        return getSecurityCode(6, SecurityCodeLevel.Simple, false);
    }

    /**
     * 获取验证码，指定长度、难度、是否允许重复字符
     *
     * @param length
     *            长度
     * @param level
     *            难度
     * @param isCanRepeat
     *            是否允许重复字符
     * @return 验证码
     */
    public static char[] getSecurityCode(int length, SecurityCodeLevel level,
                                  boolean isCanRepeat) {
        // 随机抽取len个字符
        int len = length;
        char[] code;

        // 根据不同的难度截取字符数组
        switch (level) {
            case Simple: {
                code = Arrays.copyOfRange(CHAR_CODE, 0, 9);
                break;
            }
            case Medium: {
                code = Arrays.copyOfRange(CHAR_CODE, 0, 33);
                break;
            }
            case Hard: {
                code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
                break;
            }
            default: {
                code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
            }
        }

        // 字符集合长度
        int n = code.length;

        // 抛出运行时异常
        if (len > n && isCanRepeat == false) {
            throw new RuntimeException(String.format(
                    "调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，"
                            + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
                    level, isCanRepeat, n));
        }
        // 存放抽取出来的字符
        char[] result = new char[len];
        // 判断能否出现重复的字符
        if (isCanRepeat) {
            for (int i = 0; i < result.length; i++) {
                // 索引 0 and n-1
                int r = (int) (Math.random() * n);

                // 将result中的第i个元素设置为codes[r]存放的数值
                result[i] = code[r];
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                // 索引 0 and n-1
                int r = (int) (Math.random() * n);

                // 将result中的第i个元素设置为codes[r]存放的数值
                result[i] = code[r];

                // 必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。
                // 因此，这里用数组中的最后一个字符改写codes[r]，并将n减1
                code[r] = code[n - 1];
                n--;
            }
        }
        return result;
    }

}
