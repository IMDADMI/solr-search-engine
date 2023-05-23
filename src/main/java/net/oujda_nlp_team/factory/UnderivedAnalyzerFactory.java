package net.oujda_nlp_team.factory;
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
import net.oujda_nlp_team.interfaces.IUnderived;
import net.oujda_nlp_team.entity.Segment;
/*============================================================================*/
public abstract class UnderivedAnalyzerFactory {
/*============================================================================*/
    public abstract java.util.List analyzedSegment( String normalizedWord, String unvoweledWord, Segment segment);
/*============================================================================*/
    public void clear(IUnderived underived) { underived.clear();}
/*============================================================================*/
}
