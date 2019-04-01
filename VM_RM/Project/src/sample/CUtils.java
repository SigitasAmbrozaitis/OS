package sample;

import java.util.Arrays;
import java.util.Vector;

import sample.Enumerators.ERCommand;

public class CUtils {

    public static String g_allowedLetters ="ABCDEFGHIJKLMNOPRSTUVQXZY";
    public static String g_allowedNumbers = "0123456789";

    public static int StringFirstIndexOf(String str, String allowedSymbols)
    {
        for(int i=0; i<str.length(); ++i)
        {
            for(int j=0; j<allowedSymbols.length(); ++j)
            {
                if(str.charAt(i) == allowedSymbols.charAt(j))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int StringLastIndexOf(String str, String allowedSymbols)
    {
        for(int i=str.length()-1; i>=0; --i)
        {
            for(int j=allowedSymbols.length()-1; j>=0; --j)
            {
                if(str.charAt(i) == allowedSymbols.charAt(j))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean StringIsInArray(String str, String[] strData, int lenght)
    {
        if(lenght<0) return false;
        String subStr = str.substring(0, lenght);
        return Arrays.asList(strData).indexOf(subStr)!=-1;
    }

    public static String NormalizeString(String str)
    {
        if(str.length()==5) return str;
        String dummyStr = "";
        for(int i=0; i<5-str.length(); ++i)
        {
            dummyStr += "0";
        }

        return dummyStr + str;
    }






}
