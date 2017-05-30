package com.example;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import javax.xml.transform.Result;

/**
 * Created by kusumasri on 5/30/17.
 */

public class TestRunner {

    public static void main(String[] args) {
        Result result = (Result) JUnitCore.runClasses(Weathertest.class);
        System.out.println(result);

    }
}
