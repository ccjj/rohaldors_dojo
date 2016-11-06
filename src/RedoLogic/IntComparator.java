/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RedoLogic;

import java.util.Comparator;

/**
 * 
 * @author Omar
 */
        class IntComparator implements Comparator {
            @Override
            public int compare(Object o1, Object o2) {
                Integer int1 = (Integer)o1;
                Integer int2 = (Integer)o2;
                return int1.compareTo(int2);
            }

        }