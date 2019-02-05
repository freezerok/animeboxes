package com.example.bottomnavigationcheck;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Available {
    ArrayList<String[]> names;
    Available() {
        names = new ArrayList<String[]>();
        names.add(new String[] {"asunar", "Yuuki Asuna"});
        names.add(new String[] {"remr", "Rem"});
        names.add(new String[] {"haruhir", "Haruhi Suzumiya"});
        names.add(new String[] {"violetr", "Violet Evergarden"});
        names.add(new String[] {"hiyorir", "Iki Hiyori"});
        names.add(new String[] {"misakiayur", "Misaki Ayuzawa"});
        names.add(new String[] {"02r", "Zero Two"});
    }

    public int getSize() {
        return this.names.size();
    }
}
