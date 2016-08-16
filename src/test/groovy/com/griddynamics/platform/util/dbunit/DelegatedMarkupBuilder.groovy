package com.griddynamics.platform.util.dbunit

import com.google.common.base.CaseFormat
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.runtime.InvokerHelper

import java.lang.annotation.Annotation
import java.lang.reflect.Array
import java.lang.reflect.Modifier

/**
 * @author: Ilya Krokhmalyov ikrokhmalyov@griddynamics.com
 * @since: 3/5/15
 */

class DelegatedMarkupBuilder extends MarkupBuilder {
    private String basePackage;
    Map<Annotation, Consumer> consumers

    DelegatedMarkupBuilder(Writer pw, String basePackage) {
        super(pw)
        this.basePackage = basePackage;
    }

    @Override
    Object invokeMethod(String methodName, Object args) {

        def argsList = InvokerHelper.asList(args)
        def returnValue
        if (argsList != null && argsList.size() == 1) {
            def arg = argsList[0]
            if (!(arg instanceof Map) && !(arg instanceof Closure)) {
                returnValue = super.invokeMethod(methodName, _(arg))
            }
            else {
                returnValue = super.invokeMethod(methodName, args)
            }
        } else {
            returnValue = super.invokeMethod(methodName, args)
        }

        args.each { i ->
            def currentElementAsMap
            if (!(i instanceof Map) && !(i instanceof Closure)) {
                currentElementAsMap = getObjectsOnly(i);
                currentElementAsMap
                    .findAll { element -> element.value }
                    .each { mi ->
                        mi.value.each { internalItem ->
                            invokeMethod(mi.key.toUpperCase(), internalItem)
                        }
                    }
            }
            else {
                currentElementAsMap = i;
            }
        }

        return returnValue
    }

    def _(p) {
        p.class.declaredFields.findAll {
            if (
            !it.synthetic &&
                    (it.modifiers & Modifier.STATIC) == 0  &&
                    !Iterable.class.isAssignableFrom(it.type) &&
                    !Object[].class.isAssignableFrom(it.type) &&
                    !Map.class.isAssignableFrom(it.type) &&
                    !Array.class.isAssignableFrom(it.type) &&
                    !it.type.getName().startsWith(basePackage)
            ) {
                if (!it.isAccessible()) {
                    it.setAccessible(true)
                }
                it.get(p)  != null
            }
        }.collectEntries {
            [ (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, it.name)):p."$it.name" ]
        }
    }

    def getObjectsOnly(p) {
        p.class.declaredFields.findAll {
            if (!it.synthetic &&
                    (it.modifiers & Modifier.STATIC) == 0  &&
                    it.type != java.lang.Object.class &&
                    !it.type.getName().startsWith("java.") &&
                    !it.type.primitive
            ) {

                if (!it.isAccessible()) {
                    it.setAccessible(true)
                }


                def value = it.get(p)
                return (value != null && (!isCollectionOrArray(value) || value.size() != 0))
            }
            false
        }.collectEntries {
            [ (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, it.name)):p."$it.name" ]
        }
    }

    boolean isCollectionOrArray(object) {
        [Collection, Object[]].any { it.isAssignableFrom(object.getClass()) }
    }
}