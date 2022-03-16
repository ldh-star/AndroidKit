package com.liangguo.androidkit.string;

/**
 * 中文匹配工具
 */
public class ChineseStringMatcher {

    /**
     *
     */
    public static final char MATCH = '十';
    public static final char MISMATCH = '口';

    private static final char[] cnSignArr = "。？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
    private static final char[] enSignArr = "`!@#$%^&*()_+~-=[]\\;',./{}|:\"<>?".toCharArray();

    private String originString;
    private String matchString;

    private String orgWords;
    private String mtcWords;

    private char[] org;
    private char[] mtc;


    private int[] indicator;

    private double matchingRate;
    private double accuracyRate;
    private int absent;
    private int excess;


    public ChineseStringMatcher(String orignText, String matchText) {
        super();

        this.originString = orignText;
        this.matchString = matchText;

        //init variables
        initVariables();

        //do process
        doProcess();
    }


    private void initVariables() {
        //init char array

        orgWords = clearNonWordChar(originString);
        mtcWords = clearNonWordChar(matchString);

        org = orgWords.toCharArray();
        mtc = mtcWords.toCharArray();

        //init indicator
        int i = 0;
        indicator = new int[mtc.length]; // init with 0

    }


    public int[] getIndicator() {
        return indicator;
    }


    public double getMatchingRate() {
        return matchingRate;
    }

    public double getAccuracyRate() {
        return accuracyRate;
    }


    public int getAbsent() {
        return absent;
    }


    public int getExcess() {
        return excess;
    }

    public String getOriginString() {
        return originString;
    }


    public String getMatchString() {
        return matchString;
    }


    public char[] getOrg() {
        return org;
    }


    public char[] getMtc() {
        return mtc;
    }

    private String clearNonWordChar(String str) {
        for (int i = 0; i < cnSignArr.length; i++) {
            str = str.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            str = str.replace("" + enSignArr[i], "");
        }
        return str;
    }

    public void doProcess() {
        int index = 0;
        double matchCount = 0;
        for (int i = 0; i < mtc.length; i++) {
            for (int j = index; j < org.length; j++) {
                if (mtc[i] == org[j]) {
                    index++;
                    indicator[i] = 1;
                    matchCount++;
                    break;
                }
            }
        }

        matchingRate = matchCount / orgWords.length() * 100;
        accuracyRate = matchingRate * matchCount / mtcWords.length();

        if (orgWords.length() > matchCount) {
            absent = orgWords.length() - (int) matchCount;
        }
        if (mtcWords.length() - (int) matchCount != 0) {
            excess = mtcWords.length() - (int) matchCount;
        }

    }
}
