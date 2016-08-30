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
public class Bowl {
    def id
    def petId
    String color = "Yellow"
    String material = "Plastic"
    String size = "S"
    Feed feed = null
}
