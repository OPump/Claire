/*
 * Copyright (C) 2015 南瓜工作室.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jasonlvhit.claire;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by Jason Lyu on 2015/1/26.
 */
public class ColorMap {

    public class ColorHolder{
        private String mLeftColor;
        private String mRightColor;

        public ColorHolder(String leftColor, String rightColor){
            mLeftColor = leftColor;
            mRightColor = rightColor;
        }

        public String getLeftColor(){return mLeftColor;}
        public String getRightColor(){return mRightColor;}
    }

    public ColorMap(){
        mColorMap = new HashMap<Integer, ColorHolder>();
        mColorMap.put(2000, new ColorHolder("#000000", "#000033"));
        mColorMap.put(1800, new ColorHolder("#330000", "#330033"));
        mColorMap.put(1600, new ColorHolder("#660000", "#660033"));
        mColorMap.put(1500, new ColorHolder("#990000", "#990033"));
        mColorMap.put(1400, new ColorHolder("#663300", "#663333"));
        mColorMap.put(1300, new ColorHolder("#993300", "#993333"));
        mColorMap.put(1200, new ColorHolder("#996600", "#996633"));
        mColorMap.put(1100, new ColorHolder("#CC0000", "#CC0033"));
        mColorMap.put(1000, new ColorHolder("#CC3300", "#CC3333"));
        mColorMap.put(900 , new ColorHolder("#FF0000", "#FF0033"));
        mColorMap.put(800 , new ColorHolder("#FF3300", "#FF3333"));
        mColorMap.put(700 , new ColorHolder("#990066", "#990099"));
        mColorMap.put(600 , new ColorHolder("#993366", "#993399"));
        mColorMap.put(500 , new ColorHolder("#CC0066", "#CC0099"));
        mColorMap.put(400 , new ColorHolder("#CC3366", "#CC3399"));
        mColorMap.put(300 , new ColorHolder("#FF0066", "#FF0099"));
        mColorMap.put(200 , new ColorHolder("#FF3366", "#FF3399"));
        mColorMap.put(100 , new ColorHolder("#FF6600", "#FF6633"));
        mColorMap.put(90  , new ColorHolder("#FF6666", "#FF6699"));
        mColorMap.put(80  , new ColorHolder("#FF9966", "#FF9999"));
        mColorMap.put(70  , new ColorHolder("#FFCC00", "#FFCC33"));
        mColorMap.put(60  , new ColorHolder("#FFFF00", "#FFFF33"));
        mColorMap.put(50  , new ColorHolder("#FFCC66", "#FFCC99"));
        mColorMap.put(40  , new ColorHolder("#FFCCCC", "#FFCCFF"));
        mColorMap.put(30  , new ColorHolder("#FF99CC", "#FF99FF"));
        mColorMap.put(20  , new ColorHolder("#CC99CC", "#CC99FF"));
        mColorMap.put(10  , new ColorHolder("#9966CC", "#9966FF"));
        mColorMap.put(0   , new ColorHolder("#0099CC", "#0099FF"));

        mColorMap.put(-10 , new ColorHolder("#0099CC", "#0099FF"));
        mColorMap.put(-20 , new ColorHolder("#00CCCC", "#00CCFF"));
        mColorMap.put(-30 , new ColorHolder("#00FFCC", "#00FFFF"));
        mColorMap.put(-40 , new ColorHolder("#00FF99", "#00FF66"));
        mColorMap.put(-50 , new ColorHolder("#00CC99", "#00CC66"));
        mColorMap.put(-60 , new ColorHolder("#009999", "#009966"));
        mColorMap.put(-70 , new ColorHolder("#006666", "#006699"));
        mColorMap.put(-80 , new ColorHolder("#006633", "#006600"));
        mColorMap.put(-90 , new ColorHolder("#66FF66", "#66FF99"));
        mColorMap.put(-100, new ColorHolder("#66FFCC", "#66FFFF"));
        mColorMap.put(-200, new ColorHolder("#66CCCC", "#66CCFF"));
        mColorMap.put(-300, new ColorHolder("#6699FF", "#6699CC"));
        mColorMap.put(-400, new ColorHolder("#6666CC", "#6666FF"));
        mColorMap.put(-500, new ColorHolder("#6600CC", "#6600FF"));
        mColorMap.put(-600, new ColorHolder("#660099", "#660066"));
        mColorMap.put(-700, new ColorHolder("#666666", "#666699"));
        mColorMap.put(-800, new ColorHolder("#CC99CC", "#CC99FF"));
        mColorMap.put(-900, new ColorHolder("#CCCCCC", "#CCCCFF"));
        mColorMap.put(-1000, new ColorHolder("#FFCCCC", "#FFCCFF"));
    }

    public ColorHolder getColor(BigInteger celsius){
        if(celsius.compareTo(BigInteger.valueOf(2000))>0) return mColorMap.get(2000);
        else if(celsius.compareTo(BigInteger.valueOf(1800)) > 0 &&
                celsius.compareTo(BigInteger.valueOf(2000)) <= 0) return mColorMap.get(1800);
        else if(celsius.compareTo(BigInteger.valueOf(1600)) > 0 &&
                celsius.compareTo(BigInteger.valueOf(1800)) <= 0) return mColorMap.get(1600);
        else if(celsius.compareTo(BigInteger.valueOf(100)) > 0 &&
                celsius.compareTo(BigInteger.valueOf(1600)) <= 0)
            return mColorMap.get(celsius.intValue() - (celsius.intValue() % 100));
        else if(celsius.intValue() >=0 && celsius.intValue() <= 100)
            return mColorMap.get(celsius.intValue() - (celsius.intValue() % 10));
        else if(celsius.compareTo(BigInteger.valueOf(-1000)) < 0) return mColorMap.get(-1000);
        else if(celsius.compareTo(BigInteger.valueOf(-1000)) >= 0 &&
                celsius.compareTo(BigInteger.valueOf(-100)) < 0)
            return mColorMap.get(celsius.intValue() - (celsius.intValue() % 100));
        else if(celsius.compareTo(BigInteger.valueOf(-100)) >= 0 &&
                celsius.compareTo(BigInteger.valueOf(0)) < 0)
            return mColorMap.get(celsius.intValue() - (celsius.intValue() % 10));
        else return mColorMap.get(0);
    }

    private static HashMap<Integer, ColorHolder> mColorMap;
}
