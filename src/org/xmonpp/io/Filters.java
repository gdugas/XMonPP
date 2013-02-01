package org.xmonpp.io;

import java.util.ArrayList;
import java.util.Collection;

public class Filters {

    public static Collection<Filter> filters = new ArrayList<Filter>();

    public void add(Filter filter) {
        Filters.filters.add(filter);
    }
}
