package org.dump2csv;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

public class RegexFinder extends TypeSafeMatcher<String> {

    private final String regex;

    public RegexFinder(final String regex) {
        this.regex = regex;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("finds regex=`" + regex + "`");
    }

    @Override
    public boolean matchesSafely(final String string) {
        return Pattern.compile(regex, Pattern.DOTALL|Pattern.MULTILINE).matcher(string).find();
    }

    public static RegexFinder finds(final String regex) {
        return new RegexFinder(regex);
    }
}
