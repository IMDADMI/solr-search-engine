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
public class Times {
    
    private static long start;
    
    private static long end;

    public Times() {
    }

    public static void start() {
        Times.start = java.util.Calendar.getInstance().getTimeInMillis();
    }

    public static long Start() {
        return java.util.Calendar.getInstance().getTimeInMillis();
    }


    public static long End() {
        return java.util.Calendar.getInstance().getTimeInMillis();
    }

    public static void end() {
        Times.end = java.util.Calendar.getInstance().getTimeInMillis();
    }

    public static long getTimes(){return (Times.end - Times.start);}
    
    public static void printTimes() {
        System.out.println(""+(Times.end - Times.start)+" T");
    }
    
    public static int getMilSecTimes(){
        return ((int) (Times.end - Times.start))%1000;
    }
    
    public static int getSecTimes(){
        return (int) ((Times.end - Times.start) / 1000) % 60;
    }
    
    public static int getMinTimes(){
        return (int) (((Times.end - Times.start) / (1000*60)) % 60);
    }
    
    public static int getHourTimes(){
        return (int) (((Times.end - Times.start) / (1000*60*60)) % 24);
    }
    
}
