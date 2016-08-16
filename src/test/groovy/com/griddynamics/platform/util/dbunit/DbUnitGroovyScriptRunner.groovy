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
        String basePackage = args.length > 1 ? args[1] : "com.griddynamics.model."
        generate0(fromInputStream(new FileInputStream(new File(args[0]))), stringWriter, basePackage)
        println stringWriter.toString()
    }

    public static void generate(InputStream stream, Writer writer, String basePackage) {
        generate0(fromInputStream(stream), writer, basePackage);
    }
    
    private static void generate0(Script script, Writer writer, String basePackage) {
        def builder = new DelegatedMarkupBuilder(writer, basePackage)
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
