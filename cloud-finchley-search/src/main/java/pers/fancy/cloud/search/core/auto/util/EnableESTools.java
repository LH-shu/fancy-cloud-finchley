package pers.fancy.cloud.search.core.auto.util;

import org.springframework.core.annotation.AnnotationAttributes;

/**
 **/
public class EnableESTools {
    private static String[] basePackages;
    private static String[] value;
    private static String[] entityPath;
    private static boolean printregmsg = false;

    public static void gainAnnoInfo(AnnotationAttributes attributes ){
        basePackages = attributes.getStringArray("basePackages");
        value = attributes.getStringArray("value");
        entityPath = attributes.getStringArray("entityPath");
        printregmsg = attributes.getBoolean("printregmsg");
    }

    public static String[] getBasePackages() {
        return basePackages;
    }

    public static String[] getValue() {
        return value;
    }

    public static String[] getEntityPath() {
        return entityPath;
    }

    public static boolean isPrintregmsg() {
        return printregmsg;
    }
}
