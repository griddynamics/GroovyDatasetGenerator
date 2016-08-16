package com.griddynamics.model

import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * @author: Helena Pantioukh epantiukh@griddynamics.com
 * @since: 8/8/16
 */

@TupleConstructor
@ToString
@Builder(builderStrategy = SimpleStrategy, prefix = "")
public class Feed {
    def time = "2016-09-16 08:23:40.389716";
    def bowlId
    def food = "Water"
    def portion = 100
}
