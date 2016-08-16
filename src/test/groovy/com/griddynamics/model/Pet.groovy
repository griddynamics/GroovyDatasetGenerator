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
@Cassandra
public class Pet {
    def petId;
    String type = "pet"
    String name = "Жывотное"
    String color = "White"
    String sex = "N/A"
    String breed = ""
    Bowl[] bowl = []

    def plus(List<Bowl> b) {
        bowl += b.each {it.petId(petId)}
        this
    }

    def plus(Bowl b) {
        bowl += b.each {it.petId(petId)}
        this
    }
}
