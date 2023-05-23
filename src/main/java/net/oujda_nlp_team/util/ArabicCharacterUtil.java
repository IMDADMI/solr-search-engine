package net.oujda_nlp_team.util;
/*============================================================================*/
/**
 * 
 * ADAT : AlKhalil for Disambiguation of Arabic Texts
 * © 2018
 * @author Mohamed BOUDCHICHE
 * @email moha.boudchiche@gmail.com
 * 
 */
/*============================================================================*/
public class ArabicCharacterUtil {
/*============================================================================*/
    public static String[] getSeparator(){
        String[] Str = {"\\.", "!", ":", ""+ArabicCharacter.COMMA, ""+ArabicCharacter.QUESTION_MARK, ""+ArabicCharacter.SEMICOLON};
        return Str;
    }
/*============================================================================*/
/**
 * <p>Returns a character in the Buckwalter transliteration system from a character in arabic</p>
 * @param c the arabic character.
 * @return the romanized character. 
 */
    public static char getBuckWalterCharacter(char c) {        
        switch (c) {
            case ArabicCharacter.HAMZA : return '\''; // \u0621 : ء
            case ArabicCharacter.ALEF_MADDA : return '|'; // \u0622 : آ
            case ArabicCharacter.ALEF_HAMZA_ABOVE : return '>'; // \u0623 : أ
            case ArabicCharacter.WAW_HAMZA : return '&'; // \u0624 : ؤ
            case ArabicCharacter.ALEF_HAMZA_BELOW : return '<'; // \u0625 : إ
            case ArabicCharacter.YEH_HAMZA : return '}'; // \u0626 : ئ
            case ArabicCharacter.ALEF : return 'A'; // \u0627 : ا
            case ArabicCharacter.BEH : return 'b'; // \u0628 : ب
            case ArabicCharacter.TEH_MARBUTA : return 'p'; // \u0629 : ة
            case ArabicCharacter.TEH : return 't'; // \u062A : ت
            case ArabicCharacter.THEH : return 'v'; // \u062B : ث
            case ArabicCharacter.JEEM : return 'j'; // \u062C : ج
            case ArabicCharacter.HAH : return 'H'; // \u062D : ح
            case ArabicCharacter.KHAH : return 'x'; // \u062E : خ
            case ArabicCharacter.DAL : return 'd'; // \u062F : د
            case ArabicCharacter.THAL : return '*'; // \u0630 : ذ
            case ArabicCharacter.REH: return 'r'; // \u0631 : ر
            case ArabicCharacter.ZAIN : return 'z'; // \u0632 : ز
            case ArabicCharacter.SEEN : return 's'; // \u0633 : س
            case ArabicCharacter.SHEEN : return '$'; // \u0634 : ش
            case ArabicCharacter.SAD : return 'S'; // \u0635 : ص
            case ArabicCharacter.DAD : return 'D'; // \u0636 : ض
            case ArabicCharacter.TAH : return 'T'; // \u0637 : ط
            case ArabicCharacter.ZAH : return 'Z'; // \u0638 : ظ
            case ArabicCharacter.AIN : return 'E'; // \u0639 : ع
            case ArabicCharacter.GHAIN : return 'g'; // \u063A : غ
            case ArabicCharacter.TATWEEL : return '_'; // \u0640 : ـ
            case ArabicCharacter.FEH : return 'f'; // \u0641 : ف
            case ArabicCharacter.QAF : return 'q'; // \u0642 : ق
            case ArabicCharacter.KAF : return 'k'; // \u0643 : ك
            case ArabicCharacter.LAM : return 'l'; // \u0644 : ل
            case ArabicCharacter.MEEM : return 'm'; // \u0645 : م
            case ArabicCharacter.NOON : return 'n'; // \u0646 : ن
            case ArabicCharacter.HEH : return 'h'; // \u0647 : ه
            case ArabicCharacter.WAW : return 'w'; // \u0648 : و
            case ArabicCharacter.ALEF_MAKSURA : return 'Y'; // \u0649 : ى
            case ArabicCharacter.YEH : return 'y'; // \u064A : ي
            case ArabicCharacter.FATHATAN : return 'F'; // \u064B : ً
            case ArabicCharacter.DAMMATAN : return 'N'; // \u064C : ٌ
            case ArabicCharacter.KASRATAN : return 'K'; // \u064D : ٍ
            case ArabicCharacter.FATHA : return 'a'; // \u064E : َ
            case ArabicCharacter.DAMMA : return 'u'; // \u064F : ُ
            case ArabicCharacter.KASRA : return 'i'; // \u0650 : ِ
            case ArabicCharacter.SHADDA : return '~'; // \u0651 : ّ
            case ArabicCharacter.SUKUN : return 'o'; // \u0652 : ْ
            case ArabicCharacter.ALEF_SUPERSCRIPT : return '`'; // \u0670 :  
            case ArabicCharacter.ALEF_WASLA : return '{'; // \u0671 :  
            case ArabicCharacter.PEH : return 'P'; // \u067E :  
            case ArabicCharacter.TCHEH : return 'J'; // \u0686 :  
            case ArabicCharacter.VEH : return 'V'; // \u06A4 :  
            case ArabicCharacter.GAF : return 'G'; // \u06AF :  
            case ArabicCharacter.JEH  : return 'R'; // \u0698 :  
            case ArabicCharacter.COMMA : return ','; // \u060C :  
            case ArabicCharacter.SEMICOLON : return ';'; // \u061B :  
            case ArabicCharacter.QUESTION_MARK : return '?'; // \u061F :  
            default : return c;
        }
    }
/*============================================================================*/
/**
 * <p>Returns a character in the Buckwalter transliteration system from a character in arabic</p>
 * @param c the arabic character.
 * @return the romanized character. 
 */
    public static char getArabicCharacter(char c) {        
        switch (c) {
            case '\'': return ArabicCharacter.HAMZA; // \u0621 : ء
            case '|' : return ArabicCharacter.ALEF_MADDA; // \u0622 : آ
            case '>' : return ArabicCharacter.ALEF_HAMZA_ABOVE; // \u0623 : أ
            case '&' : return ArabicCharacter.WAW_HAMZA; // \u0624 : ؤ
            case '<' : return ArabicCharacter.ALEF_HAMZA_BELOW; // \u0625 : إ
            case '}' : return ArabicCharacter.YEH_HAMZA; // \u0626 : ئ
            case 'A' : return ArabicCharacter.ALEF; // \u0627 : ا
            case 'b' : return ArabicCharacter.BEH; // \u0628 : ب
            case 'p' : return ArabicCharacter.TEH_MARBUTA; // \u0629 : ة
            case 't' : return ArabicCharacter.TEH; // \u062A : ت
            case 'v' : return ArabicCharacter.THEH; // \u062B : ث
            case 'j' : return ArabicCharacter.JEEM; // \u062C : ج
            case 'H' : return ArabicCharacter.HAH; // \u062D : ح
            case 'x' : return ArabicCharacter.KHAH; // \u062E : خ
            case 'd' : return ArabicCharacter.DAL; // \u062F : د
            case '*' : return ArabicCharacter.THAL; // \u0630 : ذ
            case 'r' : return ArabicCharacter.REH; // \u0631 : ر
            case 'z' : return ArabicCharacter.ZAIN; // \u0632 : ز
            case 's' : return ArabicCharacter.SEEN; // \u0633 : س
            case '$' : return ArabicCharacter.SHEEN; // \u0634 : ش
            case 'S' : return ArabicCharacter.SAD; // \u0635 : ص
            case 'D' : return ArabicCharacter.DAD; // \u0636 : ض
            case 'T' : return ArabicCharacter.TAH; // \u0637 : ط
            case 'Z' : return ArabicCharacter.ZAH; // \u0638 : ظ
            case 'E' : return ArabicCharacter.AIN; // \u0639 : ع
            case 'g' : return ArabicCharacter.GHAIN; // \u063A : غ
            case '_' : return ArabicCharacter.TATWEEL; // \u0640 : ـ
            case 'f' : return ArabicCharacter.FEH; // \u0641 : ف
            case 'q' : return ArabicCharacter.QAF; // \u0642 : ق
            case 'k' : return ArabicCharacter.KAF; // \u0643 : ك
            case 'l' : return ArabicCharacter.LAM; // \u0644 : ل
            case 'm' : return ArabicCharacter.MEEM; // \u0645 : م
            case 'n' : return ArabicCharacter.NOON; // \u0646 : ن
            case 'h' : return ArabicCharacter.HEH; // \u0647 : ه
            case 'w' : return ArabicCharacter.WAW; // \u0648 : و
            case 'Y' : return ArabicCharacter.ALEF_MAKSURA; // \u0649 : ى
            case 'y' : return ArabicCharacter.YEH; // \u064A : ي
            case 'F' : return ArabicCharacter.FATHATAN; // \u064B : ً
            case 'N' : return ArabicCharacter.DAMMATAN; // \u064C : ٌ
            case 'K' : return ArabicCharacter.KASRATAN; // \u064D : ٍ
            case 'a' : return ArabicCharacter.FATHA; // \u064E : َ
            case 'u' : return ArabicCharacter.DAMMA; // \u064F : ُ
            case 'i' : return ArabicCharacter.KASRA; // \u0650 : ِ
            case '~' : return ArabicCharacter.SHADDA; // \u0651 : ّ
            case 'o' : return ArabicCharacter.SUKUN; // \u0652 : ْ
            case '`' : return ArabicCharacter.ALEF_SUPERSCRIPT; // \u0670 :  
            case '{' : return ArabicCharacter.ALEF_WASLA; // \u0671 :  
            case 'P' : return ArabicCharacter.PEH; // \u067E :  
            case 'J' : return ArabicCharacter.TCHEH; // \u0686 :  
            case 'V' : return ArabicCharacter.VEH; // \u06A4 :  
            case 'G' : return ArabicCharacter.GAF; // \u06AF :  
            case 'R' : return ArabicCharacter.JEH; // \u0698 :  
            case ',' : return ArabicCharacter.COMMA; // \u060C :  
            case ';' : return ArabicCharacter.SEMICOLON; // \u061B :  
            case '?' : return ArabicCharacter.QUESTION_MARK; // \u061F :  
            default  : return c;
        }
    }
/*============================================================================*/
}
