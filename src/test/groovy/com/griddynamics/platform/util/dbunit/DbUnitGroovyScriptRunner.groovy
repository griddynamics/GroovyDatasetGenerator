package com.griddynamics.platform.util.dbunit;

import com.google.common.base.Charsets
import com.google.common.io.CharStreams

/**
 * @author: Ilya Krokhmalyov ikrokhmalyov@griddynamics.com
 * @since: 3/5/15
 */

class DbUnitGroovyScriptRunner {

    public static void main(String[] args) {
        StringWriter stringWriter = new StringWriter();
        generate0(fromInputStream(new FileInputStream(new File(args[0]))), stringWriter)
        println stringWriter.toString()
    }

    public static void generate(InputStream stream, Writer writer) {
        generate0(fromInputStream(stream), writer);
    }
    
    private static void generate0(Script script, Writer writer) {
        def builder = new DelegatedMarkupBuilder(writer)
        builder.setDoubleQuotes(true)

        ExpandoMetaClass metaClass = new ExpandoMetaClass(script.class);
        metaClass.addMetaMethod(new MissingMarkupMethod(builder));
        metaClass.addMetaMethod(new MissingMarkupGetProperty(builder));
        metaClass.initialize();

        script.setMetaClass(metaClass);
        builder.getMkp().xmlDeclaration(version:'1.0', encoding:"UTF-8")
        builder.dataset{script.run()}
    }
    
    private static Script fromInputStream(InputStream inputStream) {
        GroovyClassLoader scriptLoader = new GroovyClassLoader(DbUnitGroovyScriptRunner.class.classLoader);
        String scriptText = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
        scriptLoader.parseClass(scriptText).newInstance()
    }
    
}
