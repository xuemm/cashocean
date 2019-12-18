package com.jike.cashocean.util;

public class MoneyUtils {
    /**
     * 为字符串从后往前，没三位加一个点
     * 金额数规范显示
     *
     * @param money 金额数
     * @return
     */
    public static String fomatMoney(String money) {
        StringBuilder str = new StringBuilder();
        str.append(money);
        int last = str.length();
        for (int i = last - 3; i > 0; i -= 3) {
            str.insert(i, ',');
        }
        return str.toString();
    }
}
