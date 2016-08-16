package com.griddynamics.platform.util.dbunit

import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache

import java.lang.reflect.Modifier

/**
 * @author: Ilya Krokhmalyov ikrokhmalyov@griddynamics.com
 * @since: 3/5/15
 */

public class MissingMarkupGetProperty extends MetaMethod {

    private final GroovyObject handler;

    public MissingMarkupGetProperty(GroovyObject handler) {
        super([Object.class] as Class[]);

        this.handler = handler;
    }

    @Override
    public int getModifiers() {
        return Modifier.PUBLIC;
    }

    @Override
    public String getName() {
        return "propertyMissing";
    }

    @Override
    public Class getReturnType() {
        return Iterable.class;
    }

    @Override
    public CachedClass getDeclaringClass() {
        return ReflectionCache.getCachedClass(this.getClass());
    }

    @Override
    public Object invoke(Object object, Object[] arguments) {
        if (arguments.length != 1) {
            throw new IllegalArgumentException();
        }

        String propertyName = (String) arguments[0];
        Object[] args = {object};

        return handler.invokeMethod(propertyName, args);
    }
}
