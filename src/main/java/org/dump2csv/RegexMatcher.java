package org.dump2csv;

import org.hamcrest.Description;

public class RegexMatcher extends org.hamcrest.TypeSafeMatcher<String> {

    private final String regex;

    public RegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches regex=`" + regex + "`");
    }

    @Override
    public boolean matchesSafely(final String string) {
        return string.matches(regex);
    }


    public static RegexMatcher matches(final String regex) {
        return new RegexMatcher(regex);
    }
}
