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
import java.util.regex.Pattern;
/*============================================================================*/
public class ArabicString {
    //+-------------------------------------------+
    public static final String allArabicCharacter = ""
            + ArabicCharacter.ALEF_WASLA
            + ArabicCharacter.HAMZA
            + ArabicCharacter.ALEF_MADDA
            + ArabicCharacter.ALEF_HAMZA_ABOVE
            + ArabicCharacter.ALEF_HAMZA_BELOW
            + ArabicCharacter.WAW_HAMZA
            + ArabicCharacter.YEH_HAMZA
            + ArabicCharacter.ALEF
            + ArabicCharacter.BEH
            + ArabicCharacter.TEH_MARBUTA
            + ArabicCharacter.TEH
            + ArabicCharacter.THEH
            + ArabicCharacter.JEEM
            + ArabicCharacter.HAH
            + ArabicCharacter.KHAH
            + ArabicCharacter.DAL
            + ArabicCharacter.THAL
            + ArabicCharacter.REH
            + ArabicCharacter.ZAIN
            + ArabicCharacter.SEEN
            + ArabicCharacter.SHEEN
            + ArabicCharacter.SAD
            + ArabicCharacter.DAD
            + ArabicCharacter.TAH
            + ArabicCharacter.ZAH
            + ArabicCharacter.AIN
            + ArabicCharacter.GHAIN
            + ArabicCharacter.FEH
            + ArabicCharacter.QAF
            + ArabicCharacter.KAF
            + ArabicCharacter.LAM
            + ArabicCharacter.MEEM
            + ArabicCharacter.NOON
            + ArabicCharacter.HEH
            + ArabicCharacter.WAW
            + ArabicCharacter.YEH
            + ArabicCharacter.ALEF_MAKSURA;
    //+-------------------------------------------+
    public static final String allDiacritics = ArabicString.allDiacriticsExceptSHADDA
            + ArabicCharacter.SHADDA;
    //+-------------------------------------------+
    public static final String allDiacriticsExceptSHADDA = "" 
            + ArabicCharacter.DAMMA
            + ArabicCharacter.DAMMATAN
            + ArabicCharacter.FATHA
            + ArabicCharacter.FATHATAN
            + ArabicCharacter.KASRA
            + ArabicCharacter.KASRATAN
            + ArabicCharacter.SUKUN;
    //+-------------------------------------------+
    public static final Pattern DIACRITICS_EXCEPT_SHADDA = Pattern.compile("[" + ArabicString.allDiacriticsExceptSHADDA + "]$");
    //+-------------------------------------------+
    public static final Pattern ALL_DIACRITICS = Pattern.compile("[" + ArabicString.allDiacritics + "]");
    //+-------------------------------------------+
    public static final Pattern ALL_HAMZA = Pattern.compile("["+
            + ArabicCharacter.ALEF_HAMZA_ABOVE
            + ArabicCharacter.ALEF_HAMZA_BELOW
            + ArabicCharacter.WAW_HAMZA
            + ArabicCharacter.YEH_HAMZA + "]");
    //+-------------------------------------------+

}
