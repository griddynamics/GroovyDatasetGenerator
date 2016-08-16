package com.griddynamics.platform.util.dbunit

import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache

import java.lang.reflect.Modifier

/**
 * @author: Ilya Krokhmalyov ikrokhmalyov@griddynamics.com
 * @since: 3/5/15
 */
public class MissingMarkupMethod extends MetaMethod {

    private final GroovyObject handler;

    public MissingMarkupMethod(GroovyObject handler) {
        super([String.class, Object.class] as Class[]);

        this.handler = handler;
    }

    @Override
    public int getModifiers() {
        return Modifier.PUBLIC;
    }

    @Override
    public String getName() {
        return "methodMissing";
    }

    @Override
    public Class getReturnType() {
        return Object.class;
    }

    @Override
    public CachedClass getDeclaringClass() {
        return ReflectionCache.getCachedClass(this.getClass());
    }

    @Override
    public Object invoke(Object object, Object[] arguments) {
        if (arguments.length != 2) {
            throw new IllegalArgumentException("Missing method should specify method name and argument");
        }

        String methodName = (String) arguments[0];
        Object[] realArgs = (Object[]) arguments[1];

        return handler.invokeMethod(methodName, realArgs);
    }
}
