package sample;

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


}
