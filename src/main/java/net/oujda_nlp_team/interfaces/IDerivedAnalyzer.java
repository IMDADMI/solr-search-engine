package net.oujda_nlp_team.interfaces;
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
import net.oujda_nlp_team.entity.Segment;
import java.util.List;
/*============================================================================*/
public interface IDerivedAnalyzer {
    //+----------------------------------------------------+
    public List analyzedSegment(String normalizedWord, Segment segment);
    //+----------------------------------------------------+
    public List getPossiblesSolutions(String normalizedWord, Segment segment);
    //+----------------------------------------------------+
    public void clear();
    //+----------------------------------------------------+
}
