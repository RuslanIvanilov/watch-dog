package ru.rwe.util;

import ru.rwe.WatchDogProperty;
import java.text.DecimalFormat;

public class BytesConvertor {


    private static DecimalFormat df = new DecimalFormat(WatchDogProperty.ROUND_PRECISION_TEMPLATE);

    public static String convert(long valueInBytes){
        double valuePowed = valueInBytes * Math.pow( WatchDogProperty.BYTES_CALC_COEFF, Measure.valueOf(WatchDogProperty.HEAP_SIZE_IN).ordinal() );
        return df.format(valuePowed) + WatchDogProperty.HEAP_SIZE_IN;

    }

}

enum Measure{ B, KB, MB, GB, TB, PB }