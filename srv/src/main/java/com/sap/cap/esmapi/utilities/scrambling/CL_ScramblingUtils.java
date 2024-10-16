package com.sap.cap.esmapi.utilities.scrambling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CL_ScramblingUtils
{

    private static final String ccPattern = "(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|"
            + "6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|"
            + "(?:2131|1800|35\\d{3})\\d{11}|"
            + "(?:4\\d{3}|5[1-5]\\d{2}|6011|7\\d{3})-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13})";

    private static final String CC_PLACEHOLDER = "XXXX XXXX XXXX XXXX";

    private static final Pattern ccDetector = Pattern.compile(ccPattern);

    public static String scrambleText(String text)
    {
        String scrambledTxt = null;

        scrambledTxt = text.replaceAll("(\\d*)\\.(\\d+)", "$1$2");
        scrambledTxt = scrambledTxt.replaceAll("(\\d*)\\,(\\d+)", "$1$2");
        scrambledTxt = scrambledTxt.replaceAll("(\\d*)\\s(\\d+)", "$1$2");

        scrambledTxt = redactCC(scrambledTxt);
        log.info("Scrambled Text : " + scrambledTxt);

        return scrambledTxt;
    }

    public static String redactCC(String data)
    {
        Matcher m = ccDetector.matcher(data);
        return m.replaceAll(CC_PLACEHOLDER);
    }
}
